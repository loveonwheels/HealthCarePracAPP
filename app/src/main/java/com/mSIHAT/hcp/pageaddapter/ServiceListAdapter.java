package com.mSIHAT.hcp.pageaddapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.classes.HCPAuthentication;
import com.mSIHAT.hcp.model.noficationmodelfromapi;
import com.mSIHAT.hcp.util.DateTimeFormatter;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 14/11/2015.
 */
public class ServiceListAdapter extends BaseAdapter {
    private List<HCPAuthentication> listData;
    private LayoutInflater layoutInflater;

    public ServiceListAdapter(Context aContext, List<HCPAuthentication> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public HCPAuthentication getItem(int position) {
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
       convertView = layoutInflater.inflate(R.layout.list_servicearea_item, null);


            holder.servicearea = (TextView) convertView.findViewById(R.id.serviceareadesc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.servicearea.setText( listData.get(position).hcp_url);



        return convertView;
    }

    static class ViewHolder {

        TextView servicearea;

    }
}