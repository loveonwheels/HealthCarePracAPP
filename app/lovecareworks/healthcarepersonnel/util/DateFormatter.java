package com.mSIHAT.hcp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ghost on 14/9/16.
 */
public class DateFormatter {
    private String day;
    private String month;
    private String year;
    private String dayofweek;
    private java.util.Date javadate;

    public DateFormatter(String day, String month, String year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public DateFormatter(String dat) throws ParseException {

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
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
                "EEE, dd MMM yyyy ");
        return formatter.format(javadate);
    }

}
