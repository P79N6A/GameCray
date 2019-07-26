package com.blockchain.cray.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockchain.cray.R;
import com.blockchain.cray.ui.fragment.MarketFragment;
import com.blockchain.cray.ui.fragment.MyFragment;
import com.blockchain.cray.ui.fragment.ServiceCenterFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivHome,ivService,ivMe;
    private TextView tvHome,tvService,tvMe;
    private RelativeLayout rlHome,rlService,rlMe;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private static final int TAB_HOME = 0;//虾虾集市
    private static final int TAB_SERVICE = 1;//服务中心
    private static final int TAB_ME = 2;//个人中心
    private int mCurrentSelection = -1;

    private MarketFragment mMarketFragment;
    private MyFragment mMeFragment;
    private ServiceCenterFragment mServiceCenterFragment;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        rlHome = findViewById(R.id.rl_home);
        ivHome = findViewById(R.id.iv_home);
        tvHome = findViewById(R.id.tv_home);

        rlService = findViewById(R.id.rl_service);
        ivService = findViewById(R.id.iv_service);
        tvService = findViewById(R.id.tv_service);

        rlMe = findViewById(R.id.rl_me);
        ivMe = findViewById(R.id.iv_me);
        tvMe = findViewById(R.id.tv_me);

        mFragmentManager = getSupportFragmentManager();

        rlHome.setOnClickListener(this);
        rlService.setOnClickListener(this);
        rlMe.setOnClickListener(this);

        setTabSelection(TAB_HOME);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_home:
                setTabSelection(TAB_HOME);
                break;
            case R.id.rl_service:
                setTabSelection(TAB_SERVICE);
                break;
            case R.id.rl_me:
                setTabSelection(TAB_ME);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (mCurrentSelection){
            case TAB_HOME:
                setTabSelection(TAB_HOME);
                break;
            case TAB_SERVICE:
                setTabSelection(TAB_SERVICE);
                break;
            case TAB_ME:
                setTabSelection(TAB_ME);
                break;
            default:
                setTabSelection(TAB_HOME);
                break;
        }
    }

    private void setTabSelection(int iPage) {
        if (iPage == mCurrentSelection) {
            return;
        }
        mCurrentSelection = iPage;
        mFragmentTransaction = mFragmentManager.beginTransaction();
        clearSelection();
        hideFragments(mFragmentTransaction);

        switch (iPage){
            case TAB_HOME:
                ivHome.setBackgroundResource(R.mipmap.tab_home_selected);
                tvHome.setTextColor(getResources().getColor(R.color.text_selected));

                mMarketFragment = new MarketFragment();
                mFragmentTransaction.add(R.id.fl_content, mMarketFragment);

//                if (mMarketFragment == null) {
//                    mMarketFragment = new MarketFragment();
//                    mFragmentTransaction.add(R.id.fl_content, mMarketFragment);
//                } else {
//                    mFragmentTransaction.show(mMarketFragment);
//                }
                break;
             //#F0F0F0
            case TAB_SERVICE:
                ivService.setBackgroundResource(R.mipmap.tab_service_select);
                tvService.setTextColor(getResources().getColor(R.color.text_selected));

                mServiceCenterFragment = new ServiceCenterFragment();
                mFragmentTransaction.add(R.id.fl_content, mServiceCenterFragment);

//                if (mServiceCenterFragment == null) {
//                    mServiceCenterFragment = new ServiceCenterFragment();
//                    mFragmentTransaction.add(R.id.fl_content, mServiceCenterFragment);
//                } else {
//                    mFragmentTransaction.show(mServiceCenterFragment);
//                }
                break;

            case TAB_ME:
                ivMe.setBackgroundResource(R.mipmap.tab_me_selected);
                tvMe.setTextColor(getResources().getColor(R.color.text_selected));

                mMeFragment = new MyFragment();
                mFragmentTransaction.add(R.id.fl_content, mMeFragment);

//                if (mMeFragment == null) {
//                    mMeFragment = new MyFragment();
//                    mFragmentTransaction.add(R.id.fl_content, mMeFragment);
//                } else {
//                    mFragmentTransaction.show(mMeFragment);
//                }
                break;
        }

        mFragmentTransaction.commit();
        mFragmentManager.executePendingTransactions();
    }

    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (mMarketFragment != null) {
//            fragmentTransaction.hide(mMarketFragment);
            fragmentTransaction.remove(mMarketFragment);
        }

        if (mServiceCenterFragment != null) {
//            fragmentTransaction.hide(mServiceCenterFragment);
            fragmentTransaction.remove(mServiceCenterFragment);
        }

        if (mMeFragment != null) {
//            fragmentTransaction.hide(mMeFragment);
            fragmentTransaction.remove(mMeFragment);
        }
    }

    private void clearSelection() {

        ivHome.setBackgroundResource(R.mipmap.tab_home_unselected);
        tvHome.setTextColor(getResources().getColor(R.color.white));

        ivService.setBackgroundResource(R.mipmap.tab_service_unselect);
        tvService.setTextColor(getResources().getColor(R.color.white));

        ivMe.setBackgroundResource(R.mipmap.tab_me_unselected);
        tvMe.setTextColor(getResources().getColor(R.color.white));
    }
}
