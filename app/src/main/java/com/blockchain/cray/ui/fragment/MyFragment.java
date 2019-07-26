package com.blockchain.cray.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blockchain.cray.R;
import com.blockchain.cray.bean.UserInfoBean;
import com.blockchain.cray.net.HttpCmdCallback;
import com.blockchain.cray.ui.view.CustomGridView;
import com.blockchain.cray.ui.activity.AccountCreditedActivity;
import com.blockchain.cray.ui.activity.AdoptingRecordActivity;
import com.blockchain.cray.ui.activity.CertificationAccountActivity;
import com.blockchain.cray.ui.activity.InviteFriendsActivity;
import com.blockchain.cray.ui.activity.MyTeamActivity;
import com.blockchain.cray.ui.activity.ReservationRecordActivity;
import com.blockchain.cray.ui.activity.SecurityCenterActivity;
import com.blockchain.cray.ui.activity.SettingActivity;
import com.blockchain.cray.ui.activity.TransferRecordActivity;
import com.blockchain.cray.utils.Constans;
import com.blockchain.cray.utils.DebugLog;
import com.blockchain.cray.utils.UIUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Response;

import static com.blockchain.cray.utils.Constans.ACCOUNT_YOLLON;
import static com.blockchain.cray.utils.Constans.CERTIFICATION_NAME;
import static com.blockchain.cray.utils.Constans.INVITE_FRIEND;
import static com.blockchain.cray.utils.Constans.MY_TEAM;
import static com.blockchain.cray.utils.Constans.SECURITY_CENTER;
import static com.blockchain.cray.utils.Constans.SYSTEM_MSG;

public class MyFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.tv_username)
    TextView tvUserName;
    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_total_asset)
    TextView tvTotalAsset;
    @BindView(R.id.tv_cray_coin)
    TextView tvCrayCoin;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.tv_total_income)
    TextView tvTotalIncome;
    @BindView(R.id.tv_spread_income)
    TextView tvSpreadIncome;
    @BindView(R.id.tv_team_income)
    TextView tvTeamIncome;
    @BindView(R.id.ll_adopting_record)
    LinearLayout llAdoptingRecord;
    @BindView(R.id.ll_transfer_record)
    LinearLayout llTransferRecord;
    @BindView(R.id.ll_reservation_record)
    LinearLayout llReservationRecord;

    @BindView(R.id.gridView)
    CustomGridView gridView;
    private List<Map<String, Object>> gridviewDataList;
    private Unbinder unbinder;

    private int[] iconArray = {
            R.mipmap.ic_my_team, R.mipmap.ic_invite_friends, R.mipmap.ic_system_msg,
            R.mipmap.ic_name_auth,R.mipmap.ic_security_center,R.mipmap.ic_money_account};

    private String[] textArray = {MY_TEAM,INVITE_FRIEND,SYSTEM_MSG,CERTIFICATION_NAME,SECURITY_CENTER,ACCOUNT_YOLLON,};
    private UserInfoBean userInfo;

    public MyFragment(){
    }
    
    @Override
    public View initView(LayoutInflater inflater) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_my, null);
        unbinder =ButterKnife.bind(this,view);

        initUserInfo();
        initGridViewData();
        initGridView();
        return view;
    }

    private void initUserInfo() {
        mLoadView.onShow();
        httpCommand.loadUserInfo(new HttpCmdCallback() {
            @Override
            public void onSucceed(Response response) {
                mLoadView.onDismiss();
                try {
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        DebugLog.i("userInfo: "+result);
                        if (null != result) {
                            userInfo = JSON.parseObject(result, UserInfoBean.class);
                            if (null != userInfo) {
                                if (userInfo.getCode() == Constans.REQUEST_SUCCESSFUL) {
                                    dealResul(userInfo);
                                }else {
                                    showToast();
                                }


                            }
                        }
                    }else {
                        showToast();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String message) {
                mLoadView.onDismiss();
            }
        });
    }

    private void showToast(){

        UIUtils.showToast("獲取用戶信息失敗");
    }

    private void dealResul(final UserInfoBean userInfo) {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvUserName.setText(userInfo.getAttach().getNickName());
                tvRank.setText(showRank(userInfo.getAttach().getRank()));
                tvPhone.setText(userInfo.getAttach().getMobile());
                tvTotalAsset.setText(userInfo.getAttach().getTotalAssets());
                tvCrayCoin.setText(userInfo.getAttach().getCrayfishCoin());
                tvIntegral.setText(userInfo.getAttach().getIntegral());
                tvTotalIncome.setText(userInfo.getAttach().getTotalIncome());
                tvTeamIncome.setText(userInfo.getAttach().getTeamIncome());
                tvSpreadIncome.setText(userInfo.getAttach().getSpreadIncome());

                sp.put(Constans.KEY_USER_NAME,userInfo.getAttach().getNickName());
                sp.put(Constans.KEY_QRCODE,userInfo.getAttach().getQrCode());
                sp.put(Constans.KEY_MOBILE,userInfo.getAttach().getMobile());
            }
        });
    }

    private String showRank(String rank){
        String rankName = "";
        switch(rank){
            case Constans.USER_INFO_RANK_0:
                rankName = UIUtils.getString(R.string.user_info_ran_0);
                break;
            case Constans.USER_INFO_RANK_1:
                rankName = UIUtils.getString(R.string.user_info_ran_1);
                break;
            case Constans.USER_INFO_RANK_2:
                rankName = UIUtils.getString(R.string.user_info_ran_2);
                break;
            case Constans.USER_INFO_RANK_3:
                rankName = UIUtils.getString(R.string.user_info_ran_3);
                break;
        }
         return rankName;
    }

    private void initGridViewData() {
        gridviewDataList = new ArrayList<>();
        for(int i=0;i<iconArray.length;i++){
            Map<String, Object> map = new HashMap<>();
            map.put("image", iconArray[i]);
            map.put("text", textArray[i]);
            gridviewDataList.add(map);
        }
    }

    private void initGridView() {
        String [] from ={"image","text"};
        int [] to = {R.id.iv_gridview,R.id.tv_gridview};
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), gridviewDataList, R.layout.item_my_gridview, from, to);
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, Object> map = gridviewDataList.get(position);
        String name = (String) map.get("text");
        switchPage(name);
    }

    private void switchPage(String name) {
        switch (name){
            case MY_TEAM:
                startActivity(new Intent(getActivity(), MyTeamActivity.class));
                break;
            case INVITE_FRIEND:
                Intent intent = new Intent(getActivity(), InviteFriendsActivity.class);
//                intent.putExtra(Constans.KEY_QRCODE,userInfo.getAttach().getQrCode());
                startActivity(intent);
                break;
            case SYSTEM_MSG:
                 //TODO 系统消息
                showToast("正在开发中，敬请期待...");
                break;
            case CERTIFICATION_NAME:
                startActivity(new Intent(getActivity(), CertificationAccountActivity.class));
                break;
            case SECURITY_CENTER:
                startActivity(new Intent(getActivity(), SecurityCenterActivity.class));
                break;
            case ACCOUNT_YOLLON:
                Intent in = new Intent(getActivity(), AccountCreditedActivity.class);
                in.putExtra(Constans.KEY_ACCOUNT_INFO,userInfo);
                startActivity(in);
                break;
        }
    }

    @OnClick({R.id.iv_setting,R.id.ll_adopting_record,R.id.ll_transfer_record,R.id.ll_reservation_record})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_adopting_record:
                startActivity(new Intent(getActivity(), AdoptingRecordActivity.class));
                break;
            case R.id.ll_transfer_record:
                startActivity(new Intent(getActivity(), TransferRecordActivity.class));
                break;
            case R.id.ll_reservation_record:
                startActivity(new Intent(getActivity(), ReservationRecordActivity.class));
                break;
            case R.id.iv_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        if (null != unbinder) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }
}
