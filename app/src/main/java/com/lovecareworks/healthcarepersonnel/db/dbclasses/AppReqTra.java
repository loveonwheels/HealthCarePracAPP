package com.lovecareworks.healthcarepersonnel.db.dbclasses;

import android.util.Log;

import com.lovecareworks.healthcarepersonnel.model.AppRequest;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by admin on 14-Sep-15.
 */
public class AppReqTra implements Serializable {
    public String patient_fullname;
    public String patient_address;
    public String patient_gender;
    public String patient_age;
    public String appiontment_date;
    public String appiontment_time;
    public String ailmentdescription;
    public String servicedescription;
    public String addiontionalinformation;
    public int appiontmentid;


    public AppReqTra() {
    }

    public AppReqTra(AppRequest request) {

        Calendar now = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();
       Calendar appiontment = Calendar.getInstance();

        SimpleDateFormat appiontmentdateformatter = new SimpleDateFormat("EE, MMM dd yyyy");
        SimpleDateFormat appiontmenttimeformatter = new SimpleDateFormat("HH:mm a");
        appiontmenttimeformatter.setTimeZone(TimeZone.getDefault());

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
        try {
            dob.setTime(sdf.parse(request.patient_dob));
            appiontment.setTime(sdf.parse(request.appiontment_date));
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("dfdf", "dateerror");
        }

        this.patient_fullname = request.patient_fullname;
        this.patient_address = request.patient_Address;
        this.patient_gender = request.patient_gender;
        Log.e("appiontmenttime",appiontmenttimeformatter.format(appiontment.getTime()));
        this.patient_age = String.valueOf(now.get(Calendar.YEAR) - dob.get(Calendar.YEAR))+" yr(s) old";
        this.appiontment_date = appiontmentdateformatter.format(appiontment.getTime());
        this.appiontment_time = appiontmenttimeformatter.format(appiontment.getTime());
        this.ailmentdescription = request.ailmentdescription;
        this.servicedescription = request.servicedescription;
        this.addiontionalinformation = request.addiontionalinformation;
        this.appiontmentid = request.appiontmentid;
    }



    public AppReqTra(String patient_fullname, String patient_address, String patient_gender, String patient_age, String appiontment_date, String appiontment_time, String ailmentdescription, String servicedescription, String addiontionalinformation, int appiontmentid) {
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
    public String getPatient_age() {
        return patient_age;
    }

    public void setPatient_age(String patient_age) {
        this.patient_age = patient_age;
    }

    public String getPatient_fullname() {
        return patient_fullname;
    }

    public void setPatient_fullname(String patient_fullname) {
        this.patient_fullname = patient_fullname;
    }

    public String getPatient_address() {
        return patient_address;
    }

    public void setPatient_address(String patient_address) {
        this.patient_address = patient_address;
    }

    public String getPatient_gender() {
        return patient_gender;
    }

    public void setPatient_gender(String patient_gender) {
        this.patient_gender = patient_gender;
    }

    public String getAppiontment_date() {
        return appiontment_date;
    }

    public void setAppiontment_date(String appiontment_date) {
        this.appiontment_date = appiontment_date;
    }

    public String getAppiontment_time() {
        return appiontment_time;
    }

    public void setAppiontment_time(String appiontment_time) {
        this.appiontment_time = appiontment_time;
    }

    public String getAilmentdescription() {
        return ailmentdescription;
    }

    public void setAilmentdescription(String ailmentdescription) {
        this.ailmentdescription = ailmentdescription;
    }

    public String getServicedescription() {
        return servicedescription;
    }

    public void setServicedescription(String servicedescription) {
        this.servicedescription = servicedescription;
    }

    public String getAddiontionalinformation() {
        return addiontionalinformation;
    }

    public void setAddiontionalinformation(String addiontionalinformation) {
        this.addiontionalinformation = addiontionalinformation;
    }

    public int getAppiontmentid() {
        return appiontmentid;
    }

    public void setAppiontmentid(int appiontmentid) {
        this.appiontmentid = appiontmentid;
    }


}
