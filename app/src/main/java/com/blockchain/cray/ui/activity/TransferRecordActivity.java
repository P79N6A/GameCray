package com.blockchain.cray.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockchain.cray.R;
import com.blockchain.cray.ui.fragment.TransferAppealFragment;
import com.blockchain.cray.ui.fragment.TransferCompleteFragment;
import com.blockchain.cray.ui.fragment.TransferToFragment;
import com.blockchain.cray.ui.fragment.TransferingFragment;
import com.blockchain.cray.ui.view.FragmentPagerItemAdapter;
import com.blockchain.cray.ui.view.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransferRecordActivity extends BaseActivity implements SmartTabLayout.OnTabClickListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.vp_main)
    ViewPager viewPager;
    @BindView(R.id.stl_main)
    SmartTabLayout smartTabLayout;
    private FragmentManager fragmentManager;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_transfer_record);
        ButterKnife.bind(this);
        tvTitle.setText(getString(R.string.tab_my_msg_transfer_record));
        init();
    }

    private void init() {
        fragmentManager = getSupportFragmentManager();
        smartTabLayout.setOnTabClickListener(this);

        FragmentPagerItems.Creator creator = FragmentPagerItems.with(this)
                .add(getString(R.string.record_cray_to_be_transferred), TransferToFragment.class)
                .add(getString(R.string.record_cray_transfering), TransferingFragment.class)
                .add(getString(R.string.record_cray_transfer_complete), TransferCompleteFragment.class)
                .add(getString(R.string.record_cray_appeal), TransferAppealFragment.class);

        FragmentPagerItemAdapter pagerItemAdapter = new FragmentPagerItemAdapter(fragmentManager, creator.create());
        viewPager.setAdapter(pagerItemAdapter);
        viewPager.addOnPageChangeListener(this);
        smartTabLayout.setViewPager(viewPager);

    }

    @Override
    public void onTabClicked(int position) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

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
