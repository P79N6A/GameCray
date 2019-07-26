package com.blockchain.cray;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Handler;

public class CrayApplication extends Application {

    // 获取到主线程的上下文
    @SuppressLint("StaticFieldLeak")
    private static CrayApplication sContext;
    // 获取到主线程的handler
    private static Handler mMainThreadHandler;
    // 获取到主线程
    private static Thread mMainThread;
    // 获取到主线程的id
    private static int mMainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        mMainThreadHandler = new Handler();
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
    }

    public static CrayApplication getApplication() {
        return sContext;
    }

    /**
     * 对外暴露主线程的handler
     *
     * @return
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 对外暴露主线程
     *
     * @return
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 对外暴露主线程id
     *
     * @return
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }
}
