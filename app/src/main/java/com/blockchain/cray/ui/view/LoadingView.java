package com.blockchain.cray.ui.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockchain.cray.R;
import com.blockchain.cray.utils.UIUtils;

/**
 * Created by huangzy on 2017-08-23. ui loadingView
 */

public class LoadingView extends Dialog {
    private Activity context;
    private ImageView mSpinnerImageView;
    private AnimationDrawable mSpinner;
    private TextView mTextView;
    private String message;

    public LoadingView(@NonNull Activity context, String message) {
        this(context, message, R.style.CommDialog);
    }

    public LoadingView(@NonNull Activity context) {
        this(context, null, R.style.CommDialog);
    }

    private LoadingView(@NonNull Activity context, String message, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.message = message;
        initView();
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.loading_dialog, null);
        mSpinnerImageView = view.findViewById(R.id.iv_spinnerImageView);
        mTextView = view.findViewById(R.id.tv_message);
        setContentView(view);
        setMessage();

        getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            mSpinner = (AnimationDrawable) mSpinnerImageView.getBackground();
            assert mSpinner != null;
            mSpinner.start();
        }
    }

    private void setMessage() {
        mTextView.setText(message);
    }

    public void onShow() {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                if (!isShowing()) {
                    LoadingView.this.show();
                }
            }
        });

    }

    public void onDismiss() {

        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                if (isShowing()) {
                    LoadingView.this.dismiss();
                }
            }
        });
    }
}
