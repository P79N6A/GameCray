package com.blockchain.cray.oss;

import com.blockchain.ossuploadlib.ClientException;
import com.blockchain.ossuploadlib.ServiceException;

/**
 * Created by jingdan on 2017/8/31.
 */

public interface Callback<Request, Result> {

    void onSuccess(Request request, Result result);

    void onFailure(Request request, ClientException clientException, ServiceException serviceException);
}
