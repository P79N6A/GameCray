package com.blockchain.cray.ui.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockchain.cray.R;
import com.blockchain.cray.ui.activity.BaseActivity;
import com.blockchain.cray.ui.activity.ForgetPasswordActivity;
import com.blockchain.cray.utils.Constans;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecurityCenterActivity extends BaseActivity {

    @BindView(R.id.tv_head_comm_title)
    TextView title;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_change_psw)
    RelativeLayout rlChangePsw;
    @BindView(R.id.rl_change_pay_psw)
    RelativeLayout rlChangePayPsw;
    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_security_center);
        ButterKnife.bind(this);
        title.setText(getString(R.string.title_security_center));
    }

    @OnClick({R.id.iv_back, R.id.rl_change_psw, R.id.rl_change_pay_psw})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_change_psw:
                Bundle bundle = new Bundle();
                bundle.putString(Constans.KEY_PSW_TYPE,Constans.PSW_TYPE_LOGIN);
                gotoActivity(ForgetPasswordActivity.class,bundle);
                break;
            case R.id.rl_change_pay_psw:
                Bundle b = new Bundle();
                b.putString(Constans.KEY_PSW_TYPE,Constans.PSW_TYPE_PAYMENT);
                gotoActivity(ForgetPasswordActivity.class,b);
                break;
        }
    }
}
