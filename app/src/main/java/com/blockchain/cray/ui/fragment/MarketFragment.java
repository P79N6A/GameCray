package com.blockchain.cray.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blockchain.cray.CrayApplication;
import com.blockchain.cray.R;
import com.blockchain.cray.bean.CrayBean;
import com.blockchain.cray.net.HttpCmdCallback;
import com.blockchain.cray.ui.view.CustomGridView;
import com.blockchain.cray.ui.adapter.CrayMarketAdapter;
import com.blockchain.cray.ui.activity.AdoptingRecordActivity;
import com.blockchain.cray.ui.view.CustomDialog;
import com.blockchain.cray.utils.Constans;
import com.blockchain.cray.utils.DebugLog;
import com.blockchain.cray.utils.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

public class MarketFragment extends BaseFragment implements CrayMarketAdapter.OnItemClickListener {

    @BindView(R.id.grid_view)
    CustomGridView gridView;

    private Unbinder unbinder;
    List<CrayBean.AttachBean> mCrayList = new ArrayList<>();
    private CrayMarketAdapter adapter;
    private CustomDialog customDialog;

    public MarketFragment(){

    }
    @Override
    public View initView(LayoutInflater inflate) {
        
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);
        adapter = new CrayMarketAdapter(getActivity(), mCrayList);
        adapter.setOnItemClickListener(this);
        gridView.setAdapter(adapter);
        DebugLog.d("MarketFragment initView ===>>");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DebugLog.d("MarketFragment onResume ===>>");
    }

    @Override
    public void initData() {
        super.initData();
        DebugLog.d("MarketFragment initData ===>>");
        mLoadView.onShow();
        httpCommand.loadCrayData(new HttpCmdCallback() {
            @Override
            public void onSucceed(Response response) {
                mLoadView.onDismiss();
                if (response.isSuccessful()) {
                    try {
                        String jsonString = response.body().string();
                        DebugLog.i("loadCrayData,jsonString info: "+jsonString);
                        CrayBean cray = JSON.parseObject(jsonString, CrayBean.class);
                        DebugLog.i("loadCrayData,info: "+cray.toString());
                        if (cray.isSuccess()) {
                            List<CrayBean.AttachBean> dataList = cray.getAttach();
                            if (dataList != null) {
                                mCrayList.addAll(dataList);
                                updateUI();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFail(String message) {
               mLoadView.onDismiss();
            }
        });
    }

    private void updateUI(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        if (null != unbinder) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public void onItemClick(View view, int position, final TextView tvCray) {
        if (mCrayList.size() > 0) {
            CrayBean.AttachBean cray = mCrayList.get(position);
            DebugLog.i("Cray Status:"+cray.getStatus()+", id:"+cray.getId()+", text:"+tvCray.getText().toString());
            DebugLog.i("Cray id:"+cray.getId());
            DebugLog.i("Cray text:"+tvCray.getText().toString());
            if (tvCray.getText().toString().equals(getString(R.string.cray_status_reservation))) {
                subscribeCray(cray,tvCray);
            }else if (tvCray.getText().toString().equals(getString(R.string.cray_status_snap_up)) || tvCray.getText().toString().equals(getString(R.string.cray_status_in_panic))){
                spikeCray(cray,tvCray);
            }
        }
    }

    private void spikeCray(CrayBean.AttachBean cray, final TextView tvCray) {
        httpCommand.spikeCray(cray.getId(), new HttpCmdCallback() {
            @Override
            public void onSucceed(Response response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        DebugLog.i("spikeCray: "+result);
                        JSONObject jb = new JSONObject(result);
                        int code = jb.optInt("code");
                        dealSpikeCrayResult(code,tvCray);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    private void dealSpikeCrayResult(final int code, final TextView crayStatus){

        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                switch (code){
                    case Constans.REQUEST_SUCCESSFUL://抢购成功

                        crayStatus.setText(Objects.requireNonNull(getActivity()).getString(R.string.cray_status_in_panic));
                        crayStatus.setBackground(getActivity().getDrawable(R.drawable.background_cray_status_adopting_bg));
                        customDialog = new CustomDialog(getActivity());
                        customDialog.setMessage(getString(R.string.cray_spike_success_msg));
                        customDialog.setConfirm("confirm", new CustomDialog.IOnConfirmListener(){
                            @Override
                            public void onConfirm(CustomDialog dialog) {
                                mActivity.startActivity(new Intent(CrayApplication.getApplication(), AdoptingRecordActivity.class));
                            }
                        });
                        customDialog.show();
                        break;
                    case Constans.CRAY_SPIKE_CODE_20001:
                        showToast(Objects.requireNonNull(getActivity()).getString(R.string.cray_spike_error_20001));
                        break;
                    case Constans.CRAY_SPIKE_CODE_20011:
                        showToast(Objects.requireNonNull(getActivity()).getString(R.string.cray_spike_error_20011));
                        break;
                    case Constans.CRAY_SPIKE_CODE_20012:
                        showToast(Objects.requireNonNull(getActivity()).getString(R.string.cray_spike_error_20012));
                        break;
                    case Constans.CRAY_SPIKE_CODE_20013:
                        showToast(Objects.requireNonNull(getActivity()).getString(R.string.cray_spike_error_20013));
                        break;
                }
            }
        });
    }

    private void subscribeCray(final CrayBean.AttachBean cray,final TextView tvCray) {

        httpCommand.subscribeCray(cray.getId(),new HttpCmdCallback() {
            @Override
            public void onSucceed(Response response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        DebugLog.i("subscribeCray: "+result);
                        JSONObject jb = new JSONObject(result);
                        int code = jb.optInt("code");
                        dealSubscribeCrayResult(code,tvCray);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    private void dealSubscribeCrayResult(final int code, final TextView crayStatus) {

        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (code){
                    case Constans.REQUEST_SUCCESSFUL:
                        showToast(Objects.requireNonNull(getActivity()).getString(R.string.cray_subscribe_success));
                        crayStatus.setText(getActivity().getString(R.string.cray_status_snap_up));
                        crayStatus.setBackground(getActivity().getDrawable(R.drawable.background_cray_status_adoptable_bg));
                        break;
                    case Constans.CRAY_SUBSCRIBE_CODE_20001:
                        showToast(Objects.requireNonNull(getActivity()).getString(R.string.cray_subscribe_error_20001));
                        break;
                    case Constans.CRAY_SUBSCRIBE_CODE_20002:
                        showToast(Objects.requireNonNull(getActivity()).getString(R.string.cray_subscribe_error_20002));
                        break;
                    case Constans.CRAY_SUBSCRIBE_CODE_20003:
                        showToast(Objects.requireNonNull(getActivity()).getString(R.string.cray_subscribe_error_20003));
                        break;
                    case Constans.CRAY_SUBSCRIBE_CODE_20004:
                        showToast(Objects.requireNonNull(getActivity()).getString(R.string.cray_subscribe_error_20004));
                        break;
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != customDialog) {
            customDialog = null;
        }
    }
}
