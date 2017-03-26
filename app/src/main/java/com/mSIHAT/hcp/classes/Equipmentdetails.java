package com.mSIHAT.hcp.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ghost on 17/8/16.
 */
public class Equipmentdetails implements Parcelable {
    public Equipmentdetails(int id, String name, Float price){
        this.id= id;
        this.name = name;
        this.price = price;
    }



    public int id;
    public String name;
public double price;

    protected Equipmentdetails(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readDouble();
    }

    public static final Creator<Equipmentdetails> CREATOR = new Creator<Equipmentdetails>() {
        @Override
        public Equipmentdetails createFromParcel(Parcel in) {
            return new Equipmentdetails(in);
        }

        @Override
        public Equipmentdetails[] newArray(int size) {
            return new Equipmentdetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeDouble(price);
    }
}
