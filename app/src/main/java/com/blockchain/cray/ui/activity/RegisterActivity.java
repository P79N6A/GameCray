package com.blockchain.cray.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.blockchain.cray.R;
import com.blockchain.cray.net.HttpCallback;
import com.blockchain.cray.net.HttpCmdCallback;
import com.blockchain.cray.ui.view.FocusEditText;
import com.blockchain.cray.utils.Constans;
import com.blockchain.cray.utils.CountDownTimerUtils;
import com.blockchain.cray.utils.DebugLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.tv_head_comm_title)
    TextView title;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_phone_number)
    FocusEditText etPhoneNumber;
    @BindView(R.id.et_verify_code)
    FocusEditText etVerifyCode;
    @BindView(R.id.tv_get_verify_code)
    TextView tvGetVerifyCode;
    @BindView(R.id.et_user_name)
    FocusEditText etUserName;
    @BindView(R.id.et_psw)
    FocusEditText etPassword;
    @BindView(R.id.et_ckeck_psw)
    FocusEditText etCheckPassword;
    @BindView(R.id.et_pay_psw)
    FocusEditText etPayPassword;
    @BindView(R.id.et_check_pay_psw)
    FocusEditText etCheckPayPassword;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    private  int DELAYMILLIS = 1000;
    private  int RECLEN_SECOND = 60000;
    private CountDownTimerUtils countDownTimerUtils;


    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        title.setText(R.string.register);
    }

    @Override
    public void initData() {
        super.initData();
        String inviteCode = getIntent().getStringExtra(Constans.KEY_INVITE_CODE);
        if (null != inviteCode) {
            DebugLog.d("inviteCode: "+inviteCode);
        }
    }

    @OnClick({R.id.tv_register,R.id.tv_get_verify_code,R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_register:
                register();
                break;
            case R.id.tv_get_verify_code:
                getVerifyCode();
                break;
            case R.id.iv_back:
                finish();
                break;

        }
    }

    private void getVerifyCode() {

        String phoneNumber = etPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            showToast(getString(R.string.register_input_phone_null));
            return;
        }

        if ( phoneNumber.length() != 11) {
            showToast(getString(R.string.register_input_phone_tips_2));
            return;
        }
        startCountDown();

       mHttpCommand.getVerifyCode(phoneNumber,Constans.TYPE_VERIFY_CODE_REGISTER, new HttpCmdCallback() {
            @Override
            public void onSucceed(Response response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        DebugLog.d("doRegister response body: "+result);
                        JSONObject jb = new JSONObject(result);
                        int code = jb.optInt("code");
                        if (code == Constans.REQUEST_SUCCESSFUL) {
                            showToast(getString(R.string.verify_code_send_success));
                        }else if (code == Constans.REGISTER_REPEAT){
                            showToast(getString(R.string.user_is_registered));
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFail(String message) {
                showToast(getString(R.string.server_net_error));
            }
        });
    }

    private void register() {
        String phoneNumber = etPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            showToast(getString(R.string.register_input_phone_null));
            return;
        }

        if ( phoneNumber.length() != 11) {
            showToast(getString(R.string.register_input_phone_tips_2));
            return;
        }

        String verifyCode = etVerifyCode.getText().toString();
        if (TextUtils.isEmpty(verifyCode)) {
            showToast(getString(R.string.register_input_code_null));
            return;
        }

        String userName = etUserName.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            showToast(getString(R.string.register_user_name_null));
            return;
        }

        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            showToast(getString(R.string.register_psw_input_null));
            return;
        }
        if (password.length() < 6 || password.length() > 18) {
            showToast(getString(R.string.register_psw_input_error1));
            return;
        }

        String checkPassword = etCheckPassword.getText().toString();
        if (TextUtils.isEmpty(checkPassword)) {
            showToast(getString(R.string.register_input_again_psw));
            return;
        }
        if (!checkPassword.equals(password)) {
            showToast(getString(R.string.register_input_again_psw_error));
            return;
        }

        String payPassword = etPayPassword.getText().toString();
        if (TextUtils.isEmpty(payPassword)) {
            showToast(getString(R.string.register_pay_psw_null));
            return;
        }

        if (payPassword.length() !=6) {
            showToast(getString(R.string.register_pay_psw_error));
            return;
        }

        String checkPayPassword = etCheckPayPassword.getText().toString();
        if (TextUtils.isEmpty(checkPayPassword)) {
            showToast(getString(R.string.register_pay_psw_again));
            return;
        }
        if (!checkPayPassword.equals(payPassword)) {
            showToast(getString(R.string.register_pay_psw_error1));
            return;
        }

        //TODO 注册时的邀请码
        String inviteCode = "hdfg-dfgd-dfg-dsfgsd-dsdfg";
        doRegister(phoneNumber, password, inviteCode, payPassword, verifyCode,userName);
    }

    private void doRegister(final String phoneNumber, final String password,final String superQrCode,final String payPassword,final String verifyCode,final String userName) {
        mLoadView.onShow();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mHttpCommand.register(RegisterActivity.this, phoneNumber, password, superQrCode, payPassword, verifyCode, userName,new HttpCallback() {
                    @Override
                    public void onResponse(String result) {
                        DebugLog.d("register result: "+result);
                        try {
                            JSONObject jb = new JSONObject(result);
                            JSONObject attach = jb.optJSONObject("attach");
                            int code = attach.optInt("code");
                            if (code == Constans.REQUEST_SUCCESSFUL) {
                                //註冊成功，返回登錄頁
                                showToast(getString(R.string.register_successful));
                                finish();
                            }else if (code == Constans.REGISTER_REPEAT){
                                showToast(getString(R.string.user_is_registered));
                            }else if (code == Constans.REGISTER_WRONG_VERIFY_CODE){
                                showToast(getString(R.string.register_wrong_verify_code));
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
            }
        }).start();
    }

    private void startCountDown(){
        DebugLog.d("startCountDown =====>> ");
        countDownTimerUtils = new CountDownTimerUtils(RegisterActivity.this,tvGetVerifyCode,RECLEN_SECOND,DELAYMILLIS);
        countDownTimerUtils.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != countDownTimerUtils) {
            countDownTimerUtils.cancel();
        }
    }
}
