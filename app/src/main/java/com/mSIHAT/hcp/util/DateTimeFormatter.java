package com.mSIHAT.hcp.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ghost on 14/9/16.
 */
public class DateTimeFormatter {
    private String day;
    private String month;
    private String year;
    private String dayofweek;
    private java.util.Date javadate;
    private String timevalue;

    public DateTimeFormatter(String dat) throws ParseException {

        DateFormat df = new SimpleDateFormat("M/d/yyyy h:mm:ss aa");

        Calendar cal  = Calendar.getInstance();
        cal.setTime(df.parse(dat));
        this.javadate = cal.getTime();
        this.day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        this.month = String.valueOf(cal.get(Calendar.MONTH));
        this.year = String.valueOf(cal.get(Calendar.YEAR));
        this.dayofweek = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
    }


    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getDayofweek(){

        return dayofweek;
    }

    public String getDateWithDay(){

        SimpleDateFormat formatter = new SimpleDateFormat(
                "EEE, dd MMM yyyy");
        return formatter.format(javadate);
    }

    public String getDateWithMonth(){

        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd MMM yyyy");
        return formatter.format(javadate);
    }


    public String getTImevalue(){

        SimpleDateFormat formatter = new SimpleDateFormat(
                "hh:mm aa");
        return formatter.format(javadate);
    }


    public String getdayvalue(){

        SimpleDateFormat formatter = new SimpleDateFormat(
                "EEEE");
        return formatter.format(javadate);
    }

}
