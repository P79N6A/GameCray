package com.blockchain.cray.oss;


import com.blockchain.cray.CrayApplication;
import com.blockchain.ossuploadlib.OSSClient;
import com.blockchain.ossuploadlib.common.auth.OSSAuthCredentialsProvider;

public class OSSWrapper {

    private static final OSSWrapper WRAPPER = new OSSWrapper();
    private OSSClient mClient = null;
    private static final String STS_INFO_URL = "http://*.*.*.*:****/sts/getsts";
    private static final String OSS_ENDPOINT = "http://oss-cn-shanghai.aliyuncs.com";

    private OSSWrapper() {
        OSSAuthCredentialsProvider authCredentialsProvider = new OSSAuthCredentialsProvider(STS_INFO_URL);
        mClient = new OSSClient(CrayApplication.getApplication(), OSS_ENDPOINT, authCredentialsProvider);
    }

    public static OSSWrapper sharedWrapper() {
        return WRAPPER;
    }

    public OSSClient getClient() {
        return mClient;
    }
}
