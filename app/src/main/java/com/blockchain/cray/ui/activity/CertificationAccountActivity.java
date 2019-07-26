package com.blockchain.cray.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockchain.cray.R;
import com.blockchain.cray.net.HttpCmdCallback;
import com.blockchain.cray.utils.Constans;
import com.blockchain.cray.utils.DebugLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

public class CertificationAccountActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_comm_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_id_number)
    EditText etNumber;
    @BindView(R.id.tv_commit)
    TextView tvCommit;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_certification_account);
        ButterKnife.bind(this);
        tvTitle.setText(getString(R.string.setting_certification_name));
    }

    @OnClick({R.id.iv_back,R.id.tv_commit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_commit:
                doCommit();
                break;
        }
    }

    private void doCommit() {

        final String realName = etName.getText().toString();
        if (TextUtils.isEmpty(realName)) {
            showToast(getString(R.string.title_certify_et_real_name));
            return;
        }

        String idNumber = etNumber.getText().toString();
        if (TextUtils.isEmpty(idNumber)) {
            showToast(getString(R.string.title_certify_et_id_number));
            return;
        }

        if (idNumber.length() < 18) {
            showToast(getString(R.string.title_certify_et_id_number_error));
            return;
        }

        mHttpCommand.nameAuthentication(realName, idNumber, new HttpCmdCallback() {
            @Override
            public void onSucceed(Response response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        DebugLog.i("nameAuthentication: "+result);
                        JSONObject jb = new JSONObject(result);
                        int code = jb.optInt("code");
                        if (code == Constans.REQUEST_SUCCESSFUL) {
                            showToast(getString(R.string.name_Authentication_success));
                            finish();
                        }else if (code == Constans.LOGIN_PSW_ERROR){
                            showToast(getString(R.string.name_Authentication_fail));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFail(String message) {

            }
        });
    }
}
