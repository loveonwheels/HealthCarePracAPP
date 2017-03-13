package com.mSIHAT.hcp.model;

import java.util.List;

/**
 * Created by ghost on 14/9/16.
 */
public class ScheduleSlotHolder {

    List<myScheduleSlot2> ScheduleList = null;

    int id = 0;

    public ScheduleSlotHolder() {
    }

    public ScheduleSlotHolder(List<myScheduleSlot2> scheduleList) {
        this.ScheduleList = scheduleList;
    }

    public List<myScheduleSlot2> getScheduleList() {
        return ScheduleList;
    }

    public void setScheduleList(List<myScheduleSlot2> scheduleList) {
        this.ScheduleList = scheduleList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
