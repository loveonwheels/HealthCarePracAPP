package com.mSIHAT.hcp.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.util.DateFormatter;
import com.mSIHAT.hcp.util.TimeSlotUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 14/11/2015.
 */
public class myScheduleSlotListAdapter2 extends BaseAdapter {
    private List<myScheduleSlot2> listData;
    private LayoutInflater layoutInflater;


    public myScheduleSlotListAdapter2(Context aContext, List<myScheduleSlot2> listData) {
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

            convertView = layoutInflater.inflate(R.layout.myscheduleitem2, null);
            holder = new ViewHolder();

            holder.emptyoutline = (LinearLayout) convertView.findViewById(R.id.emptyoutline);
            holder.detailoutline = (LinearLayout) convertView.findViewById(R.id.detailoutline);
        holder.startTime = (TextView) convertView.findViewById(R.id.txtStartTime);
        holder.endTime = (TextView) convertView.findViewById(R.id.txtEndTime);

        holder.patientName = (TextView) convertView.findViewById(R.id.txtPatientName);
        holder.clientName = (TextView) convertView.findViewById(R.id.txtClientName);
        holder.service = (TextView) convertView.findViewById(R.id.txtService);


          /* holder.startDate = (TextView) convertView.findViewById(R.id.sch_hols_startDate);
            holder.endDate = (TextView) convertView.findViewById(R.id.sch_hols_endDate);
            holder.to = (TextView) convertView.findViewById(R.id.sch_hols_to);

            */
           convertView.setTag(holder);


        if(Integer.parseInt(listData.get(position).getEndDate()) == 0){

            holder.detailoutline.setVisibility(View.GONE);
            holder.startTime.setText(TimeSlotUtil.getTimeStringValue(listData.get(position).getStartSlot()));
            holder.endTime.setText(TimeSlotUtil.getTimeStringValue(listData.get(position).getEndSlot()));



        }else{
            holder.emptyoutline.setVisibility(View.GONE);

            holder.startTime.setText(TimeSlotUtil.getTimeStringValue(listData.get(position).getStartSlot()));
            holder.endTime.setText(TimeSlotUtil.getTimeStringValue(listData.get(position).getEndSlot()));
            Log.e("clinet patient",listData.get(position).getSlotDate());
            holder.patientName.setText(listData.get(position).getPatientName());
            holder.clientName.setText(listData.get(position).getClientName());
            holder.service.setText(listData.get(position).getService());
         //   Log.e("clinet patient",listData.get(position).getPatientName());
           // holder.patientName.setText(listData.get(position).getPatientName());
            //holder.clientName.setText(listData.get(position).getClientName());
            //holder.service.setText(listData.get(position).getService());
            /*

            holder.to.setVisibility(View.VISIBLE);
            try {
                holder.startDate.setText(new DateFormatter(listData.get(position).getStartDate()).getDateWithDay());
                holder.endDate.setText(new DateFormatter(listData.get(position).getEndDate()).getDateWithDay());
            } catch (ParseException e) {
                holder.startDate.setText("");
            }


            holder.startTime.setText(TimeSlotUtil.getTimeStringValue(listData.get(position).getStartSlot()));
            holder.endTime.setText(TimeSlotUtil.getTimeStringValue(listData.get(position).getEndSlot()));

            */
        }





        return convertView;
    }

    static class ViewHolder {


        TextView startTime;
        TextView endTime;
        TextView patientName;
        TextView clientName;
        TextView service;
        LinearLayout emptyoutline;
        LinearLayout detailoutline;
    }
}