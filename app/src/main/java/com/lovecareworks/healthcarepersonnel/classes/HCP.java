package com.lovecareworks.healthcarepersonnel.classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.lovecareworks.healthcarepersonnel.model.EducationItem;
import com.lovecareworks.healthcarepersonnel.model.EmploymentClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ghost on 17/7/16.
 */
public class HCP implements Parcelable{
    public basicinfomation BasicInformation = null;
    public certificationbody CertificationBody = null;
    List<EmploymentClass> EmploymentHistoryList = null;
    List<EducationItem> EducationBackgroundList= null;

    protected HCP(Parcel in) {
        BasicInformation = in.readParcelable(basicinfomation.class.getClassLoader());
        CertificationBody = in.readParcelable(certificationbody.class.getClassLoader());
        EmploymentHistoryList = in.createTypedArrayList(EmploymentClass.CREATOR);
        EducationBackgroundList = in.createTypedArrayList(EducationItem.CREATOR);
    }

    public static final Creator<HCP> CREATOR = new Creator<HCP>() {
        @Override
        public HCP createFromParcel(Parcel in) {
            return new HCP(in);
        }

        @Override
        public HCP[] newArray(int size) {
            return new HCP[size];
        }
    };

    public List<EducationItem> getEducationBackgroundList() {
        return EducationBackgroundList;
    }

    public void setEducationBackgroundList(List<EducationItem> educationBackgroundList) {
        EducationBackgroundList = educationBackgroundList;
    }

    public List<EmploymentClass> getEmploymentHistoryList() {
        return EmploymentHistoryList;
    }

    public void setEmploymentHistoryList(List<EmploymentClass> employmentHistoryList) {
        EmploymentHistoryList = employmentHistoryList;
    }





    public HCP() {
    }



    public basicinfomation getBasicInformation() {
        return BasicInformation;
    }

    public void setBasicInformation(basicinfomation basicInformation) {
        BasicInformation = basicInformation;
    }

    public certificationbody getCertificationBody() {
        return CertificationBody;
    }

    public void setCertificationBody(certificationbody certificationBody) {
        CertificationBody = certificationBody;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(BasicInformation, flags);
        dest.writeParcelable(CertificationBody, flags);
        dest.writeTypedList(EmploymentHistoryList);
        dest.writeTypedList(EducationBackgroundList);
    }
}
