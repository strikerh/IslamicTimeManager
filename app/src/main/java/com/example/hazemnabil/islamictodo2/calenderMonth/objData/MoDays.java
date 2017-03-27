package com.example.hazemnabil.islamictodo2.calenderMonth.objData;

import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

import java.util.Calendar;
import java.util.Locale;

public class MoDays {

    public String   _dayOfWeek_s;
    public int      _dayOfWeek_n;
    public int      _day_n       ,_day_alt_n;
    public int      _month_n     ,_month_alt_n;
    public int      _year_n      ,_year_alt_n;
    public String   _month_s     ,_month_alt_s;
    public String _dayWithMonth_s,_dayWithMonth_alt_s;

    public Locale locateAr = new Locale("ar");


    public MoTask[] tasks;
    public MoMonth parentMonth;
    public Calendar  mCal;
    public Calendar  hCal;

    public MoDays(MoMonth parentMonth, int dayNum, MoTask[] tasks) {
        this.parentMonth = parentMonth;


        this.tasks = tasks;
        //TODO: any DayAlt must auto calculated
        //TODO: get the month from parentMonth

        setCalenders (parentMonth.year,parentMonth.monthNum,dayNum);
        setAllAttribute();

    }

    /// Setter

    public void setCalenders (int year,int month, int day){
        mCal = Calendar.getInstance();
        mCal.set(year,month,day);

        hCal = new UmmalquraCalendar();
        hCal.setTime(mCal.getTime());

    }
    public void setAllAttribute(){

        this._dayOfWeek_n = mCal.get(Calendar.DAY_OF_WEEK);
        this._dayOfWeek_s = mCal.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.SHORT,locateAr);

        this._day_n = mCal.get(Calendar.DAY_OF_MONTH);
        this._month_n = mCal.get(Calendar.MONTH);
        this._year_n = mCal.get(Calendar.YEAR);
        this._month_s =  mCal.getDisplayName(Calendar.MONTH,Calendar.SHORT,locateAr);
        this._dayWithMonth_s =_day_n +" "+ _month_s ;

        this._day_alt_n = hCal.get(Calendar.DAY_OF_MONTH);
        this._month_alt_n = hCal.get(Calendar.MONTH);
        this._year_alt_n = hCal.get(Calendar.YEAR);
        this._month_alt_s = hCal.getDisplayName(Calendar.MONTH,Calendar.SHORT,locateAr);
        this._dayWithMonth_alt_s =_day_alt_n +" "+ _month_alt_s ;


    }




}

