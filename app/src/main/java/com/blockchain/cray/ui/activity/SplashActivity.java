package com.blockchain.cray.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blockchain.cray.R;
import com.blockchain.cray.utils.Constans;
import com.blockchain.cray.utils.SharedPreferencesUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isLogin = (boolean) SharedPreferencesUtil.getInstance(SplashActivity.this).get(Constans.KEY_ISLOGIN, false);
                if (isLogin) {
                   startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }else {
                   startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
               finish();
            }
        },1500);


    }
}
