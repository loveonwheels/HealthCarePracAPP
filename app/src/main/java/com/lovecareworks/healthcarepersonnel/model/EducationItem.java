package com.lovecareworks.healthcarepersonnel.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 14/11/2015.
 */
public class EducationItem implements Parcelable{

        private String UniversityName;
        private String StartDate;
    private String EndDate;
    private String AwardReceived;

    public EducationItem() {
    }

    protected EducationItem(Parcel in) {
        UniversityName = in.readString();
        StartDate = in.readString();
        EndDate = in.readString();
        AwardReceived = in.readString();
    }

    public static final Creator<EducationItem> CREATOR = new Creator<EducationItem>() {
        @Override
        public EducationItem createFromParcel(Parcel in) {
            return new EducationItem(in);
        }

        @Override
        public EducationItem[] newArray(int size) {
            return new EducationItem[size];
        }
    };

    public String getUniversityName() {
        return UniversityName;
    }

    public void setUniversityName(String universityName) {
        UniversityName = universityName;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getAwardReceived() {
        return AwardReceived;
    }

    public void setAwardReceived(String awardReceived) {
        AwardReceived = awardReceived;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(UniversityName);
        dest.writeString(StartDate);
        dest.writeString(EndDate);
        dest.writeString(AwardReceived);
    }
}
