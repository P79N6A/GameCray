package com.blockchain.cray.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;


@SuppressLint("AppCompatCustomView")
public class FocusEditText extends EditText {
    private String hint="";

    public FocusEditText(Context context) {
        super(context);
        init();
    }

    public FocusEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        hint = getHint().toString();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
//        if (focused) {
//            setHint("");
//        } else {
//            setHint(hint);
//        }
        setHint(hint);
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }
}
