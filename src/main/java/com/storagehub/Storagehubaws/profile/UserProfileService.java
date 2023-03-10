package com.storagehub.Storagehubaws.profile;

import com.storagehub.Storagehubaws.bucket.BucketName;
import com.storagehub.Storagehubaws.filestore.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileAccessService,
                              FileStore fileStore) {
        this.userProfileDataAccessService = userProfileAccessService;
        this.fileStore = fileStore;
    }
    List<UserProfile> getUserProfiles(){
        return userProfileDataAccessService.getUserProfiles();
    }

    void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        //1.check if image is not empty
        if(file.isEmpty()){
            throw  new IllegalStateException("Cannot upload empty file ["+ file.getSize()+" ]");
        }
        //2.If file is an image
        if(!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(),
                ContentType.IMAGE_PNG.getMimeType(),
                ContentType.IMAGE_GIF.getMimeType()).contains(file.getContentType())){
            throw  new IllegalStateException("File must be an image ["+ file.getContentType()+"]");
        }
        //3.the user exists in our database
        UserProfile user=userProfileDataAccessService
                .getUserProfiles()
                .stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
                .findFirst()
                .orElseThrow(()->new IllegalStateException(String.format("User Profile %s is not found",userProfileId)));
        //4.Grab some metadata from the file if any

        Map<String,String> metadata =new HashMap<>();
        metadata.put("Content-Type",file.getContentType());
        metadata.put("Content-Length",String.valueOf(file.getSize()));

        //5.Store the image in S3 and update database (userProfileImageLink)with s3 image link

        String path=String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(),user.getUserProfileId());
        String filename=String.format("%s-%s",file.getOriginalFilename(),UUID.randomUUID());
        try{
            fileStore.save(path,filename,Optional.of(metadata),file.getInputStream());
            user.setUserProfileImageLink(filename);
        }
        catch(IOException e){
            throw new IllegalStateException(e);
        }
    }

    byte[] downloadUserProfileImage(UUID userProfileId) {
        UserProfile user=userProfileDataAccessService
                .getUserProfiles()
                .stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
                .findFirst()
                .orElseThrow(()->new IllegalStateException(String.format("User Profile %s is not found",userProfileId)));

        String path=String.format("%s/%s",
                BucketName.PROFILE_IMAGE.getBucketName(),
                user.getUserProfileId());
        return user.getUserProfileImageLink()
                .map(key -> fileStore.download(path,key))
                .orElse(new byte[0]);


    }
}
