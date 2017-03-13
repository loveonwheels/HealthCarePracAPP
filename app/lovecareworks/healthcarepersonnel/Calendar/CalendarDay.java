package com.mSIHAT.hcp.Calendar;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ghost on 13/10/16.
 */
public class CalendarDay {

    String day;

    String month;
    Date dayDate;

    SimpleDateFormat fulldateformatter = new SimpleDateFormat("MMM, dd yyyy");
    SimpleDateFormat dayinfullformatter = new SimpleDateFormat("EEEE");


    public CalendarDay(String day, String month, Date dayDate) {
        this.day = day;
        this.month = month;
        this.dayDate = dayDate;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Date getDayDate() {
        return dayDate;
    }

    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }

    public String getDayinfull(){

       return  dayinfullformatter.format(dayDate);
    }

    public String getdateinstring() {
    return fulldateformatter.format(dayDate);
    }
}
