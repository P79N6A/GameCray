package com.blockchain.cray.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockchain.cray.R;
import com.blockchain.cray.bean.CrayAdoptionBean;
import com.blockchain.cray.ui.activity.CrayPaymentActivity;
import com.blockchain.cray.ui.activity.PaymentDetailActivity;
import com.blockchain.cray.utils.Constans;
import com.blockchain.cray.utils.DebugLog;
import com.blockchain.cray.utils.UIUtils;

import java.util.List;

import static com.blockchain.cray.utils.Constans.CRAY_ADAPTION_STATUS_APPEALING;
import static com.blockchain.cray.utils.Constans.CRAY_ADAPTION_STATUS_CANCEL;
import static com.blockchain.cray.utils.Constans.CRAY_ADAPTION_STATUS_EARNING;
import static com.blockchain.cray.utils.Constans.CRAY_ADAPTION_STATUS_EARNING_FINISHIED;
import static com.blockchain.cray.utils.Constans.CRAY_ADAPTION_STATUS_PAYMENTED;
import static com.blockchain.cray.utils.Constans.CRAY_ADAPTION_STATUS_UN_PAYMENT;


public class AdoptionRecordAdapter extends RecyclerView.Adapter<AdoptionRecordAdapter.AdoptionRecordViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<CrayAdoptionBean.AttachBean.ListBean> dataList;

    public AdoptionRecordAdapter(Context context, List<CrayAdoptionBean.AttachBean.ListBean> dataList) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.dataList = dataList;

    }
    @NonNull
    @Override
    public AdoptionRecordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = mInflater.inflate(R.layout.item_adapter_adoption, viewGroup, false);
        return new AdoptionRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdoptionRecordViewHolder adoptionRecordViewHolder, final int position) {
        final CrayAdoptionBean.AttachBean.ListBean adoptionBean =dataList.get(position);
        if (null != adoptionBean) {
            adoptionRecordViewHolder.tvCrayNumber.setText(adoptionBean.getId());
            adoptionRecordViewHolder.tvCrayName.setText(adoptionBean.getCrayFishName());
            adoptionRecordViewHolder.tvCrayIncome.setText(adoptionBean.getIncome());
            adoptionRecordViewHolder.tvCrayEarning.setText(adoptionBean.getIncomeRule());
            adoptionRecordViewHolder.tvCrayTransferTime.setText(adoptionBean.getTransTime());
            adoptionRecordViewHolder.btnCrayStaus.setText(showCrayStatus(adoptionRecordViewHolder.llRemainingPaymentTime, adoptionRecordViewHolder.tvRemainingPaymentTime, adoptionRecordViewHolder.tvCrayRemainingPaymentTime,
                    adoptionRecordViewHolder.btnCrayPayment,adoptionBean.getStatus(),adoptionBean));
            adoptionRecordViewHolder.btnCrayPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CrayPaymentActivity.class);
                    intent.putExtra(Constans.KEY_ADOPTION_ID,adoptionBean.getId());
                    mContext.startActivity(intent);
                }
            });

            adoptionRecordViewHolder.llItemAdoption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PaymentDetailActivity.class);
                    intent.putExtra(Constans.KEY_ADOPTION_ID,adoptionBean.getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class AdoptionRecordViewHolder extends RecyclerView.ViewHolder{

        TextView tvCrayNumber,tvCrayName,tvCrayIncome,tvCrayEarning,tvCrayTransferTime,tvRemainingPaymentTime,tvCrayRemainingPaymentTime;
        Button btnCrayStaus,btnCrayPayment;
        LinearLayout llRemainingPaymentTime,llItemAdoption;
        public AdoptionRecordViewHolder(@NonNull View itemView) {
            super(itemView);
             tvCrayNumber = itemView.findViewById(R.id.tv_cray_number);
             tvCrayName = itemView.findViewById(R.id.tv_cray_name);
             tvCrayIncome = itemView.findViewById(R.id.tv_cray_income);
             tvCrayEarning = itemView.findViewById(R.id.tv_cray_earnings);
             tvCrayTransferTime = itemView.findViewById(R.id.tv_cray_transfer_time);
             tvRemainingPaymentTime = itemView.findViewById(R.id.tv_remaining_payment_time);
             tvCrayRemainingPaymentTime = itemView.findViewById(R.id.tv_cray_remaining_payment_time);
             btnCrayStaus = itemView.findViewById(R.id.btn_cray_staus);
             btnCrayPayment = itemView.findViewById(R.id.btn_cray_payment);
             llRemainingPaymentTime = itemView.findViewById(R.id.ll_remaining_payment_time);
             llItemAdoption = itemView.findViewById(R.id.ll_item_adoption);
        }
    }

    private String showCrayStatus(LinearLayout remainingPaymentTime,TextView tvRemainingPaymentTime,TextView tvTimesValues,Button crayPayment,String status,CrayAdoptionBean.AttachBean.ListBean adoptingBean){
           String statusMsg = "";
           switch (status){
               case CRAY_ADAPTION_STATUS_UN_PAYMENT://btn_cray_status_bg
                   remainingPaymentTime.setVisibility(View.VISIBLE);
                   crayPayment.setVisibility(View.VISIBLE);//显示付款按钮
                   tvRemainingPaymentTime.setText(mContext.getString(R.string.cray_remaining_payment_time));
                   tvTimesValues.setText(adoptingBean.getLastPayTime());//剩余付款时间
                   tvTimesValues.setTextColor(UIUtils.getColor(R.color.color_cray_time_values));
                   statusMsg = mContext.getString(R.string.cray_status_un_payment);
                   break;
               case CRAY_ADAPTION_STATUS_PAYMENTED:
                   remainingPaymentTime.setVisibility(View.VISIBLE);
                   crayPayment.setVisibility(View.GONE);
                   tvRemainingPaymentTime.setText(mContext.getString(R.string.cray_remaining_confirm_time));
                   tvTimesValues.setText(adoptingBean.getLastConfirmTime());//剩余确认时间
                   tvTimesValues.setTextColor(UIUtils.getColor(R.color.color_cray_time_values));
                   statusMsg = mContext.getString(R.string.cray_status_paymented);
                   break;
               case CRAY_ADAPTION_STATUS_APPEALING:
                   remainingPaymentTime.setVisibility(View.GONE);
                   crayPayment.setVisibility(View.GONE);
                   statusMsg =mContext.getString(R.string.cray_status_appealing);
                   break;
               case CRAY_ADAPTION_STATUS_CANCEL:
                   remainingPaymentTime.setVisibility(View.GONE);
                   crayPayment.setVisibility(View.GONE);
                   statusMsg =mContext.getString(R.string.cray_status_cancel);
                   break;
               case CRAY_ADAPTION_STATUS_EARNING:
                   remainingPaymentTime.setVisibility(View.GONE);
                   crayPayment.setVisibility(View.GONE);
                   statusMsg = mContext.getString(R.string.cray_status_earning);
                   break;
               case CRAY_ADAPTION_STATUS_EARNING_FINISHIED:
                   remainingPaymentTime.setVisibility(View.GONE);
                   crayPayment.setVisibility(View.GONE);
                   statusMsg = mContext.getString(R.string.cray_status_earning_finished);
                   break;
           }
           return statusMsg;
    }
}
