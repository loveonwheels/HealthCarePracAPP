package com.lovecareworks.healthcarepersonnel.model;

import android.util.Log;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by ghost on 26/8/16.
 */
public class hcp_infoDate {
    Calendar contractdate = Calendar.getInstance();
    Calendar now = Calendar.getInstance();
    Calendar expiration = Calendar.getInstance();
    Date contractdd = null;
    Date expdd = null;
    Date nowdd = null;

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
    SimpleDateFormat contractdateformatter = new SimpleDateFormat("MMM dd yyyy");

    public hcp_infoDate(String contractdatevalue) throws ParseException {
        Log.e("diff","1");
        contractdate.setTime(sdf.parse(contractdatevalue));
        Log.e("diff","2");
        expiration.setTime(sdf.parse(contractdatevalue));
        Log.e("diff","3");
        expiration.add(Calendar.YEAR,1);
        Log.e("diff","4");
        contractdd = contractdate.getTime();
        Log.e("diff","5");
        expdd = expiration.getTime();
        Log.e("diff","6");
        nowdd = now.getTime();
        Log.e("diff",String.valueOf(expdd));
        Log.e("diff",String.valueOf(nowdd));
    }


    public hcp_infoDate() {
    }

    public String getContractDate(){
        return contractdateformatter.format(contractdate.getTime());
    }

    public String getExpDate(){
        return contractdateformatter.format(expiration.getTime());
    }

    public String getContractremaindays(){
        long diff = expdd.getTime() - nowdd.getTime();
        Log.e("diff",String.valueOf(diff));
      return String.valueOf(TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS))+" days";
    }
}
