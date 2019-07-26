package com.blockchain.cray.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.blockchain.cray.R;
import com.blockchain.cray.net.HttpCommand;
import com.blockchain.cray.ui.view.LoadingView;
import com.blockchain.cray.utils.ActivityCollector;
import com.blockchain.cray.utils.DebugLog;
import com.blockchain.cray.utils.SharedPreferencesUtil;

public abstract class BaseActivity extends FragmentActivity {

    protected static BaseActivity mActivity;
    protected HttpCommand mHttpCommand;
    protected LoadingView mLoadView;
    protected SharedPreferencesUtil mSp;
    private static final int WRITE_PERMISSION = 0x01;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mHttpCommand = HttpCommand.getInstance();
        mHttpCommand.setContext(mActivity);
        mSp = SharedPreferencesUtil.getInstance(this);
        mLoadView = new LoadingView(this);
        initView(savedInstanceState);
        initData();
        ActivityCollector.addActivity(this,this.getClass());
    }

    protected void requestWritePermission() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == WRITE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                DebugLog.i("You must allow permission write external storage to your mobile device.");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mLoadView) {
            if (mLoadView.isShowing()) {
                mLoadView.onDismiss();
                mLoadView = null;
            }
        }
        ActivityCollector.removeActivity(this);
    }

    public abstract void initView(Bundle savedInstanceState);
    public void initData() {}
    
    public void gotoActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null)
            intent.putExtras(encryptData(bundle));
        startActivity(intent);
        overridePendingTransition(R.anim.move_right_in_activity,R.anim.move_left_out_activity);
    }
    
    public void gotoActivityAndFinishMe(Class<?> cls, Bundle bundle, boolean isBack) {
        Intent intent = new Intent(this, cls);
        if (bundle != null)
            intent.putExtras(encryptData(bundle));
        startActivity(intent);
        finish();
        if (isBack) {
            overridePendingTransition(R.anim.move_left_in_activity,R.anim.move_right_out_activity);
        } else {
            overridePendingTransition(R.anim.move_right_in_activity,R.anim.move_left_out_activity);
        }
    }

    protected void showToast(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.move_left_in_activity,R.anim.move_right_out_activity);
    }
    
    private Bundle encryptData(Bundle bundle){
        return bundle;
    }
}
