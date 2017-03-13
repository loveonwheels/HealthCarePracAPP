package com.mSIHAT.hcp.model;

/**
 * Created by ghost on 14/9/16.
 */
public class myScheduleSlot2{

    public String SlotDate = "";

    public int startSlot = 0;
    public int endSlot = 0;
    public int scheduleType = 0;
    public String StartDate = "";
    public String EndDate= "";
    public String PatientName = "";
    public String ClientName= "";
    public String Service = "";

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

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }
}
