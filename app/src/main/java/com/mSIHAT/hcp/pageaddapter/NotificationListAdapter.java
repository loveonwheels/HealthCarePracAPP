package com.mSIHAT.hcp.pageaddapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.model.noficationmodel;
import com.mSIHAT.hcp.model.noficationmodelfromapi;
import com.mSIHAT.hcp.util.DateTimeFormatter;
import com.mSIHAT.hcp.util.TimeSlotUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 14/11/2015.
 */
public class NotificationListAdapter extends BaseAdapter {
    private List<noficationmodelfromapi> listData;
    private LayoutInflater layoutInflater;

    public NotificationListAdapter(Context aContext, List<noficationmodelfromapi> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public noficationmodelfromapi getItem(int position) {
        return listData.get(position);
    }

    public void removeItem(int position){
        listData.remove(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
       convertView = layoutInflater.inflate(R.layout.list_notification_item, null);
              /*

            holder.scheduleImg = (ImageView) convertView.findViewById(R.id.dialogImage);
            holder.patName = (TextView) convertView.findViewById(R.id.sch_dia_pat_name);
            holder.cliName = (TextView) convertView.findViewById(R.id.sch_dia_cli_name);
            holder.Add = (TextView) convertView.findViewById(R.id.sch_dia_add);
            holder.Gender = (TextView) convertView.findViewById(R.id.sch_dia_gender);
            holder.Relationship= (TextView) convertView.findViewById(R.id.sch_dia_rel);
            holder.schDate = (TextView) convertView.findViewById(R.id.sch_dia_date);
            holder.schTime = (TextView) convertView.findViewById(R.id.sch_dia_time);
            holder.ailDes = (TextView) convertView.findViewById(R.id.sch_dia_ail);
            holder.addDes = (TextView) convertView.findViewById(R.id.sch_dia_info);
            holder.serDes = (TextView) convertView.findViewById(R.id.sch_dia_service_des);
            holder.btnReq = (Button)convertView.findViewById(R.id.btnRequest);
            holder.btnCan = (Button)convertView.findViewById(R.id.btnCanel);
               */

            holder.header = (TextView) convertView.findViewById(R.id.header);
            holder.day = (TextView) convertView.findViewById(R.id.day_text);
            holder.date = (TextView) convertView.findViewById(R.id.date_text);
            holder.time = (TextView) convertView.findViewById(R.id.time_text);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.status = (LinearLayout)convertView.findViewById(R.id.status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DateTimeFormatter datevalue;
        try {
             datevalue = new DateTimeFormatter(listData.get(position).notification_date);
            holder.day.setText(datevalue.getdayvalue());
            holder.date.setText(datevalue.getDateWithMonth());

            holder.time.setText(datevalue.getTImevalue());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.header.setText( listData.get(position).header);


        holder.description.setText( listData.get(position).summary);

        Log.e("dfdfdf",String.valueOf(listData.get(position).status));
        if(listData.get(position).status == 2){
            Log.e("dfdfdf","i am here");
            holder.status.setVisibility(View.GONE);
        }else{
            holder.status.setVisibility(View.VISIBLE);
        }

             /*
        holder.patName.setText( listData.get(position).patient_fullname);
        holder.scheduleImg = (ImageView) convertView.findViewById(R.id.dialogImage);
        holder.cliName.setText( listData.get(position).sponsor_fullname);
        holder.Add.setText( listData.get(position).patient_address);
        holder.Gender.setText( listData.get(position).patient_gender);
        holder.Relationship.setText( listData.get(position).patient_relationship);
        holder.schDate.setText( listData.get(position).appiontment_date);
        holder.schTime.setText( listData.get(position).appiontment_time);
        holder.ailDes.setText( listData.get(position).ailmentdescription);
        holder.addDes.setText( listData.get(position).addiontionalinformation);
        holder.serDes.setText( listData.get(position).servicedescription);


        try {

            holder.scheduleImg.setImageBitmap(textImg(reutilfunction.convertname_to_intial(listData.get(position).patient_fullname),39,189,190));
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.btnReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.btnCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */
        return convertView;
    }

    static class ViewHolder {
        TextView day;
        TextView date;
        TextView time;
        TextView header;
        TextView description;
        LinearLayout status;

    }
}