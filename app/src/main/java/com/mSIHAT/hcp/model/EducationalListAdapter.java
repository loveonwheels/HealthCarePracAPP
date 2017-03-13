package com.mSIHAT.hcp.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mSIHAT.hcp.R;

import java.util.List;

/**
 * Created by Administrator on 14/11/2015.
 */
public class EducationalListAdapter extends BaseAdapter {
    private List<EducationItem> listData;
    private LayoutInflater layoutInflater;

    public EducationalListAdapter(Context aContext, List<EducationItem> listData) {
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
            convertView = layoutInflater.inflate(R.layout.list_education, null);
            holder = new ViewHolder();
            holder.universityName = (TextView) convertView.findViewById(R.id.uniName);
            holder.startDate = (TextView) convertView.findViewById(R.id.stDate);
            holder.endDate = (TextView) convertView.findViewById(R.id.endDate);
            holder.award = (TextView) convertView.findViewById(R.id.awardtext);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

    holder.universityName.setText( listData.get(position).getUniversityName());

        holder.startDate.setText(listData.get(position).getStartDate());
        holder.endDate.setText(listData.get(position).getEndDate());
        holder.award.setText(listData.get(position).getAwardReceived());
        return convertView;
    }

    static class ViewHolder {
        TextView universityName;
        TextView startDate;
        TextView endDate;
        TextView award;
    }
}