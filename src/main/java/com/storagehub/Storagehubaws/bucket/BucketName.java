package com.storagehub.Storagehubaws.bucket;

public enum BucketName {
    PROFILE_IMAGE("storagehub-aws-image-upload");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }
    public String getBucketName(){
        return bucketName;
    }
}
