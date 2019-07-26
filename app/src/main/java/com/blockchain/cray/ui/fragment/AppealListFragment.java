package com.blockchain.cray.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.alibaba.fastjson.JSON;
import com.blockchain.cray.R;
import com.blockchain.cray.bean.CrayAdoptionBean;
import com.blockchain.cray.net.HttpCmdCallback;
import com.blockchain.cray.ui.adapter.AdoptionRecordAdapter;
import com.blockchain.cray.utils.Constans;
import com.blockchain.cray.utils.DebugLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import okhttp3.Response;

/**
 * 领养记录中的取消/申诉 列表
 */
public class AppealListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_reservation_record)
    RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private int mLastVisibleItem;
    private int page;// 分页页码
    private static final int START_PAGE = 0;
    private List<CrayAdoptionBean.AttachBean.ListBean> dataList = new ArrayList<>();
    private AdoptionRecordAdapter mAdoptionRecordAdapter;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_appeal, null);
        ButterKnife.bind(this,view);

        initSwipeRefreshLayout();
        initDatas();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void initDatas() {
        httpCommand.getCrayfishAdoptionList(3,"",new HttpCmdCallback() {
            @Override
            public void onSucceed(Response response) {
                String jsonString;
                try {
                    if (response.isSuccessful()) {
                        jsonString = response.body().string();
                        DebugLog.i("getCrayfish Appeal list,jsonString info: "+jsonString);
                        CrayAdoptionBean crayAdoptionBean = JSON.parseObject(jsonString, CrayAdoptionBean.class);
                        if (crayAdoptionBean.getCode() == Constans.REQUEST_SUCCESSFUL) {
                            List<CrayAdoptionBean.AttachBean.ListBean> list = crayAdoptionBean.getAttach().getList();
                            if (null != list) {
                                dataList.addAll(list);

                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setRefreshStatues();
            }

            @Override
            public void onFail(String message) {
                setRefreshStatues();
            }
        });
    }

    private void setRefreshStatues() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                mAdoptionRecordAdapter.notifyDataSetChanged();
            }
        });
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
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdoptionRecordAdapter = new AdoptionRecordAdapter(getActivity(),dataList);
        SlideInBottomAnimationAdapter slideAdapter = new SlideInBottomAnimationAdapter(mAdoptionRecordAdapter);
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
                        mLastVisibleItem + 1 == mAdoptionRecordAdapter.getItemCount() &&
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

    @Override
    public void onRefresh() {
        // 下拉刷新
        page = START_PAGE;
        initDatas();
    }
}
