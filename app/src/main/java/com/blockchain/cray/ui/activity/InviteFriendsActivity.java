package com.blockchain.cray.ui.activity;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockchain.cray.R;
import com.blockchain.cray.utils.DebugLog;
import com.blockchain.zxinglib.encode.CodeCreator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.blockchain.cray.utils.Constans.KEY_QRCODE;

public class InviteFriendsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_qrCode)
    ImageView ivQRCode;
    @BindView(R.id.tv_head_comm_title)
    TextView tvTitle;
    @BindView(R.id.tv_invitation_code)
    TextView tvInvitationCode;
    @BindView(R.id.tv_scanning_qr_code)
    TextView tvScanningQrCode;
    @BindView(R.id.iv_copy_invitation_code)
    Button btnCopyInvitationCode;
    @BindView(R.id.iv_generate_qrCode)
    Button btnGenerateQrCode;

    private  String invitationCode;
    private ClipboardManager clipboardManager;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_invite_friends);
        ButterKnife.bind(this);
        tvTitle.setText(getString(R.string.title_invite_friend));

        invitationCode = (String) mSp.get(KEY_QRCODE,"");
        tvInvitationCode.setText(invitationCode);
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @OnClick({R.id.iv_back,R.id.iv_copy_invitation_code,R.id.iv_generate_qrCode})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_copy_invitation_code:
                ClipData clipData = ClipData.newPlainText("Label", invitationCode);
                clipboardManager.setPrimaryClip(clipData);
                showToast(getString(R.string.title_invitation_code_copied));
                break;
            case R.id.iv_generate_qrCode:
                createQRCode();
                break;
        }
    }

    private void createQRCode(){

        if (TextUtils.isEmpty(invitationCode)) {
            showToast("请输入要生成二维码图片的字符串");
            return;
        }
        Bitmap bitmap = CodeCreator.createQRCode(invitationCode, 400, 400, null);
        if (bitmap != null) {
            DebugLog.d("已生成二维码图片");
            ivQRCode.setImageBitmap(bitmap);
            tvScanningQrCode.setText(getString(R.string.title_scanning_qr_code));
        }
    }
}
