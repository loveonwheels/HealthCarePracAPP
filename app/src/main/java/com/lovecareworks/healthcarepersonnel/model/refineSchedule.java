package com.lovecareworks.healthcarepersonnel.model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ghost on 19/10/16.
 */
public class refineSchedule {

    public static List<myScheduleSlot2> refine(List<myScheduleSlot2> listData, Date cDate) throws ParseException {


        List<myScheduleSlot2> returnList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(cDate);

if( listData != null) {
    Log.e("current date",String.valueOf(currentDate.get(Calendar.DAY_OF_MONTH)));
    Log.e("list size",String.valueOf(listData.size()));

    for (int i = 0; i < listData.size(); i++) {
        Date date = sdf.parse(listData.get(i).getSlotDate());// all done
        Calendar listDate = sdf.getCalendar();

        if (listDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) && listDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) && listDate.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)) {
            returnList.add(listData.get(i));
        }
        ;


    }
}

        return returnList;

    }
}
