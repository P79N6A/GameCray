package com.blockchain.cray.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockchain.cray.R;
import com.blockchain.cray.net.HttpCmdCallback;
import com.blockchain.cray.ui.view.FocusEditText;
import com.blockchain.cray.utils.Constans;
import com.blockchain.cray.utils.CountDownTimerUtils;
import com.blockchain.cray.utils.DebugLog;
import com.blockchain.cray.utils.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

public class ForgetPasswordActivity extends BaseActivity {

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
    @BindView(R.id.et_psw)
    FocusEditText etPassword;
    @BindView(R.id.et_ckeck_psw)
    FocusEditText etCheckPassword;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout ll_phone;

    private CountDownTimerUtils countDownTimerUtils;
    private  int DELAYMILLIS = 1000;
    private  int RECLEN_SECOND = 60000;
    private String mPswType;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        mPswType = getIntent().getStringExtra(Constans.KEY_PSW_TYPE);
        DebugLog.i("mPswType: "+mPswType);
        if (Constans.PSW_TYPE_LOGIN.equals(mPswType)) {
            title.setText(getString(R.string.title_change_password));
//            tvPhone.setVisibility(View.VISIBLE);
//            ll_phone.setVisibility(View.VISIBLE);
        }else if (Constans.PSW_TYPE_PAYMENT.equals(mPswType)){
            title.setText(getString(R.string.title_change_pay_password));
//            tvPhone.setVisibility(View.GONE);
//            ll_phone.setVisibility(View.GONE);
        }else {
            title.setText(getString(R.string.login_forget_password));
//            tvPhone.setVisibility(View.VISIBLE);
//            ll_phone.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.tv_commit,R.id.tv_get_verify_code,R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_get_verify_code:
                getVerifyCode();
                break;
            case R.id.tv_commit:
                if (mPswType.equals(Constans.PSW_TYPE_PAYMENT)) {
                    commitPaymentPsw();
                }else{
                    commitLoginPsw();
                }
                break;

        }
    }

    private void commitPaymentPsw() {
        String verifyCode = etVerifyCode.getText().toString();
        if (TextUtils.isEmpty(verifyCode)) {
            showToast(getString(R.string.register_input_code_null));
            return;
        }

        String paymentPassword = etPassword.getText().toString();
        if (TextUtils.isEmpty(paymentPassword)) {
            showToast(getString(R.string.register_psw_input_null));
            return;
        }
        if (paymentPassword.length() < 6 || paymentPassword.length() > 18) {
            showToast(getString(R.string.register_psw_input_error1));
            return;
        }

        String checkPassword = etCheckPassword.getText().toString();
        if (TextUtils.isEmpty(checkPassword)) {
            showToast(getString(R.string.register_input_again_psw));
            return;
        }
        if (!checkPassword.equals(paymentPassword)) {
            showToast(getString(R.string.register_input_again_psw_error));
            return;
        }

        mHttpCommand.changePaymentPsw(paymentPassword, verifyCode, new HttpCmdCallback() {
            @Override
            public void onSucceed(Response response) {
                try {
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        DebugLog.i("changePsw: "+result);
                        JSONObject jb = new JSONObject(result);
                        int code = jb.optInt("code");
                        if (code == Constans.REQUEST_SUCCESSFUL) {
                            showToast(getString(R.string.change_psw_successful));
                            finish();
                        }else {
                            showToast(getString(R.string.change_psw_fail));
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String message) {
                showToast(getString(R.string.server_net_error));
            }
        });

    }

    private void commitLoginPsw() {
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

        mHttpCommand.changePsw(phoneNumber, password, verifyCode, new HttpCmdCallback() {
            @Override
            public void onSucceed(Response response) {
                try {
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        DebugLog.i("changePsw: "+result);
                        JSONObject jb = new JSONObject(result);
                        int code = jb.optInt("code");
                        if (code == Constans.REQUEST_SUCCESSFUL) {
                            showToast(getString(R.string.change_psw_successful));
                            finish();
                        }else {
                            showToast(getString(R.string.change_psw_fail));
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String message) {
                showToast(getString(R.string.server_net_error));
            }
        });
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

        mHttpCommand.getVerifyCode(phoneNumber,mPswType.equals(Constans.PSW_TYPE_PAYMENT)?Constans.TYPE_VERIFY_CODE_PAY_PSW:Constans.TYPE_VERIFY_CODE_LOGIN_PSW,
                new HttpCmdCallback() {
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
                            startCountDown();
                        } else if (code == Constans.LOGIN_PSW_ERROR){
                            showToast(getString(R.string.user_is_un_register));
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

    private void startCountDown() {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                DebugLog.d("startCountDown =====>> ");
                countDownTimerUtils = new CountDownTimerUtils(ForgetPasswordActivity.this,tvGetVerifyCode,RECLEN_SECOND,DELAYMILLIS);
                countDownTimerUtils.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != countDownTimerUtils) {
            countDownTimerUtils.cancel();
        }
    }
}
