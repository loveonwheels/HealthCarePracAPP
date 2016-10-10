package com.lovecareworks.healthcarepersonnel.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ghost on 17/7/16.
 */
public class educationbackground implements Parcelable{

    public String name;
    public String startdate;
    public String todate;
    public String awardreceived;



    public educationbackground(String name, String startdate, String todate, String awardreceived) {
        this.name = name;
        this.startdate = startdate;
        this.todate = todate;
        this.awardreceived = awardreceived;
    }

    protected educationbackground(Parcel in) {
        name = in.readString();
        startdate = in.readString();
        todate = in.readString();
        awardreceived = in.readString();
    }

    public static final Creator<educationbackground> CREATOR = new Creator<educationbackground>() {
        @Override
        public educationbackground createFromParcel(Parcel in) {
            return new educationbackground(in);
        }

        @Override
        public educationbackground[] newArray(int size) {
            return new educationbackground[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getAwardreceived() {
        return awardreceived;
    }

    public void setAwardreceived(String awardreceived) {
        this.awardreceived = awardreceived;
    }

    public educationbackground() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(startdate);
        dest.writeString(todate);
        dest.writeString(awardreceived);
    }
}

