package com.example.hazemnabil.islamictodo2.myCalender;

import java.util.Calendar;

/**
 * Created by hazem.nabil on 2/4/2018.
 */

public class SmallTime_old {

    public MyTime myTime;
    public  int timeName_id;
    public  int timeName_txt;
    public  int afterMinutes;
    public int timeType;
    public int hour24,hour12,minute;
    public String ampm;

    public Calendar timeCalendar;
    public float f_time;
    public float f_timeNameBegin;
    public float f_timeNameEnd;



    public SmallTime_old(Calendar timeCalendar) {
        setSpecificTime(timeCalendar.get(Calendar.HOUR), timeCalendar.get(Calendar.MINUTE));


    }


    public void setRelatedTime(int timeName_id, int afterMinutes) {
        this.timeName_id = timeName_id;
        this.afterMinutes = afterMinutes;
        setMyTime(timeCalendar);
        f_timeNameBegin =Float.valueOf(  myTime.getPayerTimeAt(timeName_id));
        f_timeNameEnd = Float.valueOf(  myTime.getPayerTimeAt(timeName_id+1));

        f_time = f_timeNameBegin + afterMinutes;
        int[] h = myTime.floatToTimeArray(f_time);
        setSpecificTime(h[0],h[1]);
    }
    public void getTimeNameId(){

    }
    public void afterMinutes(){

    }


    public void setSpecificTime(int hour24, int minute) {
        this.setHour24(hour24);
        this.setMinute(minute);



    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }

    public void setHour24(int hour24) {

        this.hour24 = hour24;

        if (hour24 > 12) {
            this.hour12 = hour24 - 12;
            ampm = "pm";
        } else {
            hour12 = hour24;
            ampm = "am";
        }
        this.hour12 = hour24;
        f_time = myTime.time24ToFloat(hour24,minute);
    }

    public void setHour12(int hour12, String ampm) {
        this.hour12 = hour12;
        this.ampm = ampm;
        if(ampm == "pm"){
            hour24 = hour12 + 12;
        }else
            hour24 = hour12;
        f_time = myTime.time24ToFloat(hour24,minute);
    }

    public void setMinute(int minute) {
        this.minute = minute;
        f_time = myTime.time24ToFloat(hour24,minute);
    }

    public void setMyTime(Calendar timeCalender){
        timeCalendar = timeCalender;
        timeCalendar.set(Calendar.HOUR,hour24);
        timeCalendar.set(Calendar.MINUTE,minute);
        myTime = new MyTime(timeCalender);
        //myTime.checkWhen()

    }
    public void setDateCalender(Calendar dateCalender){
        this.timeCalendar.set(dateCalender.get(Calendar.YEAR),dateCalender.get(Calendar.MONTH),dateCalender.get(Calendar.DAY_OF_MONTH));
        myTime = new MyTime(timeCalendar);
    }

}
