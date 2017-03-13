package com.mSIHAT.hcp.Calendar;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ghost on 12/10/16.
 */
public class CalendarController {

    public Calendar[] weekdet = new Calendar[3];
    public CalendarDay[] daydetails = new CalendarDay[7];
    public CalendarDay[] Nxtdaydetails = new CalendarDay[7];
    public CalendarDay[] Pvsdaydetails = new CalendarDay[7];
    public CalendarDay currentDay ;
    public CalendarDay previousDay ;
    public CalendarDay nextDay ;
    SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy");
    SimpleDateFormat displayweeklen = new SimpleDateFormat("dd MMM");

    SimpleDateFormat daydet = new SimpleDateFormat("dd");
    SimpleDateFormat mondet= new SimpleDateFormat("MMM");

    Calendar cal = Calendar.getInstance(new Locale("es", "ES"));
    Calendar curcal = Calendar.getInstance(new Locale("es", "ES"));
    Calendar precal = Calendar.getInstance(new Locale("es", "ES"));
    Calendar nxtcal = Calendar.getInstance(new Locale("es", "ES"));

    int currentyear = 1000;
    int currentweek = 12;

    public CalendarController() {
        cal.add(Calendar.DATE, 7);
        currentweek = cal.get(Calendar.WEEK_OF_YEAR) ;

        curcal.add(Calendar.DATE, 7);
        precal.add(Calendar.DATE, 6);
        nxtcal.add(Calendar.DATE, 8);

        currentDay = new CalendarDay(daydet.format(curcal.getTime()),mondet.format(curcal.getTime()),curcal.getTime());
        previousDay = new CalendarDay(daydet.format((precal.getTime())),mondet.format(precal.getTime()),precal.getTime());
        nextDay = new CalendarDay(daydet.format(nxtcal.getTime()),mondet.format(nxtcal.getTime()),nxtcal.getTime());

        Log.e("week of the year",String.valueOf(cal.get(Calendar.WEEK_OF_YEAR)));
        currentyear = cal.get(Calendar.YEAR);


        setcurrentweekdeatils(currentweek,currentyear);
        setnextweekDetail((currentweek+1),currentyear);
        setpreviousweekDetail((currentweek-1),currentyear);
    }



    public void setnextweekDetail(int week,int year){
        if((week +1) >= 53){
           week = 1;
            year = (year + 1);
        }

        weekdet[1] = Calendar.getInstance();
        weekdet[1].clear();
        weekdet[1].setFirstDayOfWeek(Calendar.MONDAY);
        weekdet[1].set(Calendar.WEEK_OF_YEAR, week);
        weekdet[1].set(Calendar.YEAR,year);
        weekdet[1].set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Nxtdaydetails[0] = new CalendarDay(daydet.format(weekdet[1].getTime()),mondet.format(weekdet[1].getTime()),weekdet[1].getTime());
        weekdet[1].set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        Nxtdaydetails[1] = new CalendarDay(daydet.format(weekdet[1].getTime()),mondet.format(weekdet[1].getTime()),weekdet[1].getTime());
        weekdet[1].set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        Nxtdaydetails[2] = new CalendarDay(daydet.format(weekdet[1].getTime()),mondet.format(weekdet[1].getTime()),weekdet[1].getTime());
        weekdet[1].set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        Nxtdaydetails[3] = new CalendarDay(daydet.format(weekdet[1].getTime()),mondet.format(weekdet[1].getTime()),weekdet[1].getTime());
        weekdet[1].set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        Nxtdaydetails[4] = new CalendarDay(daydet.format(weekdet[1].getTime()),mondet.format(weekdet[1].getTime()),weekdet[1].getTime());
        weekdet[1].set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        Nxtdaydetails[5] = new CalendarDay(daydet.format(weekdet[1].getTime()),mondet.format(weekdet[1].getTime()),weekdet[1].getTime());
        weekdet[1].set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Nxtdaydetails[6] = new CalendarDay(daydet.format(weekdet[1].getTime()),mondet.format(weekdet[1].getTime()),weekdet[1].getTime());



    }

    public void setpreviousweekDetail(int week,int year){
        if((week - 1) <= 0){
            week = 52;
            year = (year - 1);
        }

        weekdet[2] = Calendar.getInstance();
        weekdet[2].clear();
        weekdet[2].setFirstDayOfWeek(Calendar.MONDAY);
        weekdet[2].set(Calendar.WEEK_OF_YEAR, week);
        weekdet[2].set(Calendar.YEAR,year);
        weekdet[2].set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);


        Pvsdaydetails[0] = new CalendarDay(daydet.format(weekdet[2].getTime()),mondet.format(weekdet[2].getTime()),weekdet[2].getTime());
        weekdet[2].set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        Pvsdaydetails[1] = new CalendarDay(daydet.format(weekdet[2].getTime()),mondet.format(weekdet[2].getTime()),weekdet[2].getTime());
        weekdet[2].set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        Pvsdaydetails[2] = new CalendarDay(daydet.format(weekdet[2].getTime()),mondet.format(weekdet[2].getTime()),weekdet[2].getTime());
        weekdet[2].set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        Pvsdaydetails[3] = new CalendarDay(daydet.format(weekdet[2].getTime()),mondet.format(weekdet[2].getTime()),weekdet[2].getTime());
        weekdet[2].set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        Pvsdaydetails[4] = new CalendarDay(daydet.format(weekdet[2].getTime()),mondet.format(weekdet[2].getTime()),weekdet[2].getTime());
        weekdet[2].set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        Pvsdaydetails[5] = new CalendarDay(daydet.format(weekdet[2].getTime()),mondet.format(weekdet[2].getTime()),weekdet[2].getTime());
        weekdet[2].set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Pvsdaydetails[6] = new CalendarDay(daydet.format(weekdet[2].getTime()),mondet.format(weekdet[2].getTime()),weekdet[2].getTime());


    }

    public void setcurrentweekdeatils(int week,int year){

       weekdet[0] = Calendar.getInstance();
        weekdet[0].clear();
       weekdet[0].setFirstDayOfWeek(Calendar.MONDAY);
       weekdet[0].set(Calendar.WEEK_OF_YEAR, week);
       weekdet[0].set(Calendar.YEAR,year);
       weekdet[0].set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        daydetails[0] = new CalendarDay(daydet.format(weekdet[0].getTime()),mondet.format(weekdet[0].getTime()),weekdet[0].getTime());
       weekdet[0].set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        daydetails[1] = new CalendarDay(daydet.format(weekdet[0].getTime()),mondet.format(weekdet[0].getTime()),weekdet[0].getTime());
       weekdet[0].set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        daydetails[2] = new CalendarDay(daydet.format(weekdet[0].getTime()),mondet.format(weekdet[0].getTime()),weekdet[0].getTime());
       weekdet[0].set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        daydetails[3] = new CalendarDay(daydet.format(weekdet[0].getTime()),mondet.format(weekdet[0].getTime()),weekdet[0].getTime());
       weekdet[0].set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        daydetails[4] = new CalendarDay(daydet.format(weekdet[0].getTime()),mondet.format(weekdet[0].getTime()),weekdet[0].getTime());
       weekdet[0].set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        daydetails[5] = new CalendarDay(daydet.format(weekdet[0].getTime()),mondet.format(weekdet[0].getTime()),weekdet[0].getTime());
       weekdet[0].set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        daydetails[6] = new CalendarDay(daydet.format(weekdet[0].getTime()),mondet.format(weekdet[0].getTime()),weekdet[0].getTime());

    }

    public String getNxtDisplayWeekNum(){

        int responseyear = 0;
        int responseweek = 0;
        if((currentweek + 1) >= 53){
            responseweek = 1;
            responseyear = (currentyear + 1);
        }else{
            responseweek = (currentweek + 1);
            responseyear = currentyear;
        }



        return "Week "+String.valueOf(responseweek)+", "+String.valueOf(responseyear);
    }



    public String getPreDisplayWeekNum(){

        int responseyear = 0;
        int responseweek = 0;
        if((currentweek - 1) <= 0){
            responseweek = 52;
            responseyear = (currentyear - 1);
        }else{
            responseweek = (currentweek - 1);
            responseyear = currentyear;
        }



        return "Week "+String.valueOf(responseweek)+", "+String.valueOf(responseyear);
    }

    public String getPreDisplayWeekDis(){
String response= "";

        weekdet[2].set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        response += displayweeklen.format(weekdet[2].getTime());

        weekdet[2].set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        response += ("  -  "+displayweeklen.format(weekdet[2].getTime()));
        return response;
    }


    public String getNxtDisplayWeekDis(){
        String response= "";

        weekdet[1].set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        response += displayweeklen.format(weekdet[1].getTime());

        weekdet[1].set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        response += ("  -  "+displayweeklen.format(weekdet[1].getTime()));
        return response;
    }



    public String getDisplayWeekNum(){
        return "Week "+String.valueOf(currentweek)+", "+String.valueOf(currentyear);
    }



    public String getDisplayWeekDis(){
        String response= "";

        weekdet[0].set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        response += displayweeklen.format(weekdet[0].getTime());

        weekdet[0].set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        response += ("  -  "+displayweeklen.format(weekdet[0].getTime()));
        return response;
    }


    public int getcurrentdayvalue(){



        if (Calendar.MONDAY == curcal.get(Calendar.DAY_OF_WEEK)) {

           return 0;

        }else if(Calendar.TUESDAY == curcal.get(Calendar.DAY_OF_WEEK)){
            return 1;
        }else if(Calendar.WEDNESDAY == curcal.get(Calendar.DAY_OF_WEEK)){
            return 2;
        }else if(Calendar.THURSDAY == curcal.get(Calendar.DAY_OF_WEEK)){
            return 3;
        }else if(Calendar.FRIDAY == curcal.get(Calendar.DAY_OF_WEEK)){
            return 4;
        }else if(Calendar.SATURDAY == curcal.get(Calendar.DAY_OF_WEEK)){
            return 5;
        }else
            return 6;






    }
    public void nextday(){


        curcal.add(Calendar.DATE, 1);
        precal.add(Calendar.DATE, 1);
        nxtcal.add(Calendar.DATE, 1);
        currentweek = curcal.get(Calendar.WEEK_OF_YEAR);
        currentyear = curcal.get(Calendar.YEAR);

       if (Calendar.SUNDAY == curcal.get(Calendar.DAY_OF_WEEK)) {

           currentweek--;

        }

        currentDay = new CalendarDay(daydet.format(curcal.getTime()),mondet.format(curcal.getTime()),curcal.getTime());
        previousDay = new CalendarDay(daydet.format((precal.getTime())),mondet.format(precal.getTime()),precal.getTime());
        nextDay = new CalendarDay(daydet.format(nxtcal.getTime()),mondet.format(nxtcal.getTime()),nxtcal.getTime());
        setcurrentweekdeatils(currentweek,currentyear);
        setnextweekDetail((currentweek+1),currentyear);
        setpreviousweekDetail((currentweek-1),currentyear);

        setSingleDate();
    }


    public void prevday(){

        curcal.add(Calendar.DATE, -1);
        precal.add(Calendar.DATE, -1);
        nxtcal.add(Calendar.DATE, -1);

        currentweek = curcal.get(Calendar.WEEK_OF_YEAR);
        currentyear = curcal.get(Calendar.YEAR);

        currentDay = new CalendarDay(daydet.format(curcal.getTime()),mondet.format(curcal.getTime()),curcal.getTime());
        previousDay = new CalendarDay(daydet.format((precal.getTime())),mondet.format(precal.getTime()),precal.getTime());
        nextDay = new CalendarDay(daydet.format(nxtcal.getTime()),mondet.format(nxtcal.getTime()),nxtcal.getTime());


        setcurrentweekdeatils(currentweek,currentyear);
        setnextweekDetail((currentweek+1),currentyear);
        setpreviousweekDetail((currentweek-1),currentyear);

        setSingleDate();
    }

    public void nextweek(){
        currentweek++;

        if(currentweek >= 53){
            currentweek = 1;
            currentyear++;
        }

        setcurrentweekdeatils(currentweek,currentyear);
        setnextweekDetail((currentweek+1),currentyear);
        setpreviousweekDetail((currentweek-1),currentyear);
       Log.e("display week", getDisplayWeekDis());
        Log.e("display NUm", getDisplayWeekNum());



    }


    public void previousweek(){
        currentweek--;
        if(currentweek <= 0){
            currentweek = 52;
            currentyear--;
        }

        setcurrentweekdeatils(currentweek,currentyear);
        setnextweekDetail((currentweek+1),currentyear);
        setpreviousweekDetail((currentweek-1),currentyear);
        Log.e("display week", getDisplayWeekDis());
        Log.e("display NUm", getDisplayWeekNum());

    }


    public void SetCuurentDay(int datenum) {

        Log.e("current date num",String.valueOf(datenum));

        Log.e("current date",sdf.format(daydetails[datenum].getDayDate()));


        cal.setTime(daydetails[datenum].getDayDate());
        curcal.setTime(daydetails[datenum].getDayDate());
        precal.setTime(daydetails[datenum].getDayDate());
        nxtcal.setTime(daydetails[datenum].getDayDate());

        precal.add(Calendar.DATE, -1);
        nxtcal.add(Calendar.DATE, 1);

        currentDay = new CalendarDay(daydet.format(curcal.getTime()),mondet.format(curcal.getTime()),curcal.getTime());
        previousDay = new CalendarDay(daydet.format((precal.getTime())),mondet.format(precal.getTime()),precal.getTime());
        nextDay = new CalendarDay(daydet.format(nxtcal.getTime()),mondet.format(nxtcal.getTime()),nxtcal.getTime());


        currentyear = cal.get(Calendar.YEAR);


    }


    public void setSingleDate() {



        cal.setTime(curcal.getTime());


        currentweek = cal.get(Calendar.WEEK_OF_YEAR);
        precal.add(Calendar.DATE, -1);
        nxtcal.add(Calendar.DATE, 1);

        currentDay = new CalendarDay(daydet.format(curcal.getTime()),mondet.format(curcal.getTime()),curcal.getTime());
        previousDay = new CalendarDay(daydet.format((precal.getTime())),mondet.format(precal.getTime()),precal.getTime());
        nextDay = new CalendarDay(daydet.format(nxtcal.getTime()),mondet.format(nxtcal.getTime()),nxtcal.getTime());


        currentyear = cal.get(Calendar.YEAR);


        setcurrentweekdeatils(currentweek,currentyear);
        setnextweekDetail((currentweek+1),currentyear);
        setpreviousweekDetail((currentweek-1),currentyear);
    }



}
