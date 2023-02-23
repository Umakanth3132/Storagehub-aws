package com.storagehub.Storagehubaws.datastore;

import com.storagehub.Storagehubaws.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Repository
public class FakeUserProfileDataStore {

    private static final List<UserProfile> USER_PROFILES=new ArrayList<>();
    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("3ce121fb-1d28-4e90-95ab-0b77b502a78c"),"Umakanth","null"));
        USER_PROFILES.add(new UserProfile(UUID.fromString("4a9573bb-90f0-4ef4-a011-bebbcf5bb44b"),"reddy","null"));

    }
    public List<UserProfile> getUserProfiles(){
        return USER_PROFILES;
    }
}
