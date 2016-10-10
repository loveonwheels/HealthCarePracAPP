package com.lovecareworks.healthcarepersonnel.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ghost on 28/1/16.
 */
public class LanguageClass implements Parcelable{

    private String Language;
    private String speaking;
    private String writing;


    public LanguageClass(String language, String speaking, String writing) {
        Language = language;
        this.speaking = speaking;
        this.writing = writing;
    }


    protected LanguageClass(Parcel in) {
        Language = in.readString();
        speaking = in.readString();
        writing = in.readString();
    }

    public static final Creator<LanguageClass> CREATOR = new Creator<LanguageClass>() {
        @Override
        public LanguageClass createFromParcel(Parcel in) {
            return new LanguageClass(in);
        }

        @Override
        public LanguageClass[] newArray(int size) {
            return new LanguageClass[size];
        }
    };

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getSpeaking() {
        return speaking;
    }

    public void setSpeaking(String speaking) {
        this.speaking = speaking;
    }

    public String getWriting() {
        return writing;
    }

    public void setWriting(String writing) {
        this.writing = writing;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Language);
        dest.writeString(speaking);
        dest.writeString(writing);
    }
}
