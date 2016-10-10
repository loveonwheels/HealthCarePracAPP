package com.lovecareworks.healthcarepersonnel.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ghost on 17/7/16.
 */
public class employmenthistory implements Parcelable {
    public String name;
    public String startdate;
    public String todate;
    public String positionheld;

    protected employmenthistory(Parcel in) {
        name = in.readString();
        startdate = in.readString();
        todate = in.readString();
        positionheld = in.readString();
    }

    public static final Creator<employmenthistory> CREATOR = new Creator<employmenthistory>() {
        @Override
        public employmenthistory createFromParcel(Parcel in) {
            return new employmenthistory(in);
        }

        @Override
        public employmenthistory[] newArray(int size) {
            return new employmenthistory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(startdate);
        dest.writeString(todate);
        dest.writeString(positionheld);
    }
}
