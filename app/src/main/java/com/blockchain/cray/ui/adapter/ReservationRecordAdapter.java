package com.blockchain.cray.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blockchain.cray.R;
import com.blockchain.cray.bean.CrayReservationBean;

import java.util.List;


public class ReservationRecordAdapter extends RecyclerView.Adapter<ReservationRecordAdapter.ReservationRecordViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<CrayReservationBean.AttachBean.ListBean> dataList;

    public ReservationRecordAdapter(Context context, List<CrayReservationBean.AttachBean.ListBean> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        mInflater = LayoutInflater.from(context);

    }
    @NonNull
    @Override
    public ReservationRecordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_adapter_reservation_record, viewGroup, false);
        return new ReservationRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationRecordViewHolder holder, int i) {
        CrayReservationBean.AttachBean.ListBean crayReservationBean = dataList.get(i);
        if (null != crayReservationBean) {
            holder.tvReservationName.setText(crayReservationBean.getCrayfishName());
            holder.tvReservationCost.setText(crayReservationBean.getCost());
            holder.tvReservationTime.setText(crayReservationBean.getReservationTime());
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ReservationRecordViewHolder extends RecyclerView.ViewHolder{

        TextView tvReservationName,tvReservationCost,tvReservationTime;

        public ReservationRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReservationName = itemView.findViewById(R.id.tv_reservation_name);
            tvReservationCost = itemView.findViewById(R.id.tv_reservation_cost);
            tvReservationTime = itemView.findViewById(R.id.tv_reservation_time);
        }
    }
}
