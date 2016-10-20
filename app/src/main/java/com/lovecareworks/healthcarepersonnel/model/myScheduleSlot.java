package com.lovecareworks.healthcarepersonnel.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ghost on 14/9/16.
 */
public class myScheduleSlot implements Parcelable{

    public String SlotDate = "";

    public int startSlot = 0;
    public int endSlot = 0;
    public int scheduleType = 0;
    public String startDate = "";
    public String endDate= "";
    public String patientName = "";
    public String clientName= "";
    public String service = "";

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }



    public myScheduleSlot(String slotDate, int startSlot, int endSlot, int scheduleType) {
        SlotDate = slotDate;
        this.startSlot = startSlot;
        this.endSlot = endSlot;
        this.scheduleType = scheduleType;
    }

    public myScheduleSlot(int startSlot, int endSlot, int scheduleType, String startDate, String endDate,String patientName, String clientName,String service) {
        this.startSlot = startSlot;
        this.endSlot = endSlot;
        this.scheduleType = scheduleType;
        this.startDate = startDate;
        this.endDate = endDate;

        this.patientName = patientName;
        this.clientName = clientName;
        this.service = service;
    }

    public myScheduleSlot(int startSlot, int endSlot, int scheduleType, String startDate, String endDate) {
        this.startSlot = startSlot;
        this.endSlot = endSlot;
        this.scheduleType = scheduleType;
        this.startDate = startDate;
        this.endDate = endDate;

    }



    protected myScheduleSlot(Parcel in) {
        SlotDate = in.readString();
        startSlot = in.readInt();
        endSlot = in.readInt();
        scheduleType = in.readInt();
        startDate = in.readString();
        endDate = in.readString();

        patientName = in.readString();
        clientName = in.readString();
        service = in.readString();
    }

    public static final Creator<myScheduleSlot> CREATOR = new Creator<myScheduleSlot>() {
        @Override
        public myScheduleSlot createFromParcel(Parcel in) {
            return new myScheduleSlot(in);
        }

        @Override
        public myScheduleSlot[] newArray(int size) {
            return new myScheduleSlot[size];
        }
    };

    public String getSlotDate() {
        return SlotDate;
    }

    public void setSlotDate(String slotDate) {
        SlotDate = slotDate;
    }

    public int getStartSlot() {
        return startSlot;
    }

    public void setStartSlot(int startSlot) {
        this.startSlot = startSlot;
    }

    public int getEndSlot() {
        return endSlot;
    }

    public void setEndSlot(int endSlot) {
        this.endSlot = endSlot;
    }

    public int getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(int scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(SlotDate);
        dest.writeInt(startSlot);
        dest.writeInt(endSlot);
        dest.writeInt(scheduleType);
        dest.writeString(startDate);
        dest.writeString(endDate);

        dest.writeString(patientName);
        dest.writeString(clientName);
        dest.writeString(service);
    }
}
