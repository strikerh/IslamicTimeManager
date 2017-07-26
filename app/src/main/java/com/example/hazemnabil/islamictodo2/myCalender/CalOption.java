package com.example.hazemnabil.islamictodo2.myCalender;

import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

import java.util.GregorianCalendar;

/**
 * Created by hazem.nabil on 26/03/2017.
 */

@Deprecated
public  class CalOption {

    public static final int MILADY = 1;
    public static final int HIJRY = 2;

    public static int dateType = MILADY;
    public static int gDay, gMonth, gYear;

    public  CalOption(int day, int month, int year) {
        gDay = day;
        gMonth = month;
        gYear = year;
    }

    public CalOption(int day, int month, int year, int dateType1) {
        this(day, month, year);
        dateType = dateType1;
    }

    public static UmmalquraCalendar convertToHejri(int year ,int month , int day){
        GregorianCalendar gCal = new GregorianCalendar(year, month, day);
        UmmalquraCalendar uCal = new UmmalquraCalendar();
        uCal.setTime(gCal.getTime());
        //String mmm2 = String.valueOf(uCal.get(Calendar.DAY_OF_MONTH));
        //mmm2 +=" "+ uCal.getDisplayName(Calendar.MONTH,Calendar.SHORT,new Locale("ar"));
        return uCal;
    }
}





