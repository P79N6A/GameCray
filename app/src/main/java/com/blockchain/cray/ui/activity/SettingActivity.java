package com.blockchain.cray.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockchain.cray.R;
import com.blockchain.cray.net.HttpCmdCallback;
import com.blockchain.cray.ui.activity.BaseActivity;
import com.blockchain.cray.ui.activity.CertificationAccountActivity;
import com.blockchain.cray.ui.activity.LoginActivity;
import com.blockchain.cray.utils.ActivityCollector;
import com.blockchain.cray.utils.Constans;
import com.blockchain.cray.utils.DebugLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_comm_title)
    TextView tvTitle;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.rl_certification)
    RelativeLayout rlCertification;
    @BindView(R.id.rl_logout)
    RelativeLayout rlLogout;
//CertificationAccountActivity
    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        tvTitle.setText(getString(R.string.title_setting));
        tvNickName.setText((String)mSp.get(Constans.KEY_USER_NAME,""));
        tvMobile.setText((String)mSp.get(Constans.KEY_MOBILE,""));
    }

    @OnClick({R.id.iv_back,R.id.rl_logout,R.id.rl_certification})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_certification:
                gotoActivity(CertificationAccountActivity.class,null);
                break;
            case R.id.rl_logout:
               doLogout();
                break;
        }
    }

    private void doLogout() {
      mHttpCommand.logout(new HttpCmdCallback() {
          @Override
          public void onSucceed(Response response) {
              try {
                  String result = response.body().string();
                  DebugLog.i("logout response body:"+result);
                  //{"code":0,"success":true}
                  JSONObject jb = new JSONObject(result);
                  int code = jb.optInt("code");
                  dealResult(code);
              } catch (IOException | JSONException e) {
                  e.printStackTrace();
              }
          }

          @Override
          public void onFail(String message) {

          }
      });
    }

    private void dealResult(int code) {
        switch (code){
            case Constans.REQUEST_SUCCESSFUL:
                mSp.put(Constans.KEY_ISLOGIN,false);
                mSp.put(Constans.KEY_REFRESHTOKEN,"");
                mSp.put(Constans.KEY_ACCESSTOKEN,"");
                mSp.put(Constans.KEY_QRCODE,"");
                mSp.put(Constans.KEY_USER_NAME,"");
                mSp.put(Constans.KEY_MOBILE,"");
                gotoActivity(LoginActivity.class,null);
                finish();
                ActivityCollector.removeAllActivity();
                break;
             default:
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         showToast("操作失败，请重试！");
                     }
                 });
                 break;
        }
    }
}
