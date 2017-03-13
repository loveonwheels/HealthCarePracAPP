package com.mSIHAT.hcp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ghost on 28/1/16.
 */
public class EmploymentClass implements Parcelable {

    private String Employer;
    private String StartD;
    private String ToD;
    private String Position;

    public EmploymentClass() {


    }

    public EmploymentClass(String employer, String startD, String toD, String position) {
        this.Employer = employer;
        this.StartD = startD;
        this.ToD = toD;
        this.Position = position;

    }

    protected EmploymentClass(Parcel in) {
        Employer = in.readString();
        StartD = in.readString();
        ToD = in.readString();
        Position = in.readString();
    }

    public static final Creator<EmploymentClass> CREATOR = new Creator<EmploymentClass>() {
        @Override
        public EmploymentClass createFromParcel(Parcel in) {
            return new EmploymentClass(in);
        }

        @Override
        public EmploymentClass[] newArray(int size) {
            return new EmploymentClass[size];
        }
    };

    public String getEmployer() {
        return Employer;
    }

    public void setEmployer(String employer) {
        Employer = employer;
    }

    public String getStartD() {
        return StartD;
    }

    public void setStartD(String startD) {
        StartD = startD;
    }

    public String getToD() {
        return ToD;
    }

    public void setToD(String toD) {
        ToD = toD;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Employer);
        dest.writeString(StartD);
        dest.writeString(ToD);
        dest.writeString(Position);
    }
}
