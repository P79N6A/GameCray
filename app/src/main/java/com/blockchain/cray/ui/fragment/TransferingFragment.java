package com.blockchain.cray.ui.fragment;

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
import com.blockchain.cray.bean.CrayTransferBean;
import com.blockchain.cray.net.HttpCmdCallback;
import com.blockchain.cray.ui.adapter.TransferRecordAdapter;
import com.blockchain.cray.utils.Constans;
import com.blockchain.cray.utils.DebugLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import okhttp3.Response;

import static com.blockchain.cray.utils.Constans.CRAY_TANSFER_ORDER_STATUS_ERROR_20021;
import static com.blockchain.cray.utils.Constans.CRAY_TANSFER_ORDER_STATUS_ERROR_20022;

public class TransferingFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, TransferRecordAdapter.OnConfirmListener, TransferRecordAdapter.OnAppeallListener {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_reservation_record)
    RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private int mLastVisibleItem;
    private int page;// 分页页码
    private static final int START_PAGE = 0;
    private List<CrayTransferBean.AttachBean.ListBean> dataList = new ArrayList<>();
    private TransferRecordAdapter mTransferRecordAdapter;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_transfering, null);

        ButterKnife.bind(this,view);

        initSwipeRefreshLayout();
        initDatas();
        return view;
    }

    private void initDatas() {
        httpCommand.getCrayfishTransList(Constans.CRAY_STATUS_TRANSFERING,"",new HttpCmdCallback() {
            @Override
            public void onSucceed(Response response) {
                if (response.isSuccessful()) {
                    try {
                        String jsonString = response.body().string();
                        DebugLog.i("getCrayfishTransList  transfering,jsonString info: "+jsonString);
                        CrayTransferBean crayTransferBean = JSON.parseObject(jsonString, CrayTransferBean.class);
                        if (crayTransferBean.getCode() == Constans.REQUEST_SUCCESSFUL) {
                            List<CrayTransferBean.AttachBean.ListBean> list = crayTransferBean.getAttach().getList();
                            if (list != null) {
                                dataList.addAll(list);
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

    private void setRefreshStatues() {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                mTransferRecordAdapter.notifyDataSetChanged();

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

        mTransferRecordAdapter = new TransferRecordAdapter(getActivity(),dataList,Constans.CRAY_STATUS_TRANSFERING);
        mTransferRecordAdapter.setConfirmListener(this);
        mTransferRecordAdapter.setOnAppeallListener(this);
        SlideInBottomAnimationAdapter slideAdapter = new SlideInBottomAnimationAdapter(mTransferRecordAdapter);
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
                        mLastVisibleItem + 1 == mTransferRecordAdapter.getItemCount() &&
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

    /**
     * 卖家确认
     */
    @Override
    public void onConfirm(String orderId) {
        httpCommand.getCrayfishTransConfirm(orderId, new HttpCmdCallback() {
            @Override
            public void onSucceed(Response response) {
                try {
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        DebugLog.i("getCrayfishTransConfirm info: "+result);
                        JSONObject jb = new JSONObject(result);
                        int code = jb.optInt("code");
                        if (code == Constans.REQUEST_SUCCESSFUL) {
                            showToast(getString(R.string.cray_tansfer_order_confirm_status_0));
                        }else if (code == CRAY_TANSFER_ORDER_STATUS_ERROR_20021){
                            showToast(getString(R.string.cray_tansfer_order_status_error_20021));
                        }else if (code == CRAY_TANSFER_ORDER_STATUS_ERROR_20022){
                            showToast(getString(R.string.cray_tansfer_order_status_error_20022));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String message) {
                DebugLog.i("getCrayfishAppeal fail info: "+message);
            }
        });
    }

    /**
     * 卖家申诉
     */
    @Override
    public void onAppeal(String orderId) {
        httpCommand.getCrayfishAppeal(orderId, new HttpCmdCallback() {
            @Override
            public void onSucceed(Response response) {
                try {
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        DebugLog.i("getCrayfishAppeal info: "+result);
                        JSONObject jb = new JSONObject(result);
                        int code = jb.optInt("code");
                        if (code == Constans.REQUEST_SUCCESSFUL) {
                          showToast(getString(R.string.cray_tansfer_order_appeal_status_0));
                        }else if (code == CRAY_TANSFER_ORDER_STATUS_ERROR_20021){
                            showToast(getString(R.string.cray_tansfer_order_status_error_20021));
                        }else if (code == CRAY_TANSFER_ORDER_STATUS_ERROR_20022){
                            showToast(getString(R.string.cray_tansfer_order_appeal_status_error_20022));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String message) {
                DebugLog.i("getCrayfishAppeal fail info: "+message);
            }
        });
    }
}
