package com.example.hazemnabil.islamictodo2.calenderMonth.objData;

import android.util.Log;

import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by hazem.nabil on 27/03/2017.
 */

public class MoMonth {



    public String monthName;
    public String monthNameAlt; // always must be 2 months separated by ","
    public int monthNum;
    public int year;
    public int yearAlt;
    public int daysCount;
    public MoDays[] days;

    public Calendar mycal;

    public MoMonth(int monthNum, int year) {
        this.monthName = monthName;
        this.monthNameAlt = monthNameAlt;
        this.monthNum = monthNum -1;
        this.year = year;
        this.yearAlt = yearAlt;
        this.daysCount = countDays();
        this.days = createDays();

        this.firstDayOfWeek();
    }

    public MoDays[] createDays() {
        MoDays[] days = new MoDays[daysCount];
        for (int i = 0; i < daysCount; i++) {
            days[i] = new MoDays(this, i, null);
        }
        return days;
    }

    public int countDays(){
        int daysCount = 0;

        if(CalOption.dateType == CalOption.MILADY){
            this.mycal = Calendar.getInstance();
            this.mycal.set(this.year,this.monthNum,1);
            daysCount = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
        }else {

        }


        Log.i("hazem", "countDays:daysCount: "+daysCount);
        return daysCount;
    }

    public String getMonthNameAlt(){

        UmmalquraCalendar m1 = CalOption.convertToHejri(this.year, this.monthNum, 1);
        UmmalquraCalendar m2 = CalOption.convertToHejri(this.year, this.monthNum,  countDays());
        this.monthNameAlt = m1.getDisplayName(Calendar.MONTH,Calendar.SHORT,new Locale("ar")) +" , "+  m2.getDisplayName(Calendar.MONTH,Calendar.SHORT,new Locale("ar"));
        return this.monthNameAlt;

    }

    public String getMonthName(){
        GregorianCalendar gCal = new GregorianCalendar(this.year, this.monthNum, 1);

        this.monthName = gCal.getDisplayName(Calendar.MONTH,Calendar.SHORT,new Locale("ar")) ;
        return this.monthName;

    }

    public void firstDayOfWeek(){
        mycal.getFirstDayOfWeek();
    }
}
