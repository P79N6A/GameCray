package com.blockchain.cray.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockchain.cray.R;
import com.blockchain.cray.ui.fragment.AdoptedListFragment;
import com.blockchain.cray.ui.fragment.AdoptingListFragment;
import com.blockchain.cray.ui.fragment.AppealListFragment;
import com.blockchain.cray.ui.view.FragmentPagerItemAdapter;
import com.blockchain.cray.ui.view.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdoptingRecordActivity extends BaseActivity implements SmartTabLayout.OnTabClickListener, ViewPager.OnPageChangeListener {

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
        setContentView(R.layout.activity_adopting_record);
        ButterKnife.bind(this);
        tvTitle.setText(getString(R.string.tab_my_msg_adopting_record));
        init();
    }

    private void init() {
        fragmentManager = getSupportFragmentManager();
        smartTabLayout.setOnTabClickListener(this);

        FragmentPagerItems.Creator creator = FragmentPagerItems.with(this)
                .add(getString(R.string.record_cray_adopting), AdoptingListFragment.class)
                .add(getString(R.string.record_cray_adopted), AdoptedListFragment.class)
                .add(getString(R.string.record_cray_appeal), AppealListFragment.class);

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
