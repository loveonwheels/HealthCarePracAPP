package com.mSIHAT.hcp.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ghost on 17/7/16.
 */
public class BasicHCP implements Parcelable{
    public int id;
    public String fullname;
    public String email;
    public String nationality;
    public String phone;
    public String dob;
    public String maritalstatus;
    public String gender;
    public String secondaryphone;
    public String address;
    public String secondaryaddress;

    protected BasicHCP(Parcel in) {
        id = in.readInt();
        fullname = in.readString();
        email = in.readString();
        nationality = in.readString();
        phone = in.readString();
        dob = in.readString();
        maritalstatus = in.readString();
        gender = in.readString();
        secondaryphone = in.readString();
        address = in.readString();
        secondaryaddress = in.readString();
    }

    public static final Creator<BasicHCP> CREATOR = new Creator<BasicHCP>() {
        @Override
        public BasicHCP createFromParcel(Parcel in) {
            return new BasicHCP(in);
        }

        @Override
        public BasicHCP[] newArray(int size) {
            return new BasicHCP[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(fullname);
        dest.writeString(email);
        dest.writeString(nationality);
        dest.writeString(phone);
        dest.writeString(dob);
        dest.writeString(maritalstatus);
        dest.writeString(gender);
        dest.writeString(secondaryphone);
        dest.writeString(address);
        dest.writeString(secondaryaddress);
    }
}
