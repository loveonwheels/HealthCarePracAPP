package com.mSIHAT.hcp.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ghost on 17/7/16.
 */
public class certificationbody implements Parcelable {
    public String registrationbody = "";
    public String registrationnumber ="";
    public String nric = "";
    public String type;
    public String renewal = "";
    public String expirationdate = "";
    public String language;


    public certificationbody() {
    }

    public certificationbody(String registrationbody, String registrationnumber, String nric, String type, String renewal, String expirationdate, String language) {
        this.registrationbody = registrationbody;
        this.registrationnumber = registrationnumber;
        this.nric = nric;
        this.type = type;
        this.renewal = renewal;
        this.expirationdate = expirationdate;
        this.language = language;
    }

    protected certificationbody(Parcel in) {
        registrationbody = in.readString();
        registrationnumber = in.readString();
        nric = in.readString();
        type = in.readString();
        renewal = in.readString();
        expirationdate = in.readString();
        language = in.readString();
    }

    public static final Creator<certificationbody> CREATOR = new Creator<certificationbody>() {
        @Override
        public certificationbody createFromParcel(Parcel in) {
            return new certificationbody(in);
        }

        @Override
        public certificationbody[] newArray(int size) {
            return new certificationbody[size];
        }
    };

    public String getRegistrationbody() {
        return registrationbody;
    }

    public void setRegistrationbody(String registrationbody) {
        this.registrationbody = registrationbody;
    }

    public String getRegistrationnumber() {
        return registrationnumber;
    }

    public void setRegistrationnumber(String registrationnumber) {
        this.registrationnumber = registrationnumber;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRenewal() {
        return renewal;
    }

    public void setRenewal(String renewal) {
        this.renewal = renewal;
    }

    public String getExpirationdate() {
        return expirationdate;
    }

    public void setExpirationdate(String expirationdate) {
        this.expirationdate = expirationdate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(registrationbody);
        dest.writeString(registrationnumber);
        dest.writeString(nric);
        dest.writeString(type);
        dest.writeString(renewal);
        dest.writeString(expirationdate);
        dest.writeString(language);
    }
}
