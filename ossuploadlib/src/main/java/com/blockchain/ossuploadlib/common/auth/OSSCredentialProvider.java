package com.blockchain.ossuploadlib.common.auth;

import com.blockchain.ossuploadlib.ClientException;

/**
 * Created by zhouzhuo on 11/4/15.
 */
public interface OSSCredentialProvider {

    /**
     * get OSSFederationToken instance
     *
     * @return
     */
    OSSFederationToken getFederationToken() throws ClientException, ClientException;
}
