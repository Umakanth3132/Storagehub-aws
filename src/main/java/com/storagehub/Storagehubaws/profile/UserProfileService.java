package com.storagehub.Storagehubaws.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileAccessService) {
        this.userProfileDataAccessService = userProfileAccessService;
    }
    List<UserProfile> getUserProfiles(){
        return userProfileDataAccessService.getUserProfiles();
    }

    void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        //1.check if image is not empty
        //2.If file is an image
        //3.the user exists in our database
        //4.Grab some metadata from the file if any
        //5.Store the image inS3 and update database (userProfileImageLink)with s3 image link


    }
}
