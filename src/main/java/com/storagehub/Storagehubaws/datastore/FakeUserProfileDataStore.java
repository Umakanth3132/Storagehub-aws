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
        USER_PROFILES.add(new UserProfile(UUID.randomUUID(),"Umakanth","null"));
        USER_PROFILES.add(new UserProfile(UUID.randomUUID(),"reddy","null"));

    }
    public List<UserProfile> getUserProfiles(){
        return USER_PROFILES;
    }
}
