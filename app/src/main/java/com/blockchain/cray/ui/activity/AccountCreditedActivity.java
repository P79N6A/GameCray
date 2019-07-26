package com.blockchain.cray.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blockchain.cray.R;
import com.blockchain.cray.bean.UserInfoBean;
import com.blockchain.cray.net.HttpCmdCallback;
import com.blockchain.cray.ui.view.CommonPopupWindow;
import com.blockchain.cray.utils.Constans;
import com.blockchain.cray.utils.DebugLog;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

import static com.blockchain.cray.utils.Constans.REQUEST_CODE_ADD_ACCOUNT_ALIPAY;
import static com.blockchain.cray.utils.Constans.REQUEST_CODE_ADD_ACCOUNT_BANK_CARD;
import static com.blockchain.cray.utils.Constans.REQUEST_CODE_ADD_ACCOUNT_WEIXIN;

public class AccountCreditedActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_comm_title)
    TextView tvTitle;
    @BindView(R.id.tv_add_account)
    TextView tvAddAccount;
    @BindView(R.id.ll_content)
    LinearLayout content;

    @BindView(R.id.rl_account_alipay)
    RelativeLayout rlAccountAlipay;
    @BindView(R.id.tv_account_name_alipay)
    TextView tvAccountNameAlipay;
    @BindView(R.id.tv_account_number_alipay)
    TextView tvAccountNumberAlipay;

    @BindView(R.id.rl_account_weixin)
    RelativeLayout rlAccountWeixin;
    @BindView(R.id.tv_account_name_weixin)
    TextView tvAccountNameWeixin;
    @BindView(R.id.tv_account_number_weixin)
    TextView tvAccountNumberWeixin;

    @BindView(R.id.rl_account_unionpay)
    RelativeLayout rlAccountUnionpay;
    @BindView(R.id.tv_account_name_unionpay)
    TextView tvAccountNameUnionpay;
    @BindView(R.id.tv_account_number_unionpay)
    TextView tvAccountNumberUnionpay;

    private CommonPopupWindow popupWindow;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        tvTitle.setText(getString(R.string.title_account_yollon));
//        UserInfoBean userInfoBean = (UserInfoBean) getIntent().getSerializableExtra(Constans.KEY_ACCOUNT_INFO);
        initUserInfo();
    }

    private void initUserInfo() {
        mLoadView.onShow();
        mHttpCommand.loadUserInfo(new HttpCmdCallback() {
            @Override
            public void onSucceed(Response response) {
                mLoadView.onDismiss();
                try {
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        DebugLog.i("userInfo: "+result);
                        if (null != result) {
                            UserInfoBean  userInfo = JSON.parseObject(result, UserInfoBean.class);
                            if (null != userInfo) {
                                if (userInfo.getCode() == Constans.REQUEST_SUCCESSFUL) {
                                    initUI(userInfo);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String message) {
                mLoadView.onDismiss();
            }
        });
    }

    private void initUI(final UserInfoBean userInfoBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null != userInfoBean) {
                    initAliPay(userInfoBean);
                    initWeixinPay(userInfoBean);
                    initUnionpay(userInfoBean);
                }else {
                    rlAccountAlipay.setVisibility(View.GONE);
                    rlAccountWeixin.setVisibility(View.GONE);
                    rlAccountUnionpay.setVisibility(View.GONE);
                }
            }
        });

    }

    private void initUnionpay(UserInfoBean userInfoBean) {

        String unionPayAccountName = userInfoBean.getAttach().getYinlianName();
        String unionPayAccountNumber = userInfoBean.getAttach().getYinlianAccount();
        if (!TextUtils.isEmpty(unionPayAccountName)) {
            rlAccountUnionpay.setVisibility(View.VISIBLE);
            tvAccountNameUnionpay.setText(unionPayAccountName);
            tvAccountNumberUnionpay.setText(unionPayAccountNumber);
        }else {
            rlAccountUnionpay.setVisibility(View.GONE);
        }
    }

    private void initWeixinPay(UserInfoBean userInfoBean) {
        String weixinPayAccountName = userInfoBean.getAttach().getWeixinName();
        String weixinAccountNumber = userInfoBean.getAttach().getWeixinAccount();
        if (!TextUtils.isEmpty(weixinPayAccountName)) {
            rlAccountWeixin.setVisibility(View.VISIBLE);
            tvAccountNameWeixin.setText(weixinPayAccountName);
            tvAccountNumberWeixin.setText(weixinAccountNumber);
        }else {
            rlAccountWeixin.setVisibility(View.GONE);
        }
    }

    private void initAliPay(UserInfoBean userInfoBean) {
        String aliPayAccountName = userInfoBean.getAttach().getZhifubaoName();
        String aliPayAccountNumber = userInfoBean.getAttach().getZhifubaoAccount();
        if (!TextUtils.isEmpty(aliPayAccountName)) {
            rlAccountAlipay.setVisibility(View.VISIBLE);
            tvAccountNameAlipay.setText(aliPayAccountName);
            tvAccountNumberAlipay.setText(aliPayAccountNumber);
        }else {
            rlAccountAlipay.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.iv_back,R.id.tv_add_account})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_add_account:
                showPopupWindow();
                break;
        }
    }

    private void showPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.popupwidow_item,null);
        setViewClickLisener(view);
        popupWindow = new CommonPopupWindow.Builder(this)
                      .setView(view)
                       .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                      .setBackGroundLevel(0.5f)//取值范围0.0f-1.0f 值越小越暗
                      .setAnimationStyle(R.style.AnimUp)
                      .create();

        popupWindow.showAtLocation(content, Gravity.BOTTOM, 0, 0);
    }

    private void setViewClickLisener(View view) {
//        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(this,AddAccountCreditedActivity.class);
        view.findViewById(R.id.tv_bank_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                bundle.putString(Constans.KEY_ACCOUNT_TYPE,Constans.ACCOUNT_BANK_CARD);
//                gotoActivity(AddAccountCreditedActivity.class,bundle);
                intent.putExtra(Constans.KEY_ACCOUNT_TYPE,Constans.ACCOUNT_BANK_CARD);
                startActivityForResult(intent,REQUEST_CODE_ADD_ACCOUNT_BANK_CARD);
                popupWindow.dismiss();
            }
        });

        view.findViewById(R.id.tv_alipay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                bundle.putString(Constans.KEY_ACCOUNT_TYPE,Constans.ACCOUNT_ALIPAY);
//                gotoActivity(AddAccountCreditedActivity.class,bundle);

                intent.putExtra(Constans.KEY_ACCOUNT_TYPE,Constans.ACCOUNT_ALIPAY);
                startActivityForResult(intent,REQUEST_CODE_ADD_ACCOUNT_ALIPAY);
                popupWindow.dismiss();
            }
        });

        view.findViewById(R.id.tv_weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                bundle.putString(Constans.KEY_ACCOUNT_TYPE,Constans.ACCOUNT_WEIXIN);
//                gotoActivity(AddAccountCreditedActivity.class,bundle);

                intent.putExtra(Constans.KEY_ACCOUNT_TYPE,Constans.ACCOUNT_WEIXIN);
                startActivityForResult(intent,REQUEST_CODE_ADD_ACCOUNT_WEIXIN);
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DebugLog.i("resultCode: "+resultCode);
        if (resultCode == 4) {
            initUserInfo();
        }
    }
}
