package com.blockchain.cray.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.blockchain.cray.R;

public class CountDownTimerUtils extends CountDownTimer {

    private TextView tvGetVerifyCode;
    private Context context;
    public CountDownTimerUtils(Context context,TextView textView,long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.tvGetVerifyCode = textView;
        this.context = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long reclen) {
        tvGetVerifyCode.setClickable(false);
        tvGetVerifyCode.setText(reclen/1000+context.getString(R.string.register_resend_code));
        tvGetVerifyCode.setBackground(context.getDrawable(R.drawable.background_register_get_un_verifycode_bg));
        DebugLog.d("onTick reclen: "+reclen/1000);
    }

    @Override
    public void onFinish() {
        tvGetVerifyCode.setClickable(true);
        tvGetVerifyCode.setText(context.getString(R.string.register_send_code));
        tvGetVerifyCode.setBackground(context.getDrawable(R.drawable.background_comm_commit_bg));
        DebugLog.d("onFinish ====>> ");
    }


}
