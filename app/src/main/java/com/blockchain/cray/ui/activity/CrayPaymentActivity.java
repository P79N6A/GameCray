package com.blockchain.cray.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blockchain.cray.R;
import com.blockchain.cray.bean.CrayOrderDetailBean;
import com.blockchain.cray.net.HttpCmdCallback;
import com.blockchain.cray.oss.Config;
import com.blockchain.cray.oss.ImageService;
import com.blockchain.cray.utils.Constans;
import com.blockchain.cray.utils.DebugLog;
import com.blockchain.ossuploadlib.ClientConfiguration;
import com.blockchain.ossuploadlib.OSS;
import com.blockchain.ossuploadlib.OSSClient;
import com.blockchain.ossuploadlib.OssService;
import com.blockchain.ossuploadlib.OssUploadCallback;
import com.blockchain.ossuploadlib.UIDisplayer;
import com.blockchain.ossuploadlib.common.auth.OSSAuthCredentialsProvider;
import com.blockchain.ossuploadlib.common.auth.OSSCredentialProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

public class CrayPaymentActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_comm_title)
    TextView tvTitle;
    @BindView(R.id.payment_consumption_differential)
    TextView costDifferential;
    @BindView(R.id.payment_payment_transfer)
    TextView transferName;
    @BindView(R.id.payment_payment_tv_transfer_phone)
    TextView transferPhoneNumber;
    @BindView(R.id.payment_payment_tv_amount)
    TextView paymentAmount;

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

    @BindView(R.id.rl_upload)
    RelativeLayout rlUpload;
    @BindView(R.id.iv_upload)
    ImageView ivUpload;
    @BindView(R.id.bar)
    ProgressBar progressBar;
    @BindView(R.id.output_info)
    TextView outputInfo;

    @BindView(R.id.et_pay_psw)
    EditText etPaymentPsw;

    private static final int RESULT_LOAD_IMAGE = 1;
    private String mPicturePath;
    private String UUIDSTR = UUID.randomUUID().toString();
    private String picFileName;
    private UIDisplayer mUIDisplayer;
    private OssService mService;
    private String mImgEndpoint = "https://oss-cn-beijing.aliyuncs.com";
    private String orderId;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cray_payment);
        ButterKnife.bind(this);
        tvTitle.setText(getString(R.string.buyer_payment));
        orderId = getIntent().getStringExtra(Constans.KEY_ADOPTION_ID);
        getPaymentDetailData(orderId);
        picFileName = UUIDSTR+"-"+ orderId;

        initUpload();
    }

    private void initUpload() {

        mUIDisplayer = new UIDisplayer(ivUpload, progressBar, outputInfo, this);
        mService = initOSS(Config.OSS_ENDPOINT, Config.BUCKET_NAME, mUIDisplayer);
        //设置上传的callback地址，目前暂时只支持putObject的回调
        mService.setCallbackAddress(Config.OSS_CALLBACK_URL);
        ImageService mIMGService = new ImageService(initOSS(mImgEndpoint, Config.BUCKET_NAME, mUIDisplayer));
    }

    public OssService initOSS(String endpoint, String bucket, UIDisplayer displayer) {

        //使用自己的获取STSToken的类
        OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(Config.STS_SERVER_URL);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
        return new OssService(oss, bucket, displayer);
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
                                }else if (crayOrderDetailBean.getCode() == Constans.PAYMENT_ERROR){
                                    showToast("交易單不存在");
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
                costDifferential.setText(crayOrder.getIntegralCost());
                transferName.setText(crayOrder.getSellerNickName());
                transferPhoneNumber.setText(crayOrder.getSellerMobile());
                paymentAmount.setText(crayOrder.getWorth());

                initAliPay(crayOrder);
                initWeixinPay(crayOrder);
                initUnionPay(crayOrder);
            }
        });
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

    @OnClick({R.id.iv_back,R.id.rl_upload,R.id.tv_commit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_upload:
                requestWritePermission();
                selectPicture();
                break;
            case R.id.tv_commit:
                uploadPic();
                break;
        }
    }

    private void uploadPic() {
        if (null == picFileName) {
            return;
        }

        if (null == mPicturePath) {
            return;
        }
        //上传图片
        mService.asyncPutImage(picFileName, mPicturePath, new OssUploadCallback() {
            @Override
            public void onUploadResult(String result) {
                try {
                    if (null != result) {
                        DebugLog.i("picUrl: "+result);
                        JSONObject jb = new JSONObject(result);
                        String status = jb.optString("Status");
                        if (null != status) {
                            if ("OK".equals(status)) {
                                String picUrl = jb.optString("url");
                                DebugLog.i("picUrl: "+picUrl);
                                doCommit(picUrl);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void doCommit(String picUrl) {
        if (null == picUrl) {
            showToast("請上傳支付憑證！");
            return;
        }

        String payPsw = etPaymentPsw.getText().toString();
        if (TextUtils.isEmpty(payPsw)) {
            showToast("支付秘密不能為空！");
            return;
        }
        mLoadView.onShow();
        mHttpCommand.getCrayfishTransPay(orderId, payPsw,picUrl, new HttpCmdCallback() {

            @Override
            public void onSucceed(Response response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        if (null != result) {
                            JSONObject jb = new JSONObject(result);
                            int code = jb.optInt("code");
                            if (code == Constans.REQUEST_SUCCESSFUL) {
                                showToast(getString(R.string.cray_payment_success));
                                finish();
                            }else if (code == 20021){
                                showToast(getString(R.string.cray_payment_error_20021));
                            }else if (code == 20022){
                                showToast(getString(R.string.cray_payment_error_20022));
                            }else if (code == 20023){
                                showToast(getString(R.string.cray_payment_error_20023));
                            }else if (code == 20024){
                                showToast(getString(R.string.cray_payment_error_20024));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mLoadView.onDismiss();
            }

            @Override
            public void onFail(String message) {
                mLoadView.onDismiss();
                showToast(getString(R.string.server_net_error));
            }
        });
    }

    private void selectPicture() {
       Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
       startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mPicturePath = cursor.getString(columnIndex);
            Log.d("PickPicture", mPicturePath);
            DebugLog.i("Selected picturePath: "+ mPicturePath);
            cursor.close();

            try {
                Bitmap bm = mUIDisplayer.autoResizeFromLocalFile(mPicturePath);
                mUIDisplayer.displayImage(bm);
                File file = new File(mPicturePath);
                mUIDisplayer.displayInfo("文件: " + mPicturePath + "\n大小: " + String.valueOf(file.length()));
            } catch (IOException e) {
                e.printStackTrace();
                mUIDisplayer.displayInfo(e.toString());
                DebugLog.i("Selected picturePath IOException: "+e.toString());
            }
        }
    }
}
