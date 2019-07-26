package com.blockchain.cray.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blockchain.cray.R;
import com.blockchain.cray.bean.CrayMyTeamBean;

import java.util.List;


public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.ReservationRecordViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<CrayMyTeamBean.AttachBean.ListBean> list;

    public MyTeamAdapter(Context context,List<CrayMyTeamBean.AttachBean.ListBean> list) {
        this.mContext = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);

    }
    @NonNull
    @Override
    public ReservationRecordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_adapter_my_team, viewGroup, false);
        return new ReservationRecordViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReservationRecordViewHolder holder, int i) {
        CrayMyTeamBean.AttachBean.ListBean listBean = list.get(i);
        if (null != listBean) {
            holder.tvTeamName.setText(listBean.getNickName());
            holder.tvTeamNumber.setText(listBean.getMobile());
            holder.tvTeamRank.setText(listBean.getProxyType()+"");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ReservationRecordViewHolder extends RecyclerView.ViewHolder{

          TextView tvTeamName,tvTeamNumber,tvTeamRank;

        public ReservationRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTeamName = itemView.findViewById(R.id.tv_team_name);
            tvTeamNumber = itemView.findViewById(R.id.tv_team_number);
            tvTeamRank = itemView.findViewById(R.id.tv_team_rank);
        }
    }
}
