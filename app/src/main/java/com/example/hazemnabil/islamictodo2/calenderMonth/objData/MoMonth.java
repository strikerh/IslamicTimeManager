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

    public MoDays[] calenderDays;

    public Calendar mycal;

    public MoMonth(int monthNum, int year) {
        this.monthName = monthName;
        this.monthNameAlt = monthNameAlt;
        this.monthNum = monthNum -1;
        this.year = year;
        this.yearAlt = yearAlt;
        this.daysCount = countDays();



        createCalenderDays();

        this.firstDayOfWeek();
    }

    public void createCalenderDays() {
        int count = 7*6; //42
        int startingDate = mycal.get(Calendar.DAY_OF_WEEK);
        if(startingDate>=7) startingDate-=7;
        MoDays[] days = new MoDays[count+1];
       //if(mycal.getFirstDayOfWeek()>1)
        for (int i = 1-startingDate; i <= count-startingDate; i++) {
            days[i+startingDate] = new MoDays(this, i, null);
        }
        this.calenderDays = days;
    }



    public int countDays(){
        int daysCount = 0;

        if(CalOption.dateType == CalOption.MILADY){
            this.mycal = Calendar.getInstance();
            this.mycal.set(this.year,this.monthNum,1);
            this.mycal.setFirstDayOfWeek(Calendar.SATURDAY);
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
    public String getCalDayDataAt(int position,String  hh){
        MoDays tday = calenderDays[position];



            if (hh == "day_n") {
                return String.valueOf(tday._day_n);

            } else if (hh == "dayWithMonth_alt_s") {
                return tday._dayWithMonth_alt_s;

            } else if (hh == "month") {
                return String.valueOf(tday._month_n);
            }else {
                return String.valueOf(tday._month_n);
            }

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
