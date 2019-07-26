package com.blockchain.cray.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blockchain.cray.R;

import java.util.List;


public class AppealListAdapter extends RecyclerView.Adapter<AppealListAdapter.ReservationRecordViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    List<String> list;

    public AppealListAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);

    }
    @NonNull
    @Override
    public ReservationRecordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_adapter_adopting, viewGroup, false);
        return new ReservationRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationRecordViewHolder reservationRecordViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 11;
    }

    class ReservationRecordViewHolder extends RecyclerView.ViewHolder{

        public ReservationRecordViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
