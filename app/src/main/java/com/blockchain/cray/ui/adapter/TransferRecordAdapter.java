package com.blockchain.cray.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockchain.cray.R;
import com.blockchain.cray.bean.CrayTransferBean;
import com.blockchain.cray.utils.Constans;

import java.util.List;

import static com.blockchain.cray.utils.Constans.CRAY_STATUS_TRANSFERING;
import static com.blockchain.cray.utils.Constans.CRAY_STATUS_TRANSFERTO;
import static com.blockchain.cray.utils.Constans.CRAY_STATUS_TRANSFER_CANCEL;
import static com.blockchain.cray.utils.Constans.CRAY_STATUS_TRANSFER_FINISH;


public class TransferRecordAdapter extends RecyclerView.Adapter<TransferRecordAdapter.TransferRecordViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<CrayTransferBean.AttachBean.ListBean> dataList;
    private int ListType;

    private OnAppeallListener onAppeallListener;
    private OnConfirmListener confirmListener;

    public void setOnAppeallListener(OnAppeallListener onAppeallListener) {
        this.onAppeallListener = onAppeallListener;
    }

    public interface OnAppeallListener{
        void onAppeal(String id);
    }

    public void setConfirmListener(OnConfirmListener confirmListener) {
        this.confirmListener = confirmListener;
    }

    public interface OnConfirmListener{
        void onConfirm(String orderId);
    }

    public TransferRecordAdapter(Context context, List<CrayTransferBean.AttachBean.ListBean> dataList,int ListType) {
        this.mContext = context;
        this.dataList = dataList;
        this.ListType = ListType;
        mInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public TransferRecordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_adapter_transfer, viewGroup, false);
        return new TransferRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransferRecordViewHolder holder, int i) {
        final CrayTransferBean.AttachBean.ListBean listBean = dataList.get(i);
        if (null != listBean) {
            holder.tvTransferCrayNumber.setText(listBean.getId());
            holder.tvTransferCrayOriginalNumber.setText(listBean.getSourceId());
            holder.tvTransferCrayName.setText(listBean.getCrayFishName());
            holder.tvTransferCrayValues.setText(listBean.getIncome());

            showCrayStatus(ListType,listBean.getStatus(),listBean,holder.llTransferEarnings,holder.tvTransferCrayEarnings,
                    holder.llCrayTransferTime,holder.tvCrayTransferTime,holder.llCrayRemainingConfirmTime,
                    holder.tvCrayRemainingTimeText,holder.tvCrayRemainingTimeValues,holder.btnTransferCrayStatus,
                    holder.btnTransferCrayConfirm,holder.btnTransferCrayAppeal);

            holder.btnTransferCrayConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //卖家确认订单
                    if (null != confirmListener) {
                        confirmListener.onConfirm(listBean.getId());
                    }

                }
            });

            holder.btnTransferCrayAppeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //卖家申诉
                    if (null != onAppeallListener) {
                        onAppeallListener.onAppeal(listBean.getId());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class TransferRecordViewHolder extends RecyclerView.ViewHolder{

        TextView tvTransferCrayNumber,tvTransferCrayOriginalNumber,tvTransferCrayName,tvTransferCrayValues,tvTransferCrayEarnings,
                tvCrayTransferTime,tvCrayRemainingTimeText,tvCrayRemainingTimeValues;
        Button btnTransferCrayStatus,btnTransferCrayConfirm,btnTransferCrayAppeal;
        LinearLayout llCrayTransferTime,llCrayRemainingConfirmTime,llTransferEarnings;
        public TransferRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTransferCrayNumber = itemView.findViewById(R.id.tv_transfer_cray_number);
            tvTransferCrayOriginalNumber = itemView.findViewById(R.id.cray_transfer_original_number);
            tvTransferCrayName = itemView.findViewById(R.id.tv_transfer_cray_name);
            tvTransferCrayValues = itemView.findViewById(R.id.tv_transfer_cray_values);
            tvTransferCrayEarnings = itemView.findViewById(R.id.tv_cray_transfer_earnings);
            llCrayTransferTime = itemView.findViewById(R.id.ll_cray_transfer_time);
            llCrayRemainingConfirmTime = itemView.findViewById(R.id.ll_transfer_remaining_confirm_time);
            llTransferEarnings = itemView.findViewById(R.id.ll_transfer_earnings);
            tvCrayTransferTime = itemView.findViewById(R.id.tv_cray_transfer_time);
            tvCrayRemainingTimeText = itemView.findViewById(R.id.tv_cray_transfer_remaining_confirm_time);
            tvCrayRemainingTimeValues = itemView.findViewById(R.id.tv_cray_transfer_remaining_confirm_time_values);
            btnTransferCrayStatus = itemView.findViewById(R.id.btn_cray_transfer_status);
            btnTransferCrayConfirm = itemView.findViewById(R.id.btn_cray_transfer_confirm);
            btnTransferCrayAppeal = itemView.findViewById(R.id.btn_cray_transfer_appeal);
        }
    }

    /***
     *
     * @param status 状态值
     * @param transferBean 数据实体
     * @param llCrayEarnings 智能合约收益 控件
     * @param tvCrayEarningsValues 智能合约收益 值
     * @param llTransferTime 转让时间 控件
     * @param tvTransferTimeValues 转让时间 值
     * @param llRemainingTime 剩余时间 控件
     * @param tvRemainingTimeText 剩余时间 控件
     * @param tvRemainingTimeValues 剩余时间 值
     * @param btnCrayStatus 状态按钮显示
     * @param btnTransferCrayConfirm 确认按钮
     * @param btnTransferCrayAppeal 申诉按钮
     * @return
     */
    private void showCrayStatus(int ListType,String status,CrayTransferBean.AttachBean.ListBean transferBean,
                                  LinearLayout llCrayEarnings,TextView tvCrayEarningsValues,
                                  LinearLayout llTransferTime,TextView tvTransferTimeValues,
                                  LinearLayout llRemainingTime, TextView tvRemainingTimeText,TextView tvRemainingTimeValues,
                                  Button btnCrayStatus, Button btnTransferCrayConfirm, Button btnTransferCrayAppeal){

        switch (ListType){
            case CRAY_STATUS_TRANSFERTO://待转让
                llCrayEarnings.setVisibility(View.GONE);
                llTransferTime.setVisibility(View.GONE);
                llRemainingTime.setVisibility(View.GONE);
                btnTransferCrayConfirm.setVisibility(View.GONE);//
                btnTransferCrayAppeal.setVisibility(View.GONE);//
                btnCrayStatus.setText(R.string.cray_transfer_leave_unused);
                break;
            case CRAY_STATUS_TRANSFERING://转让中
                llCrayEarnings.setVisibility(View.VISIBLE);
                tvCrayEarningsValues.setText(transferBean.getIncomeRule());

                llTransferTime.setVisibility(View.VISIBLE);
                tvTransferTimeValues.setText(transferBean.getTransTime());

                llRemainingTime.setVisibility(View.VISIBLE);
                if (Constans.CRAY_ADAPTION_STATUS_UN_PAYMENT.equals(status)) {
                    tvRemainingTimeText.setText(R.string.cray_remaining_payment_time);
                    tvRemainingTimeValues.setText(transferBean.getLastPayTime());
                    btnCrayStatus.setText(R.string.cray_status_un_payment);
                }else if (Constans.CRAY_ADAPTION_STATUS_PAYMENTED.equals(status)){
                    tvRemainingTimeText.setText(R.string.cray_remaining_confirm_time);
                    tvRemainingTimeValues.setText(transferBean.getLastConfirmTime());
                    btnCrayStatus.setText(R.string.cray_status_paymented);
                }
                btnTransferCrayConfirm.setVisibility(View.VISIBLE);//
                btnTransferCrayAppeal.setVisibility(View.VISIBLE);//
                break;
            case CRAY_STATUS_TRANSFER_FINISH://已完成
                llCrayEarnings.setVisibility(View.VISIBLE);
                tvCrayEarningsValues.setText(transferBean.getIncomeRule());

                llTransferTime.setVisibility(View.VISIBLE);
                tvTransferTimeValues.setText(transferBean.getTransTime());

                llRemainingTime.setVisibility(View.GONE);
                if (Constans.CRAY_ADAPTION_STATUS_EARNING.equals(status)) {
                    btnCrayStatus.setText(R.string.cray_status_earning);
                }else if (Constans.CRAY_ADAPTION_STATUS_EARNING_FINISHIED.equals(status)){
                    btnCrayStatus.setText(R.string.cray_status_earning_finished);
                }

                btnTransferCrayConfirm.setVisibility(View.GONE);
                btnTransferCrayAppeal.setVisibility(View.GONE);
                break;
            case CRAY_STATUS_TRANSFER_CANCEL://申诉
                llCrayEarnings.setVisibility(View.GONE);

                llTransferTime.setVisibility(View.VISIBLE);
                tvTransferTimeValues.setText(transferBean.getTransTime());

                llRemainingTime.setVisibility(View.GONE);
                if (Constans.CRAY_ADAPTION_STATUS_APPEALING.equals(status)) {
                    btnCrayStatus.setText(R.string.cray_status_appealing);
                }else if (Constans.CRAY_ADAPTION_STATUS_CANCEL.equals(status)){
                    btnCrayStatus.setText(R.string.cray_status_cancel);
                }

                btnTransferCrayConfirm.setVisibility(View.GONE);
                btnTransferCrayAppeal.setVisibility(View.GONE);
                break;
        }
    }
}
