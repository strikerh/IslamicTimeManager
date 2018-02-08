package com.example.hazemnabil.islamictodo2.myCalender;

import com.example.hazemnabil.islamictodo2.colection.Vars;

import java.util.Calendar;

/**
 * Created by hazem.nabil on 2/4/2018.
 */

public class SmallTime {

    private Calendar calendar;
    private MyTime myTime;
    private int hour24;
    private int minute;
    private int timeType;

    public SmallTime() {
        this.timeType = Vars.TIME.SPECIFIC;
        this.calendar = Calendar.getInstance();
        myTime = new MyTime(calendar);
        hour24 = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }
    public SmallTime(int timeType, Calendar fullDateAndTime) {
        this.timeType = timeType;
        this.calendar = fullDateAndTime;
        myTime = new MyTime(calendar);
        hour24 = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }
    public SmallTime(int timeType, Calendar dateOnly, int timeNameId, int minutesAfterTimeName) {
        this.timeType = timeType;
        this.calendar = dateOnly;
        myTime = new MyTime(calendar);
        float newTime = myTime.getTimeOfPayerTimeAt(timeNameId);
        int[] timeArr = myTime.floatToTimeArray(newTime);
        int[] minAfterTimeArr = myTime.floatToTimeArray(minutesAfterTimeName);
        hour24 = timeArr[0] + minAfterTimeArr[0];
        minute = timeArr[1] + minAfterTimeArr[1];
        calendar.set(Calendar.HOUR,hour24);
        calendar.set(Calendar.MINUTE,minute);
        myTime = new MyTime(calendar);

    }

    /**
     *  Used If Not need To Change the Calender
     * @param hour24
     * @param minutes
     */
    public void setTime(int hour24,int minutes){
       calendar.set(Calendar.HOUR_OF_DAY, hour24);
        calendar.set(Calendar.MINUTE, minutes);
        myTime = new MyTime(calendar);
        this.hour24 = hour24;
        this.minute = minutes;
    }
    /**
     *  Used If Not need To Change the Calender
     * @param timeNameId
     * @param minutesAfterTimeName
     */
    public void setTimeRelated(int timeNameId,int minutesAfterTimeName){
        float newTime = myTime.getTimeOfPayerTimeAt(timeNameId);
        int[] timeArr = myTime.floatToTimeArray(newTime);
        int[] minAfterTimeArr = myTime.floatToTimeArray(minutesAfterTimeName);
        hour24 = timeArr[0] + minAfterTimeArr[0];
        minute = timeArr[1] + minAfterTimeArr[1];
        calendar.set(Calendar.HOUR_OF_DAY,hour24);
        calendar.set(Calendar.MINUTE,minute);
        myTime = new MyTime(calendar);
    }

    public void setTimeType(int timeType){
        this.timeType = timeType;
    }

    public void setCalendar(Calendar calendar){
        this.calendar = calendar;
        myTime = new MyTime(calendar);
        hour24 = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }




    public int getHour24(){
        return hour24;
    }
    public int getMinute(){
        return minute;
    }
    public float getTimeFloat(){
        return convert_time24ToFloat(hour24,minute);
    }
    public int getTimeNameId(){
        return myTime.checkWhen(hour24,minute);
    }
    public float getTimeOfTimeName(){
        return Float.valueOf(  myTime.getPayerTimeAt(getTimeNameId()));
    }

    public float getFloatDurationAfterTimeName(){
        float f_timeNameBegin = getTimeOfTimeName();
        float f_time = getTimeFloat();
        return  f_time - f_timeNameBegin;

    }

    public int getMinutesAfterTimeName(){

        float f_minutes = getFloatDurationAfterTimeName();
        int[] timeArr = convert_floatToTimeArray(f_minutes);
        return timeArr[0]*60 + timeArr[1];
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public int getHour12_Hour(){
        int t_hour12 = hour24;
        if (hour24 >= 12) {
            t_hour12 = hour24 - 12;
        }
        return t_hour12;
    }
    public String getHour12_ampm(){
        String t_ampm = "am";
        if (hour24 >= 12) {
             t_ampm = "pm";
        }
        return t_ampm;
    }

    public int getTimeType(){
        return timeType;
    }






    //////////////////////////  STATIC CONVERTER CALCULATOR /////////////////////////////////////


    public static  int convert_FloatToTimeInMinutes(float floatTime){
        int[] time = convert_floatToTimeArray(floatTime);

        return time[0] * 60 + time[1];
    }

    public static int[] convert_floatToTimeArray(float floatTime) {

        String result;

        if (Double.isNaN(floatTime)) {
            return null;
        }
        // <<<< add 0.5 minutes to round And FixHour()
        float a = floatTime + 0.5f / 60.0f ;
        a = a - 24.0f * (float) Math.floor(a / 24.0f);
        a = a < 0 ? (a + 24) : a;
        floatTime = a ;
        // >>>>
        int hours = (int) Math.floor(floatTime);
        float minutes = (float) Math.floor((floatTime - hours) * 60.0f);

        return new int[]{hours,(int)Math.round(minutes)};
    }

    public static int convert_floatToTime_hourOnly(float floatTime) {
        return convert_floatToTimeArray(floatTime)[0];
    }
    public static int convert_floatToTime_MinuteOnly(float floatTime) {
        return convert_floatToTimeArray(floatTime)[1];
    }


    public static float convert_time24ToFloat(int hour24, int minute) {
        return  (hour24 + minute / 60f);
    }

    public static int[] convert_minutesToDayHourMin_Array( int minute) {
        int[] result = new int[3];
        result[0] =  minute/24/60;
        result[1] = minute/60%24;
        result[2] = minute%60;
        return result;
    }
    public static int convert_dayHourMinToMinutes(int days,int hours, int min){
        return days*24*60+ hours*60+min;
    }

}
