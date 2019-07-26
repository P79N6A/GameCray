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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockchain.cray.R;
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
import java.util.HashMap;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

public class AddAccountCreditedActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_comm_title)
    TextView tvTitle;
    @BindView(R.id.ll_pay_alipay_weixin)
    LinearLayout llPay_alipay_weixin;
    @BindView(R.id.ll_pay_bank_card)
    LinearLayout llPay_bank_card;
    @BindView(R.id.et_bank_name)
    EditText etBankName;
    @BindView(R.id.et_bank_account_name)
    EditText etBankAccountName;
    @BindView(R.id.et_bank_number)
    EditText etBankAccountNumber;

    @BindView(R.id.rl_select)
    RelativeLayout rlSelect;
    @BindView(R.id.iv_upload)
    ImageView ivUpload;
    @BindView(R.id.bar)
    ProgressBar progressBar;
    @BindView(R.id.tv_start_upload)
    TextView tvStartUpload;
    @BindView(R.id.et_weixin_alipay_username)
    EditText etWeixinAlipayUsername;
    @BindView(R.id.et_weixin_alipay_account)
    EditText etWeixinAlipayAccount;
    @BindView(R.id.tv_confirm)
    TextView confirm;

    private UIDisplayer mUIDisplayer;
    private OssService mService;
    private String mImgEndpoint = "https://oss-cn-beijing.aliyuncs.com";
    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageService mIMGService;
    private String mPicturePath;
    private String MOBILE;
    private String UUIDSTR = UUID.randomUUID().toString();
    private String picFileName;
    private String picUrl;
    private String accountType;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_account_credited);
        ButterKnife.bind(this);
        MOBILE = (String)mSp.get(Constans.KEY_MOBILE,"");
        accountType = getIntent().getStringExtra(Constans.KEY_ACCOUNT_TYPE);
        if (accountType.equals(Constans.ACCOUNT_WEIXIN)) {
            llPay_alipay_weixin.setVisibility(View.VISIBLE);
            llPay_bank_card.setVisibility(View.GONE);
            tvTitle.setText(getString(R.string.title_account_weixin));
        }else if (accountType.equals(Constans.ACCOUNT_BANK_CARD)){
            llPay_alipay_weixin.setVisibility(View.GONE);
            llPay_bank_card.setVisibility(View.VISIBLE);
            tvTitle.setText(getString(R.string.title_account_bank_card));
        }else if (accountType.equals(Constans.ACCOUNT_ALIPAY)){
            llPay_alipay_weixin.setVisibility(View.VISIBLE);
            llPay_bank_card.setVisibility(View.GONE);
            tvTitle.setText(getString(R.string.title_account_alipay));
        }
        picFileName = UUIDSTR+"-"+MOBILE+"-"+accountType;
        initUpload();

    }

    private void initUpload() {

        mUIDisplayer = new UIDisplayer(ivUpload, progressBar, tvStartUpload, this);
        mService = initOSS(Config.OSS_ENDPOINT, Config.BUCKET_NAME, mUIDisplayer);
        //设置上传的callback地址，目前暂时只支持putObject的回调
        mService.setCallbackAddress(Config.OSS_CALLBACK_URL);

        //图片服务和OSS使用不同的endpoint，但是可以共用SDK，因此只需要初始化不同endpoint的OssService即可
        mIMGService = new ImageService(initOSS(mImgEndpoint, Config.BUCKET_NAME, mUIDisplayer));
    }

    public OssService initOSS(String endpoint, String bucket, UIDisplayer displayer) {

//        移动端是不安全环境，不建议直接使用阿里云主账号ak，sk的方式。建议使用STS方式。具体参
//        https://help.aliyun.com/document_detail/31920.html
//        注意：SDK 提供的 PlainTextAKSKCredentialProvider 只建议在测试环境或者用户可以保证阿里云主账号AK，SK安全的前提下使用。具体使用如下
//        主账户使用方式
//        String AK = "******";
//        String SK = "******";
//        credentialProvider = new PlainTextAKSKCredentialProvider(AK,SK)
//        以下是使用STS Sever方式。
//        如果用STS鉴权模式，推荐使用OSSAuthCredentialProvider方式直接访问鉴权应用服务器，token过期后可以自动更新。
//        详见：https://help.aliyun.com/document_detail/31920.html
//        OSSClient的生命周期和应用程序的生命周期保持一致即可。在应用程序启动时创建一个ossClient，在应用程序结束时销毁即可。

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

    @OnClick({R.id.iv_back,R.id.rl_select,R.id.tv_start_upload,R.id.tv_confirm})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_select:
                requestWritePermission();
                selectPicture();
                break;
            case R.id.tv_start_upload:
//                startUpload();
                break;
            case R.id.tv_confirm:
                doAddPayAccount();
                break;
        }
    }

    private void startUpload() {
        if (null == picFileName) {
            showToast("圖片名稱為空");
            return;
        }

        if (null == mPicturePath) {
            showToast("圖片路徑為空");
            return;
        }

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
                                picUrl = jb.optString("url");
                                DebugLog.i("picUrl: "+ picUrl);
//                                doAddPayAccount(picUrl);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void doAddPayAccount() {
        int resultCode;
        String weixinPayName = null;
        String weixinPayAccount = null;
        String weixinPayCodeUrl = null;
        String aliPayName = null;
        String aliPayAccount = null;
        String aliPayCodeUrl= null;
        String unionPayName = null;
        String unionPayAccount = null;
        String unionPayBankName = null;

        String weixinAlipayUsername = etWeixinAlipayUsername.getText().toString();
        String weixinAlipayAccount = etWeixinAlipayAccount.getText().toString();
        if (accountType.equals(Constans.ACCOUNT_WEIXIN)) {

            if (TextUtils.isEmpty(weixinAlipayUsername)) {
                showToast("請輸入微信名稱");
                return;
            }
            if (TextUtils.isEmpty(weixinAlipayAccount)) {
                showToast("請輸入微信賬號");
                return;
            }
            if (TextUtils.isEmpty(picUrl)) {
                showToast("請上傳微信收款碼");
                return;
            }

            weixinPayName = weixinAlipayUsername;
            weixinPayAccount = weixinAlipayAccount;
            weixinPayCodeUrl = picUrl;
        }else if (accountType.equals(Constans.ACCOUNT_ALIPAY)){
            if (TextUtils.isEmpty(weixinAlipayUsername)) {
                showToast("請輸入支付寶名稱");
                return;
            }
            if (TextUtils.isEmpty(weixinAlipayAccount)) {
                showToast("請輸入支付寶賬號");
                return;
            }
            if (TextUtils.isEmpty(picUrl)) {
                showToast("請上傳支付寶收款碼");
                return;
            }
            aliPayName = weixinAlipayUsername;
            aliPayAccount = weixinAlipayAccount;
            aliPayCodeUrl = picUrl;
        }else if (accountType.equals(Constans.ACCOUNT_BANK_CARD)){
            if (TextUtils.isEmpty(etBankName.getText().toString())) {
                showToast(getString(R.string.input_account_bank_card_name));
                return;
            }
            if (TextUtils.isEmpty(etBankAccountName.getText().toString())) {
                showToast(getString(R.string.intput_account_bank_card_account_name));
                return;
            }
            if (TextUtils.isEmpty(etBankAccountNumber.getText().toString())) {
                showToast(getString(R.string.input_account_bank_card_account_number));
                return;
            }

            unionPayName = etBankAccountName.getText().toString();
            unionPayAccount =etBankAccountNumber.getText().toString();
            unionPayBankName = etBankName.getText().toString();

        }

        mLoadView.onShow();
        mHttpCommand.updateUserInfo(null,weixinPayCodeUrl,weixinPayAccount,weixinPayName,aliPayAccount,aliPayName,
                                       aliPayCodeUrl,unionPayAccount,unionPayName,unionPayBankName,new HttpCmdCallback() {
            @Override
            public void onSucceed(Response response) {
                try {
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        if (null != result) {
                            JSONObject jb = new JSONObject(result);
                            int code = jb.optInt("code");
                            if (code == Constans.REQUEST_SUCCESSFUL) {
                                showToast(getString(R.string.account_successfully_added));
                                setResult(4);
                                finish();
                            }else if (code == Constans.ACCOUNT_ALIPAY_REPEATED){
                                showToast(getString(R.string.account_pay_alipay_repeat));
                            }else if (code == Constans.ACCOUNT_WEIXINPAY_REPEATED){
                                showToast(getString(R.string.account_pay_weixin_repeat));
                            }else if (code == Constans.ACCOUNT_UNIONPAY_REPEATED){
                                showToast(getString(R.string.account_pay_unionpay_repeat));
                            }
                        }else {
                            showToast(getString(R.string.server_net_error));
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                mLoadView.onDismiss();
            }

            @Override
            public void onFail(String message) {
                DebugLog.i("onFail: "+message);
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
            DebugLog.i("Selected picturePath: "+ mPicturePath);
            cursor.close();

            try {
                Bitmap bm = mUIDisplayer.autoResizeFromLocalFile(mPicturePath);
                mUIDisplayer.displayImage(bm);
                File file = new File(mPicturePath);
//                mUIDisplayer.displayInfo("文件: " + mPicturePath + "\n大小: " + String.valueOf(file.length()));
            } catch (IOException e) {
                e.printStackTrace();
                mUIDisplayer.displayInfo(e.toString());
            }

            startUpload();
        }
    }
}
