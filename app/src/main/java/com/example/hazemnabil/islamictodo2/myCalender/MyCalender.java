package com.example.hazemnabil.islamictodo2.myCalender;

import android.content.Context;

import com.example.hazemnabil.islamictodo2.colection.AppOptions;
import com.example.hazemnabil.islamictodo2.colection.Vars;
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by hazem.nabil on 5/21/2017.
 */
@Deprecated
public class MyCalender {

    private Context mContext;

    public SmallDate _smallDate;
    public String   _dayOfWeek_s;
    public int      _dayOfWeek_n;
    public int      _day_n       ,_day_alt_n;
    public int _month_n112, _month_alt_n112;
    public int _month_n011, _month_alt_n011;

    public int      _year_n      ,_year_alt_n;
    public String   _month_s     ,_month_alt_s;
    public String _dayWithMonth_s,_dayWithMonth_alt_s;

    public Locale locateAr = new Locale("ar");


    public Calendar mCal;
    public Calendar  hCal;

    public class Str {

        public String   dayOfWeek;
        public String   month     ,month_alt;
        public String dayWithMonth,dayWithMonth_alt;
        public String alt2monthsAndYear;
    }

    /// Constructor
    public MyCalender(Context mContext) {
        this.mContext = mContext;
        mCal = Calendar.getInstance();
        this.setCalenders (Vars.D.MILADY,mCal.get(Calendar.DAY_OF_MONTH),mCal.get(Calendar.MONTH), mCal.get(Calendar.YEAR));

    }
    public MyCalender(Context mContext, int dayNum,int _month_n011,int year) {
        this.mContext = mContext;
        this.setCalenders (Vars.D.MILADY,dayNum,_month_n011, year);

    }
    public MyCalender(Context mContext,Calendar cal) {
        this.mContext = mContext;
        this.setCalenders (Vars.D.MILADY,cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.MONTH),cal.get(Calendar.YEAR));

    }



    /// Setter

    public void setCalenders (int miladyOrHijry , int day, int month_011, int year){
        _smallDate = new SmallDate(day,month_011,year);

        if (miladyOrHijry == Vars.D.MILADY) {
            mCal = Calendar.getInstance();
            mCal.set(year, month_011, day);

            hCal = UmmalquraCalendar.getInstance();
            hCal.setTime(mCal.getTime());

        }else if(miladyOrHijry == Vars.D.HIJRY){
            hCal = UmmalquraCalendar.getInstance();
            hCal.set(year, month_011, day);

            mCal = Calendar.getInstance();
            mCal.setTime(hCal.getTime());
        }

        this.setAllAttribute();
    }
    public void setCalenders (int miladyOrHijry ,Calendar cal){
        setCalenders (miladyOrHijry,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
    }
    public void setCalenders (int miladyOrHijry ,SmallDate sDate){
        setCalenders (miladyOrHijry,sDate.day,sDate.month011,sDate.year);
    }


    private void setAllAttribute(){

        this._dayOfWeek_n = mCal.get(Calendar.DAY_OF_WEEK);
        this._dayOfWeek_s = mCal.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.SHORT,locateAr);

        this._day_n = mCal.get(Calendar.DAY_OF_MONTH);
        this._month_n112 = mCal.get(Calendar.MONTH)+1;
        this._month_n011 = mCal.get(Calendar.MONTH);
        this._year_n = mCal.get(Calendar.YEAR);
        this._month_s =  mCal.getDisplayName(Calendar.MONTH,Calendar.SHORT,locateAr);
        this._dayWithMonth_s =_day_n +" "+ _month_s ;

        this._day_alt_n = hCal.get(Calendar.DAY_OF_MONTH);
        this._month_alt_n112 = hCal.get(Calendar.MONTH)+1;
        this._month_alt_n011 = hCal.get(Calendar.MONTH);
        this._year_alt_n = hCal.get(Calendar.YEAR);
        this._month_alt_s = hCal.getDisplayName(Calendar.MONTH,Calendar.SHORT,locateAr);
        this._dayWithMonth_alt_s =_day_alt_n +""+ _month_alt_s ;


    }

    public static String getWeekName(int num){
        String result;
        int i;
        String weekAr[] = new String[7];
        weekAr[0] = "أحد";
        weekAr[1] = "أثنين";
        weekAr[2] = "ثلاثاء";
        weekAr[3] = "أربعاء";
        weekAr[4] = "خميس";
        weekAr[5] = "جمعة";
        weekAr[6] = "سبت";

        String weekEn[] = new String[7];
        weekEn[0] = "Sun";
        weekEn[1] = "Mon";
        weekEn[2] = "Tus";
        weekEn[3] = "Wen";
        weekEn[4] = "Thu";
        weekEn[5] = "Fri";
        weekEn[6] = "Sat";

        i  = AppOptions.firstDayOfWeek +num;
        if (i>7) i-=7;
        if (i>7) i-=7;

        if(AppOptions.lang == Vars.LANG.AR)
            result =   weekAr[i-1] ;
        else
            result =   weekEn[i-1] ;

        return result;

    }





    //////////// Other Methods //////////////////////////////////////





    ////// Converter //////////////////////////
    public static UmmalquraCalendar convertToHijry(int year ,int month , int day){
        GregorianCalendar gCal = new GregorianCalendar(year, month, day);
        UmmalquraCalendar uCal = new UmmalquraCalendar();
        uCal.setTime(gCal.getTime());

        return uCal;
    }

    public static Calendar convertToMilady(int year ,int month , int day){
        GregorianCalendar gCal = new GregorianCalendar();
        UmmalquraCalendar uCal = new UmmalquraCalendar(year, month, day);
        gCal.setTime(uCal.getTime());

        return gCal;
    }

    public static Calendar convertToHijry(Calendar gCal){
        Calendar uCal = UmmalquraCalendar.getInstance() ;
        uCal.setTime(gCal.getTime());

        return uCal;
    }

    public static Calendar convertToMilady(Calendar uCal){
        Calendar gCal = Calendar.getInstance();
        gCal.setTime(uCal.getTime());

        return gCal;
    }




}







