package com.lovecareworks.healthcarepersonnel.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alamchristian on 3/2/16.
 */
public class basicinfomation implements Parcelable {

    public String fullname;
    public String email;
    public String nationality;
    public String phone;
    public String dob;
    public String maritalstatus;
    public String gender;
    public String secondaryphone = "";
    public String address;
    public String servicearea = "";
    public String secondaryaddress = "";
    public String nric = "";

    protected basicinfomation(Parcel in) {
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
        servicearea = in.readString();
        nric = in.readString();
    }

    public static final Creator<basicinfomation> CREATOR = new Creator<basicinfomation>() {
        @Override
        public basicinfomation createFromParcel(Parcel in) {
            return new basicinfomation(in);
        }

        @Override
        public basicinfomation[] newArray(int size) {
            return new basicinfomation[size];
        }
    };



    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMaritalstatus() {
        return maritalstatus;
    }

    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSecondaryphone() {
        return secondaryphone;
    }

    public void setSecondaryphone(String secondaryphone) {
        this.secondaryphone = secondaryphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSecondaryaddress() {
        return secondaryaddress;
    }

    public void setSecondaryaddress(String secondaryaddress) {
        this.secondaryaddress = secondaryaddress;
    }


    public basicinfomation(String fullname, String email, String nationality, String phone, String dob, String maritalstatus, String gender, String secondaryphone, String address, String secondaryaddress,String servicearea,String nric) {
        this.fullname = fullname;
        this.email = email;
        this.nationality = nationality;
        this.phone = phone;
        this.dob = dob;
        this.maritalstatus = maritalstatus;
        this.gender = gender;
        this.secondaryphone = secondaryphone;
        this.address = address;
        this.secondaryaddress = secondaryaddress;
        this.servicearea = servicearea;
        this.nric = nric;
    }

    public basicinfomation(){

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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
        dest.writeString(servicearea);
        dest.writeString(nric);
    }
}
