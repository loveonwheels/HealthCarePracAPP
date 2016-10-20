package com.lovecareworks.healthcarepersonnel.model;

import java.util.List;

/**
 * Created by ghost on 14/9/16.
 */
public class ScheduleSlotHolder2 {

    List<myScheduleSlot> ScheduleList = null;

    int id = 0;

    public ScheduleSlotHolder2() {
    }

    public ScheduleSlotHolder2(List<myScheduleSlot> scheduleList) {
        this.ScheduleList = scheduleList;
    }

    public List<myScheduleSlot> getScheduleList() {
        return ScheduleList;
    }

    public void setScheduleList(List<myScheduleSlot> scheduleList) {
        this.ScheduleList = scheduleList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
