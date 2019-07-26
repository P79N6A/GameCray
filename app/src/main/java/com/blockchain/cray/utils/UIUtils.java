package com.blockchain.cray.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blockchain.cray.CrayApplication;
import com.blockchain.cray.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class UIUtils {


    private static Toast sToast;
    private static Toast sToastNotify;
    private static TextView sToastNotifyTextView;
    private static String token;
    private static String mLenovoId;
    private static String username;


    public static void updateToast(String res, int duration) {
        if (sToast == null) {
            //LayoutInflater inflate = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = Toast.makeText(getContext(), "", Toast.LENGTH_LONG).getView();
            sToast = new Toast(getContext());
            sToast.setView(v);
        }
        sToast.setText(res);
        sToast.setDuration(duration);
        sToast.show();
    }


    public static Context getContext() {
        return CrayApplication.getApplication();
    }

    public static Thread getMainThread() {
        return CrayApplication.getMainThread();
    }

    public static long getMainThreadId() {
        return CrayApplication.getMainThreadId();
    }

    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    public static void showToast(final String message) {

        if (isRunInMainThread()) {
            updateToast(message, Toast.LENGTH_LONG);
        } else {
            runInMainThread(new Runnable() {
                @Override
                public void run() {
                    updateToast(message, Toast.LENGTH_SHORT);
                }
            });
        }
    }

    public static void showNotifyInMainThread(String resId) {
        if (sToastNotify == null) {
            sToastNotify = new Toast(getContext());
            sToastNotify.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER, 0, 0);
            LayoutInflater inflate = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflate.inflate(R.layout.custom_notify_toast, null);
            sToastNotifyTextView = (TextView) v.findViewById(R.id.tv_custom_toast);
            sToastNotifyTextView.setText(resId);
//            sToastNotifyTextView.getBackground().setAlpha(150);
            sToastNotify.setView(v);
            sToastNotify.setDuration(Toast.LENGTH_SHORT);
        }
        sToastNotifyTextView.setText(resId);
        sToastNotify.setDuration(Toast.LENGTH_SHORT);
        sToastNotify.show();
    }

    /**
     * dp转px px = dip * density / 160
     *
     * @param dip
     * @return
     */
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换dip
     *
     * @param px
     * @return
     */
    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取主线程的handler
     *
     * @return
     */
    public static Handler getHandler() {
        return CrayApplication.getMainThreadHandler();
    }

    /**
     * 延时在主线程执行runnable
     *
     * @param runnable
     * @param delayMillis
     * @return
     */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    /**
     * 在主线程执行runnable
     *
     * @param runnable
     * @return
     */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /**
     * 从主线程looper里面移除runnable
     *
     * @param runnable
     */
    public static void removeCallbacks(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    /**
     * 获取资源
     *
     * @return
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取文字
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取文字数组
     *
     * @param resId
     * @return
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取dimen
     *
     * @param resId
     * @return
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     *
     * @param resId
     * @return
     */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    /**
     * 获取颜色
     *
     * @param resId
     * @return
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取颜色选择器
     *
     * @param resId
     * @return
     */
    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }


    /**
     * 获取版本号
     *
     * @param context 上下文
     * @return App版本号
     */
    public static String GetVersion(Context context) {
        try {
            PackageInfo manager = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return manager.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Unknown";
        }
    }

    public static boolean getLanguage() {
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("en")) {
            return true;
        } else {
            return false;
        }
    }

    /***
     * 弹出软键盘
     *
     * @param editText
     */
    public static void showSoftInput(EditText editText) {
        InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }

    /***
     * 隐藏软键盘
     *
     * @param editText
     */
    public static void hideSoftInput(EditText editText) {
        InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String currentTime = sdf.format(date);
        return currentTime;
    }


    // 判断SD卡是否被挂载
    public static boolean isSDCardMounted() {
        // return Environment.getExternalStorageState().equals("mounted");
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    // 获取SD卡的根目录
    public static String getSDCardBaseDir() {
        if (isSDCardMounted()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    // 往SD卡的私有Files目录下保存文件
    public static boolean saveFileToSDCardPrivateFilesDir(byte[] data, String type, String fileName, Context context) {

        BufferedOutputStream bos = null;
        if (isSDCardMounted()) {
            File file = context.getExternalFilesDir(type);
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(
                        file, fileName)));
                bos.write(data);
                bos.flush();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
