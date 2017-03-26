package com.mSIHAT.hcp.pageaddapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.classes.Equipmentdetails;
import com.mSIHAT.hcp.classes.HCPAuthentication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 14/11/2015.
 */
public class EquipmentListAdapter extends BaseAdapter {
    ArrayList<Equipmentdetails> equipments;
    private List<Equipmentdetails> listData;
    private LayoutInflater layoutInflater;

    public EquipmentListAdapter(Context aContext, ArrayList<Equipmentdetails> equipments,List<Equipmentdetails> listData) {
        this.listData = listData;
        this.equipments = equipments;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Equipmentdetails getItem(int position) {
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
       convertView = layoutInflater.inflate(R.layout.list_equipment_item, null);


            holder.equip_name = (TextView) convertView.findViewById(R.id.equip_name);
            holder.equip_price = (TextView) convertView.findViewById(R.id.equip_price);
            holder.equip_quot = (TextView) convertView.findViewById(R.id.equip_quot);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.equip_name.setText( equipments.get(position).name);
        holder.equip_price.setText("Recommeded Price : RM"+String.valueOf(equipments.get(position).price));
        holder.equip_quot.setText("Your quotation : RM"+String.valueOf(listData.get(position).price));



        return convertView;
    }

    static class ViewHolder {

        TextView equip_name;
        TextView equip_price;
        TextView equip_quot;


    }
}