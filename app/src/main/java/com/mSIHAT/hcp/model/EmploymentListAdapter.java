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
public class EmploymentListAdapter extends BaseAdapter {
    private List<EmploymentClass> listData;
    private LayoutInflater layoutInflater;

    public EmploymentListAdapter(Context aContext, List<EmploymentClass> listData) {
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
            convertView = layoutInflater.inflate(R.layout.list_employment, null);
            holder = new ViewHolder();
            holder.Employment = (TextView) convertView.findViewById(R.id.empName);
            holder.StartD = (TextView) convertView.findViewById(R.id.empStartD);
            holder.EndD = (TextView) convertView.findViewById(R.id.empEndD);
            holder.Pos = (TextView) convertView.findViewById(R.id.empPos);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.Employment.setText( listData.get(position).getEmployer());
        holder.StartD.setText( listData.get(position).getStartD());
        holder.EndD.setText( listData.get(position).getToD());
        holder.Pos.setText( listData.get(position).getPosition());
        return convertView;
    }

    static class ViewHolder {
        TextView Employment;
        TextView StartD;
        TextView EndD;
        TextView Pos;
    }
}