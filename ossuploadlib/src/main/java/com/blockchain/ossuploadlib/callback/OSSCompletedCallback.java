package com.blockchain.ossuploadlib.callback;


import com.blockchain.ossuploadlib.ClientException;
import com.blockchain.ossuploadlib.ServiceException;
import com.blockchain.ossuploadlib.model.OSSRequest;
import com.blockchain.ossuploadlib.model.OSSResult;

/**
 * Created by zhouzhuo on 11/19/15.
 */
public interface OSSCompletedCallback<T1 extends OSSRequest, T2 extends OSSResult> {

    public void onSuccess(T1 request, T2 result);

    public void onFailure(T1 request, ClientException clientException, ServiceException serviceException);
}
