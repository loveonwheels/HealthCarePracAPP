package com.mSIHAT.hcp.fragements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.db.dbclasses.ScheduleTra;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 14/11/2015.
 */
public class ScheduleListAdapter extends BaseAdapter {
    private ArrayList<ScheduleTra> listData;
    private LayoutInflater layoutInflater;

    public ScheduleListAdapter(Context aContext, ArrayList<ScheduleTra> listData) {
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
            holder = new ViewHolder();
       convertView = layoutInflater.inflate(R.layout.list_schedule, null);
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

            holder.patName = (TextView) convertView.findViewById(R.id.sch_list_pat_name);
            holder.Add = (TextView) convertView.findViewById(R.id.sch_list_add);
            holder.schDate = (TextView) convertView.findViewById(R.id.sch_list_date);
            holder.schDay = (TextView) convertView.findViewById(R.id.sch_list_day);
            holder.schTime = (TextView) convertView.findViewById(R.id.sch_list_time);
            holder.serDes = (TextView) convertView.findViewById(R.id.sch_list_service_type);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.patName.setText( listData.get(position).patient_fullname);
        holder.Add.setText( listData.get(position).patient_address);
        holder.schDay.setText( listData.get(position).appiontment_day);
        holder.schDate.setText( listData.get(position).appiontment_date);
        holder.schTime.setText( listData.get(position).appiontment_time);
        holder.serDes.setText( listData.get(position).servicedescription);


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

    Bitmap textImg(final String text, int R, int G, int B) throws IOException {
        Bitmap bitmap = Bitmap.createBitmap(112, 120, Bitmap.Config.ARGB_8888);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(45);
        bitmap.eraseColor(Color.rgb(R, G, B));
        paint.setTextAlign(Paint.Align.RIGHT);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, 80, 80, paint);
        return bitmap;
    }
    static class ViewHolder {
        TextView patName;
        TextView Add;
        TextView schDay;
        TextView schDate;
        TextView schTime;
        TextView serDes;

    }
}