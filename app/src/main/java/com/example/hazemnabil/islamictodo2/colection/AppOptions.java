package com.example.hazemnabil.islamictodo2.colection;

import com.example.hazemnabil.islamictodo2.myCalender.PrayTime;

import java.util.Calendar;

/**
 * Created by hazem.nabil on 5/21/2017.
 */

public class AppOptions {

    public static  String  lang = Vars.LANG.AR;
    public static  int dateType  = Vars.D.MILADY;
    public static  int firstDayOfWeek  = Calendar.SATURDAY;

    public static  int firstActivity  = Vars.FIRST_ACTIVITY.MONTH_ACTIVITY;

    // ____ Prayer Times Options ____

    public static int time_Format = PrayTime.TIME_12;
    public static int time_CalcMethod = PrayTime.MAKKAH;
    public static int time_AsrJuristic = PrayTime.SHAFII;
    public static int time_AdjustHighLats = PrayTime.ANGLE_BASED;
    public static int[] time_offsets = { 0, 0, 0, 0, 0, 0, 0 }; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}

    public static double time_latitude =  21.5460074 ;   // Jeddah
    public static double time_longitude = 39.2115675;   // Jeddah
    public static double time_tZone = 3;                // Jeddah

    public static  class MonthCalender{
        public static  boolean  fillScreen = true;
        public static  boolean showCategoryNames = false;

    }

}