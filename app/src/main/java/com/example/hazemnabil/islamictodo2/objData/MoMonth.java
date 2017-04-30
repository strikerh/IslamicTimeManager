package com.example.hazemnabil.islamictodo2.objData;

import android.content.Context;
import android.util.Log;

import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by hazem.nabil on 27/03/2017.
 */

public class MoMonth {

    private Context mContext;

    public String monthName;
    public String monthNameAlt; // always must be 2 months separated by ","
    public int month_n011;    //  0-11
    public int month_n112;    //  1-12
    public int year;
    public int yearAlt;
    public int daysCount;

    public MoDays[] calenderDays;

    private Locale locateAr = new Locale("ar");
    public Calendar mycal;



    public MoMonth(Context mContext, int month_n011, int year) {
        this.mContext = mContext;

        this.month_n011 = month_n011;    // 0-11
        this.month_n112 = month_n011 + 1;    // 1-12
        this.year = year;

        this.setCalender(month_n011,year);
        this.monthName = mycal.getDisplayName(Calendar.MONTH,Calendar.SHORT,locateAr);

        this.daysCount = countDays();


        this.createCalenderDays();

        this.firstDayOfWeek();
    }

    public void createCalenderDays() {
        int count = 7*6; //42
        mycal.get(Calendar.WEEK_OF_MONTH);
        int startingDate = mycal.get(Calendar.DAY_OF_WEEK);
        if(startingDate>=7) startingDate-=7;
        MoDays[] days = new MoDays[count+1];
       //if(mycal.getFirstDayOfWeek()>1)
        for (int i = 1-startingDate; i <= count-startingDate; i++) {
            days[i+startingDate] = new MoDays(mContext, this, i, null);
        }
        this.calenderDays = days;
    }


    public void setCalender(int month011,int year){
        if(CalOption.dateType == CalOption.MILADY) {
            this.mycal = Calendar.getInstance();
            this.mycal.set(year, month011, 1);
            this.mycal.setFirstDayOfWeek(Calendar.SATURDAY);
        }else {

        }
    }

    public int countDays(){
        int daysCount = 0;
        daysCount = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
        Log.i("hazem", "countDays:daysCount: "+daysCount);
        return daysCount;
    }

    public String getMonthNameAlt(){

        UmmalquraCalendar m1 = CalOption.convertToHejri(this.year, this.month_n011, 1);
        UmmalquraCalendar m2 = CalOption.convertToHejri(this.year, this.month_n011,  countDays());
        this.monthNameAlt = m1.getDisplayName(Calendar.MONTH,Calendar.LONG,new Locale("ar")) +" , "+  m2.getDisplayName(Calendar.MONTH,Calendar.LONG,new Locale("ar")) +" "+ String.valueOf(m2.get(Calendar.YEAR))+"هـ" ;
        return this.monthNameAlt;

    }
    public String getYearNameAlt(){

        UmmalquraCalendar m1 = CalOption.convertToHejri(this.year, this.month_n011, 1);
        UmmalquraCalendar m2 = CalOption.convertToHejri(this.year, this.month_n011,  countDays());
        this.monthNameAlt = m1.getDisplayName(Calendar.MONTH,Calendar.SHORT,new Locale("ar")) +" , "+  m2.getDisplayName(Calendar.MONTH,Calendar.SHORT,new Locale("ar"));
        return this.monthNameAlt;

    }

    public MoDays  getMoDay(int position){
        MoDays tday = calenderDays[position];
        return tday;
    }
    public String getCalDayDataAt(int position,String  whatINeed){
        MoDays tday = calenderDays[position];



            if (whatINeed == "day_n") {
                return String.valueOf(tday._day_n);

            } else if (whatINeed == "dayWithMonth_alt_s") {
                return tday._dayWithMonth_alt_s;

            } else if (whatINeed == "month") {
                return String.valueOf(tday._month_n112);
            }else {
                return String.valueOf(tday._month_n112);
            }

    }
    public Task[] getMoTasksAt(int position){
        MoDays tday = calenderDays[position];
        TasksList tasksList = tday.getTasks() ;

        Task task[] =tasksList.getTasksArray();


        return task;
    }

    public String getMonthName(){
        GregorianCalendar gCal = new GregorianCalendar(this.year, this.month_n011, 1);

        this.monthName = gCal.getDisplayName(Calendar.MONTH,Calendar.SHORT,new Locale("ar")) ;
        return this.monthName;

    }

    public void firstDayOfWeek(){
        mycal.getFirstDayOfWeek();
    }
}
