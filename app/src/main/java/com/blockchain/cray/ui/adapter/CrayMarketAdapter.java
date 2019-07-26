package com.blockchain.cray.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockchain.cray.R;
import com.blockchain.cray.bean.CrayBean;
import com.blockchain.cray.utils.DebugLog;
import com.blockchain.cray.utils.ImageUtils;
import java.util.List;

import static com.blockchain.cray.utils.Constans.CRAY_STATUS_BE_GROWING_UP;
import static com.blockchain.cray.utils.Constans.CRAY_STATUS_IN_BREEDING;
import static com.blockchain.cray.utils.Constans.CRAY_STATUS_IN_PANIC;
import static com.blockchain.cray.utils.Constans.CRAY_STATUS_RESERVATION;
import static com.blockchain.cray.utils.Constans.CRAY_STATUS_SNAP_UP;

public class CrayMarketAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<CrayBean.AttachBean> list;
    private Context context;

    public CrayMarketAdapter(Context context, List<CrayBean.AttachBean> list) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_adapter_craymarket, null);
            holder = new ViewHolder();
            holder.crayPortrait = convertView.findViewById(R.id.iv_cray_portrait);
            holder.crayName = convertView.findViewById(R.id.tv_cray_name);
            holder.crayValue = convertView.findViewById(R.id.tv_cray_value);
            holder.crayAdoptionTime = convertView.findViewById(R.id.tv_cray_adoption_time);
            holder.crayPayment = convertView.findViewById(R.id.tv_cray_payment);
            holder.crayEarnings = convertView.findViewById(R.id.tv_cray_earnings);
            holder.crayStatus = convertView.findViewById(R.id.btn_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CrayBean.AttachBean attachBean = list.get(position);
        if (attachBean != null) {
//            Glide.with(context).load(attachBean.getHeadPicUrl()).into(holder.crayPortrait);
            ImageUtils.showCrayPortrait(attachBean.getName(),holder.crayPortrait);
            holder.crayName.setText(""+attachBean.getName());
            holder.crayValue.setText(""+attachBean.getStartPrice()+"-"+attachBean.getEndPrice());

            String startTime = ""+attachBean.getStartTime();
            String startTimeHour = startTime.substring(0,2);
            String startTimeMinute = startTime.substring(2,4);

            String endTime = ""+attachBean.getEndTime();
            String endTimeHour = endTime.substring(0,2);
            String endTimeMinute = endTime.substring(2,4);

            holder.crayAdoptionTime.setText(startTimeHour+":"+startTimeMinute+"-"+endTimeHour+":"+endTimeMinute);
            holder.crayPayment.setText(""+attachBean.getReserveCost()+"/"+attachBean.getQuicklyCost());
            holder.crayEarnings.setText(""+attachBean.getAdoptionDay()+"/"+attachBean.getIncomePoint());

            setCrayStatus(holder.crayStatus,attachBean.getStatus());
        }

        holder.crayStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener) {
                    onItemClickListener.onItemClick(view,position,holder.crayStatus);
                }
            }
        });
        return convertView;
    }

    private void setCrayStatus(TextView crayStatus,int status){
       switch (status){
           case CRAY_STATUS_RESERVATION:
               crayStatus.setText(context.getString(R.string.cray_status_reservation));
               crayStatus.setBackground(context.getDrawable(R.drawable.btn_cray_status_bg));
               break;
           case CRAY_STATUS_SNAP_UP:
               crayStatus.setText(context.getString(R.string.cray_status_snap_up));
               crayStatus.setBackground(context.getDrawable(R.drawable.background_cray_status_adoptable_bg));
               break;
           case CRAY_STATUS_IN_PANIC:
               crayStatus.setText(context.getString(R.string.cray_status_in_panic));
               crayStatus.setBackground(context.getDrawable(R.drawable.background_cray_status_adopting_bg));
               break;
           case CRAY_STATUS_IN_BREEDING:
               crayStatus.setText(context.getString(R.string.cray_status_in_breeding));
               crayStatus.setBackground(context.getDrawable(R.drawable.background_register_get_un_verifycode_bg));
               break;
           case CRAY_STATUS_BE_GROWING_UP:
               crayStatus.setText(context.getString(R.string.cray_status_be_growing_up));
               crayStatus.setBackground(context.getDrawable(R.drawable.background_register_get_un_verifycode_bg));
               break;
       }
    }

    class ViewHolder {
        TextView crayName,crayValue,crayAdoptionTime,crayPayment,crayEarnings,crayStatus;
        ImageView crayPortrait;
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View view,int position,TextView crayStatus);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}
