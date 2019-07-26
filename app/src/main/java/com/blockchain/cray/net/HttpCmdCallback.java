package com.blockchain.cray.net;

import android.support.annotation.NonNull;

import com.blockchain.cray.utils.DebugLog;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by huangzy on 2017-08-04.
 */

public abstract class HttpCmdCallback implements Callback {

    @Override
    public void onResponse(@NonNull final Call call, @NonNull Response response) {
        onSucceed(response);
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException t) {//网络问题会走该回调


        if(t instanceof SocketTimeoutException){
            DebugLog.e("SocketTimeoutException--->"+t.getMessage());
        }else if(t instanceof ConnectException){
            DebugLog.e("ConnectException--->"+t.getMessage());
        }
        onFail(t.getMessage());
    }


    public abstract void onSucceed(Response response);

    public abstract void onFail(String message);

}
