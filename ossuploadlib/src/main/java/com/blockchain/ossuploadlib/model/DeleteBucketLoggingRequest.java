package com.blockchain.ossuploadlib.model;

public class DeleteBucketLoggingRequest extends OSSRequest {
    private String mBucketName;

    public String getBucketName() {
        return mBucketName;
    }

    public void setBucketName(String bucketName) {
        this.mBucketName = bucketName;
    }
}
