package com.blockchain.cray.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.blockchain.cray.R;
import com.blockchain.cray.bean.CrayReservationBean;
import com.blockchain.cray.net.HttpCmdCallback;
import com.blockchain.cray.ui.adapter.ReservationRecordAdapter;
import com.blockchain.cray.utils.DebugLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import okhttp3.Response;

public class ReservationRecordActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_reservation_record)
    RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private int mLastVisibleItem;
    private int page;// 分页页码
    private static final int START_PAGE = 0;
    private List<CrayReservationBean.AttachBean.ListBean> list = new ArrayList<>();
    private ReservationRecordAdapter mReservationRecordAdapter;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_reservation_record);
        ButterKnife.bind(this);
        initSwipeRefreshLayout();
        initDatas();
    }

    private void initSwipeRefreshLayout() {

        //设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
        //swipe_refresh_layout.setProgressViewOffset(true, 150, 500);

        //设置下拉圆圈的大小，两个值 LARGE， DEFAULT
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        swipeRefreshLayout.setColorSchemeResources(
                R.color.color_swl,
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // 通过 setEnabled(false) 禁用下拉刷新
        swipeRefreshLayout.setEnabled(true);
        // 设定下拉圆圈的背景
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white);
        // 第一次进来，为加载数据的状态
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);

        initRecyclerView();
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mReservationRecordAdapter = new ReservationRecordAdapter(this,list);
        SlideInBottomAnimationAdapter slideAdapter = new SlideInBottomAnimationAdapter(mReservationRecordAdapter);
        //设置不只第一次有动画
        slideAdapter.setFirstOnly(false);
        slideAdapter.setDuration(500);
        slideAdapter.setInterpolator(new OvershootInterpolator(0.5f));
        //设置只有第一次有动画
        slideAdapter.setFirstOnly(false);
        recyclerView.setAdapter(slideAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        mLastVisibleItem + 1 == mReservationRecordAdapter.getItemCount() &&
                        !swipeRefreshLayout.isRefreshing()) {
                    // 如果到底部了，而且不是正在加载状态，就变为正在加载状态，并及时去加载数据
                    swipeRefreshLayout.setRefreshing(true);
                    // 加载更多
                    page++;
                    initDatas();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void initDatas(){
        list.clear();
        mHttpCommand.getCrayfishReservationList(1,new HttpCmdCallback() {
            @Override
            public void onSucceed(Response response) {
                if (response.isSuccessful()) {
                    try {
                        String jsonString = response.body().string();
                        DebugLog.i("CrayfishReservation,jsonString info: "+jsonString);
                        CrayReservationBean crayReservation = JSON.parseObject(jsonString, CrayReservationBean.class);
                        if (crayReservation.isSuccess()) {
                            List<CrayReservationBean.AttachBean.ListBean> dataList = crayReservation.getAttach().getList();
                            if (dataList != null) {
                                list.addAll(dataList);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                setRefreshStatues();
            }

            @Override
            public void onFail(String message) {
                setRefreshStatues();
            }
        });
    }

    private void setRefreshStatues(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                mReservationRecordAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onRefresh() {
       DebugLog.d("onRefresh =====>>");
        // 下拉刷新
        page = START_PAGE;
        initDatas();
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
