package com.mSIHAT.hcp.db.dbclasses;

import android.util.Log;

import com.mSIHAT.hcp.model.Appiontments;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by admin on 14-Sep-15.
 */
public class ScheduleTra implements Serializable {
    public String sponsor_fullname;
    public String sponsor_phonenumber;
    public String patient_fullname;
    public String patient_address;
    public String patient_relationship;
    public String patient_gender;
    public String appiontment_day;
    public String appiontment_date;
    public String appiontment_time;
    public String ailmentdescription;
    public String servicedescription;
    public String addiontionalinformation;
    public String patient_age;
    public int appiontmentid;


    public ScheduleTra() {
    }

    public ScheduleTra(Appiontments request) {

        Calendar now = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();
       Calendar appiontment = Calendar.getInstance();
        SimpleDateFormat appiontmentdayformatter = new SimpleDateFormat("EEEE");
        SimpleDateFormat appiontmentdateformatter = new SimpleDateFormat("MMM dd yyyy");
        SimpleDateFormat appiontmenttimeformatter = new SimpleDateFormat("hh:mm aa");
        appiontmenttimeformatter.setTimeZone(TimeZone.getDefault());

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");

        try {
            dob.setTime(sdf.parse(request.patient_dob));
            appiontment.setTime(sdf.parse(request.appiontment_date));
            Log.e("appointment detail", request.appiontment_date);
            Log.e("appointment detail", appiontment.getTime().toString());
            Log.e("appointment detail", appiontmenttimeformatter.format(appiontment.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("dfdf", "dateerror");
        }


        this.sponsor_fullname = request.sponsor_fullname;
        this.sponsor_phonenumber = request.sponsor_phonenumber;
        this.patient_relationship = request.patient_relationship;
        this.patient_fullname = request.patient_fullname;
        this.patient_address = request.patient_Address;
        if(request.patient_gender.equals("False")){
            this.patient_gender = "Female";
        }else{
            this.patient_gender = "Male";
        }

        this.patient_age = String.valueOf(now.get(Calendar.YEAR) - dob.get(Calendar.YEAR))+" yr(s) old";
        this.appiontment_day = appiontmentdayformatter.format(appiontment.getTime());
        this.appiontment_date = appiontmentdateformatter.format(appiontment.getTime());
        this.appiontment_time = appiontmenttimeformatter.format(appiontment.getTime());
        this.ailmentdescription = request.ailmentdescription;
        this.servicedescription = request.servicedescription;
        this.addiontionalinformation = request.addiontionalinformation;
        this.appiontmentid = request.appiontmentid;
    }



    public ScheduleTra(String patient_fullname, String patient_address, String patient_gender, String patient_age, String appiontment_date, String appiontment_time, String ailmentdescription, String servicedescription, String addiontionalinformation, int appiontmentid) {
        this.patient_fullname = patient_fullname;
        this.patient_address = patient_address;
        this.patient_gender = patient_gender;
        this.patient_age = patient_age;
        this.appiontment_date = appiontment_date;
        this.appiontment_time = appiontment_time;
        this.ailmentdescription = ailmentdescription;
        this.servicedescription = servicedescription;
        this.addiontionalinformation = addiontionalinformation;
        this.appiontmentid = appiontmentid;
    }


}
