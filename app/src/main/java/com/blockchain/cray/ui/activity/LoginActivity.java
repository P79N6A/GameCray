package com.blockchain.cray.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.blockchain.cray.R;
import com.blockchain.cray.net.HttpCallback;
import com.blockchain.cray.ui.view.FocusEditText;
import com.blockchain.cray.utils.Constans;
import com.blockchain.cray.utils.DebugLog;
import com.blockchain.cray.utils.SharedPreferencesUtil;
import com.blockchain.cray.utils.UIUtils;
import com.blockchain.slidevalidationlib.Captcha;
import com.blockchain.zxinglib.CaptureActivity;
import com.blockchain.zxinglib.bean.ZxingConfig;
import com.blockchain.zxinglib.common.Constant;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.interfaces.RSAPrivateKey;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.blockchain.cray.utils.Constans.KEY_INVITE_CODE;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_scan_code_register)
    TextView tvRegister;
    @BindView(R.id.tv_forget_psw)
    TextView tv_forget_psw;
    @BindView(R.id.input_username)
    FocusEditText etUsername;
    @BindView(R.id.input_psw)
    FocusEditText etPassword;
    @BindView(R.id.iv_test)
    ImageView contentIv;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;

    private int REQUEST_CODE_SCAN = 111;
    private SliderValidationDialog mSliderValidationDialog;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mSliderValidationDialog = new SliderValidationDialog(this);
    }

    @OnClick({R.id.tv_scan_code_register,R.id.tv_forget_psw,R.id.ll_login})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_scan_code_register:
                //扫一扫注册
                //TODO 掃碼註冊
//               gotoActivity(RegisterActivity.class,null);
                AndPermission.with(this)
                    .permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Intent intent = new Intent(LoginActivity.this, CaptureActivity.class);
                                /*ZxingConfig是配置类
                                 *可以设置是否显示底部布局，闪光灯，相册，
                                 * 是否播放提示音  震动
                                 * 设置扫描框颜色等
                                 * 也可以不传这个参数
                                 * */
                                ZxingConfig config = new ZxingConfig();
                                //config.setPlayBeep(false);//是否播放扫描声音 默认为true
                                //config.setShake(false);//是否震动  默认为true
                                //config.setDecodeBarCode(false);//是否扫描条形码 默认为true
                                //config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为白色
                                //config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
                                //config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色
                                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                                startActivityForResult(intent, REQUEST_CODE_SCAN);
                            }
                        })
                        .onDenied(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Uri packageURI = Uri.parse("package:" + getPackageName());
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                showToast(getString(R.string.register_scan_permissions_denied));
                            }
                        }).start();
                break;
            case R.id.tv_forget_psw:
                Bundle bundle = new Bundle();
                bundle.putString(Constans.KEY_PSW_TYPE,Constans.PSW_TYPE_FORGET);
                gotoActivity(ForgetPasswordActivity.class,bundle);
                break;
            case R.id.ll_login:
                login();
                break;
        }
    }

    private void login() {
      //13923809473 88888888

        String phoneNumber = etUsername.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            showToast(getString(R.string.register_input_phone_null));
            return;
        }

        if ( phoneNumber.length() != 11) {
            showToast(getString(R.string.register_input_phone_tips_2));
            return;
        }

        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            showToast(getString(R.string.register_psw_input_null));
            return;
        }

        mSliderValidationDialog.show();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpCommand.getInstance().login(etUsername.getText().toString(), etPassword.getText().toString(), new HttpCmdCallback() {
//                    @Override
//                    public void onSucceed(Response response) {
//                        //{"attach":{"refreshToken":"93fd564a-2084-4524-82d0-16a10084a035","token":"6db42f40-a0cb-4434-ac7b-38ce16e8598a"},"code":0,"success":true}
//                        try {
//                            String ecryptData = response.body().string();
//                            //ecryptData result: 6B14F2DF6ED8906FA024AF5A35E671C9BA47B45D462CA7443C7BDED25229105F9FD12DCACF525FA2229F778D921752D6789D09A48A7034B37A8A8C67F0F67B40BE28BC8EF4515903E784
//                            DebugLog.d("ecryptData result: "+ecryptData);
//                            String decryptData = getdecryptData(ecryptData);
//                            DebugLog.i("decryptData result: "+decryptData);
//
//                            JSONObject jb = new JSONObject(decryptData);
//                            JSONObject attach = jb.optJSONObject("attach");
//                            int code = attach.optInt("code");
//                            if (code == Constans.REQUEST_SUCCESSFUL) {
//                                String refreshToken = attach.optString("refreshToken");
//                                String accessToken = attach.optString("accessToken");
//                                loginSuccessful(refreshToken,accessToken);
//                            }else if (code == Constans.LOGIN_PSW_ERROR){
//                                showToast(getString(R.string.login_psw_error));
//                            }
//                        } catch (IOException | JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFail(String message) {
//                        showToast(getString(R.string.server_net_error));
//                    }
//                });
//            }
//        }).start();
    }

    private void doLogin(){
        mLoadView.onShow();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mHttpCommand.login(etUsername.getText().toString(), etPassword.getText().toString(), new HttpCallback() {
                        @Override
                        public void onResponse(String result) {
                            DebugLog.d("login result: "+result);
                            //{"attach":{"refreshToken":"93fd564a-2084-4524-82d0-16a10084a035","accessToken":"6db42f40-a0cb-4434-ac7b-38ce16e8598a"},"code":0,"success":true}
                            try {
                                JSONObject jb = new JSONObject(result);
                                JSONObject attach = jb.optJSONObject("attach");
                                int code = attach.optInt("code");
                                String refreshToken = attach.optString("refreshToken");
                                String accessToken = attach.optString("accessToken");
                                if (code == Constans.REQUEST_SUCCESSFUL) {
                                    showToast(getString(R.string.login_successful));
                                    mSp.put(Constans.KEY_ISLOGIN,true);
                                    mSp.put(Constans.KEY_REFRESHTOKEN,refreshToken);
                                    mSp.put(Constans.KEY_ACCESSTOKEN,accessToken);
                                    gotoActivity(MainActivity.class,null);
                                    finish();
                                }else if (code == Constans.LOGIN_PSW_ERROR){
                                    showToast(getString(R.string.login_psw_error));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mLoadView.onDismiss();
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    DebugLog.i("Login Exception:"+e.getMessage());
                }
            }
        }).start();
    }

    private String getdecryptData(String ecryptData) {

        Map<String, Object> meRSA = null;
        try {
            meRSA = mHttpCommand.getRSAPublic();
            DebugLog.i("meRSA: "+meRSA);
//            RSAPublicKey publicKey = (RSAPublicKey) meRSA.get("public");
            RSAPrivateKey privateKey = (RSAPrivateKey) meRSA.get("private");
            DebugLog.i("privateKey: "+privateKey);
            String decryptJsonData = mHttpCommand.decryptRSA(ecryptData, "" + privateKey.getModulus(), "" + privateKey.getPrivateExponent());
            DebugLog.i("decryptJsonData: "+decryptJsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "decrypt Data error";
    }

    private void loginSuccessful(String refreshToken,String accessToken){
       showToast(getString(R.string.login_successful));
       SharedPreferencesUtil.getInstance(LoginActivity.this).put(Constans.KEY_ISLOGIN,true);
       SharedPreferencesUtil.getInstance(LoginActivity.this).put(Constans.KEY_REFRESHTOKEN,refreshToken);
       SharedPreferencesUtil.getInstance(LoginActivity.this).put(Constans.KEY_ACCESSTOKEN,accessToken);
       gotoActivity(MainActivity.class,null);
       finish();
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String scanResult = data.getStringExtra(Constant.CODED_CONTENT);
                DebugLog.d("扫描成功，结果为：" + scanResult);
                Bundle bundle = new Bundle();
                bundle.putString(KEY_INVITE_CODE,scanResult);
                gotoActivity(RegisterActivity.class,bundle);
            }
        }
    }

    class SliderValidationDialog extends Dialog implements Captcha.CaptchaListener{
        @BindView(R.id.captCha)
        Captcha captcha;
        @BindView(R.id.btn_mode)
        Button btnMode;

        public SliderValidationDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_slider_validation);
            ButterKnife.bind(this);

            //设置弹窗的宽度
            WindowManager m = getWindow().getWindowManager();
            Display d = m.getDefaultDisplay();
            WindowManager.LayoutParams p =getWindow().getAttributes();
            Point size = new Point();
            d.getSize(size);
            p.width = (int)(size.x * 0.9);//是dialog的宽度为app界面的80%
            getWindow().setAttributes(p);

            init();
        }

        private void init() {
            captcha.setCaptchaListener(this);
            captcha.setBitmap(R.mipmap.ic_cat);
            captcha.setSeekBarStyle(R.drawable.po_seekbar1,R.drawable.thumb1);
        }

        @Override
        public String onAccess(long time) {
            UIUtils.showToast("验证成功");
            doLogin();
            return "验证通过";
        }

        @Override
        public String onFailed(int count) {
//            UIUtils.showToast("验证失败,失败次数" + count);
            DebugLog.d("验证失败,失败次数" + count);
            return "验证失败";
        }

        @Override
        public String onMaxFailed() {
//            UIUtils.showToast("验证超过次数，你的帐号被封锁");
            return "可以走了";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mSliderValidationDialog) {
            if (mSliderValidationDialog.isShowing()) {
                mSliderValidationDialog.dismiss();
            }
            mSliderValidationDialog = null;
        }
    }
}
