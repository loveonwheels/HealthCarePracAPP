package com.mSIHAT.hcp.model;

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by admin on 14-Sep-15.
 */
public class noficationmodel implements Serializable {
    public int notification_id;
    public String date;
    public String day;
    public String header;
    public String description;
    public String time;
    public int hcp_id;


    public String details;




}
