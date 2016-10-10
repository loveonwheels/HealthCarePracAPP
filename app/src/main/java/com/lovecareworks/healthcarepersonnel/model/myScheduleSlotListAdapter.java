package com.lovecareworks.healthcarepersonnel.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lovecareworks.healthcarepersonnel.R;
import com.lovecareworks.healthcarepersonnel.util.Date;
import com.lovecareworks.healthcarepersonnel.util.DateFormatter;
import com.lovecareworks.healthcarepersonnel.util.TimeSlotUtil;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 14/11/2015.
 */
public class myScheduleSlotListAdapter extends BaseAdapter {
    private List<myScheduleSlot> listData;
    private LayoutInflater layoutInflater;

    public myScheduleSlotListAdapter(Context aContext, List<myScheduleSlot> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_scheduleitem, null);
            holder = new ViewHolder();
            holder.startDate = (TextView) convertView.findViewById(R.id.sch_hols_startDate);
            holder.endDate = (TextView) convertView.findViewById(R.id.sch_hols_endDate);
            holder.startTime = (TextView) convertView.findViewById(R.id.sch_hols_startTime);
            holder.endTime = (TextView) convertView.findViewById(R.id.sch_hols_endTime);
            holder.to = (TextView) convertView.findViewById(R.id.sch_hols_to);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(listData.get(position).getScheduleType() == 0){
            holder.to.setVisibility(View.GONE);
            holder.endDate.setVisibility(View.GONE);
            try {
                holder.startDate.setText(new DateFormatter(listData.get(position).getSlotDate()).getDateWithDay());
            } catch (ParseException e) {
                holder.startDate.setText("");
            }
            holder.startTime.setText(TimeSlotUtil.getTimeStringValue(listData.get(position).getStartSlot()));
            holder.endTime.setText(TimeSlotUtil.getTimeStringValue(listData.get(position).getEndSlot()));

        }else{

            holder.to.setVisibility(View.VISIBLE);
            try {
                holder.startDate.setText(new DateFormatter(listData.get(position).getStartDate()).getDateWithDay());
                holder.endDate.setText(new DateFormatter(listData.get(position).getEndDate()).getDateWithDay());
            } catch (ParseException e) {
                holder.startDate.setText("");
            }


            holder.startTime.setText(TimeSlotUtil.getTimeStringValue(listData.get(position).getStartSlot()));
            holder.endTime.setText(TimeSlotUtil.getTimeStringValue(listData.get(position).getEndSlot()));
        }





        return convertView;
    }

    static class ViewHolder {
        TextView startDate;
        TextView endDate;
        TextView startTime;
        TextView endTime;
        TextView to;
    }
}