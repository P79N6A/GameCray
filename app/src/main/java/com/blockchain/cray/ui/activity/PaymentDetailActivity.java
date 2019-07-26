package com.blockchain.cray.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blockchain.cray.R;
import com.blockchain.cray.bean.CrayOrderDetailBean;
import com.blockchain.cray.net.HttpCmdCallback;
import com.blockchain.cray.utils.Constans;
import com.blockchain.cray.utils.DebugLog;
import com.bumptech.glide.Glide;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

import static com.blockchain.cray.utils.Constans.CRAY_ADAPTION_STATUS_APPEALING;
import static com.blockchain.cray.utils.Constans.CRAY_ADAPTION_STATUS_CANCEL;
import static com.blockchain.cray.utils.Constans.CRAY_ADAPTION_STATUS_EARNING;
import static com.blockchain.cray.utils.Constans.CRAY_ADAPTION_STATUS_EARNING_FINISHIED;
import static com.blockchain.cray.utils.Constans.CRAY_ADAPTION_STATUS_LEAVE_UNUSED;
import static com.blockchain.cray.utils.Constans.CRAY_ADAPTION_STATUS_PAYMENTED;
import static com.blockchain.cray.utils.Constans.CRAY_ADAPTION_STATUS_UN_PAYMENT;

/**
 * 订单详情
 */
public class PaymentDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_comm_title)
    TextView tvTitle;
    @BindView(R.id.tv_order_cray_number)
    TextView tvCrayNumber;
    @BindView(R.id.tv_order_cray_name)
    TextView tvCrayName;
    @BindView(R.id.tv_order_cray_rule)
    TextView tvCrayRule;
    @BindView(R.id.tv_order_cray_transfer_time)
    TextView tvCrayTransferTime;
    @BindView(R.id.tv_cray_pay_time)
    TextView tvCrayPayTime;

    @BindView(R.id.tv_cray_transfer)
    TextView tvCrayTransfer;
    @BindView(R.id.tv_cray_transfer_mobile)
    TextView tvCrayTransferMobile;
    @BindView(R.id.tv_buyer_Name)
    TextView tvBuyerName;
    @BindView(R.id.tv_buyer_mobile)
    TextView tvBuyerMobile;
    @BindView(R.id.tv_order_cray_status)
    TextView tvOrderCrayStatus;
    @BindView(R.id.tv_order_income_status)
    TextView tvOrderIncomeStatus;

    @BindView(R.id.rl_account_alipay)
    RelativeLayout rlAccountAlipay;
    @BindView(R.id.tv_alipay_name)
    TextView tvAlipayAccount;
    @BindView(R.id.tv_alipay_number)
    TextView tvAlipayNumber;
    @BindView(R.id.tv_account_type_alipay)
    TextView tvAccountTypeAlipay;
    @BindView(R.id.view_line1)
    View viewLine1;

    @BindView(R.id.rl_account_weixin)
    RelativeLayout rlAccountWeixin;
    @BindView(R.id.tv_weixin_name)
    TextView tvWeixinAccount;
    @BindView(R.id.tv_weixin_number)
    TextView tvWeixinNumber;
    @BindView(R.id.tv_account_type_weixin)
    TextView tvAccountTypeWeixin;
    @BindView(R.id.view_line2)
    View viewLine2;

    @BindView(R.id.rl_account_unionpay)
    RelativeLayout rlAccountUnionpay;
    @BindView(R.id.tv_unionpay_name)
    TextView tvUnionpayAccount;
    @BindView(R.id.tv_unionpay_number)
    TextView tvUnionpayNumber;
    @BindView(R.id.tv_account_type_unionpay)
    TextView tvAccountTypeUnionpay;
    @BindView(R.id.tv_unionpay_branch_name)
    TextView tvUnionpayBranchName;

    @BindView(R.id.iv_certificate)
    ImageView ivCertificate;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cray_payment_datail);
        ButterKnife.bind(this);
        tvTitle.setText(getString(R.string.cray_payment_detail));

        String id = getIntent().getStringExtra(Constans.KEY_ADOPTION_ID);
        getPaymentDetailData(id);
    }

    private void getPaymentDetailData(String id) {
        mHttpCommand.getCrayfishOrderDetail(id, new HttpCmdCallback() {
            @Override
            public void onSucceed(Response response) {
                if (response.isSuccessful()) {
                    try {
                        String jsonString = response.body().string();
                        DebugLog.i("getCrayfishOrderDetail: "+jsonString);
                        if (null != jsonString) {
                            CrayOrderDetailBean crayOrderDetailBean = JSON.parseObject(jsonString, CrayOrderDetailBean.class);
                            if (null != crayOrderDetailBean) {
                                if (crayOrderDetailBean.getCode() == Constans.REQUEST_SUCCESSFUL) {
                                    CrayOrderDetailBean.AttachBean  crayOrder = crayOrderDetailBean.getAttach();
                                    if (null != crayOrder) {
                                        updateUI(crayOrder);
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    private void updateUI(final CrayOrderDetailBean.AttachBean crayOrder) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvCrayNumber.setText(crayOrder.getId());
                tvCrayName.setText(crayOrder.getCrayFishName());
                tvCrayRule.setText(crayOrder.getIncomeRule());
                tvCrayTransferTime.setText(crayOrder.getTransTime());
                tvCrayPayTime.setText(crayOrder.getPayTime());
                tvCrayTransfer.setText(crayOrder.getSellerNickName());
                tvCrayTransferMobile.setText(crayOrder.getSellerMobile());
                tvBuyerName.setText(crayOrder.getBuyerNickName());
                tvBuyerMobile.setText(crayOrder.getBuyerMobile());
                tvOrderCrayStatus.setText(initStatus(crayOrder.getStatus()));
                tvOrderIncomeStatus.setText(crayOrder.getIncome());

                Glide.with(PaymentDetailActivity.this).load(crayOrder.getCertificateUrl()).into(ivCertificate);

                initAliPay(crayOrder);
                initWeixinPay(crayOrder);
                initUnionPay(crayOrder);
            }
        });
    }

    private String initStatus(String status){
        String statusMsg ="";
        switch (status){
            case CRAY_ADAPTION_STATUS_LEAVE_UNUSED:
                statusMsg = "待領養";
                break;
            case CRAY_ADAPTION_STATUS_UN_PAYMENT:
                statusMsg = "未付款";
                break;
            case CRAY_ADAPTION_STATUS_PAYMENTED:
                statusMsg = "已付款";
                break;
            case CRAY_ADAPTION_STATUS_APPEALING:
                statusMsg = "申訴中";
                break;
            case CRAY_ADAPTION_STATUS_CANCEL:
                statusMsg = "取消";
                break;
            case CRAY_ADAPTION_STATUS_EARNING:
                statusMsg = "收益中";
                break;
            case CRAY_ADAPTION_STATUS_EARNING_FINISHIED:
                statusMsg = "收益已完成";
                break;
        }

        return  statusMsg;
    }

    private void initUnionPay(CrayOrderDetailBean.AttachBean crayOrder) {
        if (TextUtils.isEmpty(crayOrder.getYinlianName())) {
            rlAccountUnionpay.setVisibility(View.GONE);
            viewLine2.setVisibility(View.GONE);
        }else {
            rlAccountUnionpay.setVisibility(View.VISIBLE);
            viewLine2.setVisibility(View.VISIBLE);
            tvUnionpayAccount.setText(crayOrder.getYinlianName());
            tvUnionpayNumber.setText(crayOrder.getYinlianAccount());
            tvUnionpayBranchName.setText(crayOrder.getYinlianBankName());
        }
    }

    private void initWeixinPay(CrayOrderDetailBean.AttachBean crayOrder) {
        if (TextUtils.isEmpty(crayOrder.getWeixinName())) {
            rlAccountWeixin.setVisibility(View.GONE);
            viewLine2.setVisibility(View.GONE);
        }else {
            rlAccountWeixin.setVisibility(View.VISIBLE);
            viewLine2.setVisibility(View.VISIBLE);
            tvWeixinAccount.setText(crayOrder.getWeixinName());
            tvWeixinNumber.setText(crayOrder.getWeixinAccount());
        }
    }

    private void initAliPay(CrayOrderDetailBean.AttachBean crayOrder) {
        if (TextUtils.isEmpty(crayOrder.getZhifubaoName())) {
            rlAccountAlipay.setVisibility(View.GONE);
            viewLine1.setVisibility(View.GONE);
        }else {
            rlAccountAlipay.setVisibility(View.VISIBLE);
            viewLine1.setVisibility(View.VISIBLE);
            tvAlipayAccount.setText(crayOrder.getZhifubaoName());
            tvAlipayNumber.setText(crayOrder.getZhifubaoAccount());
        }
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
