package com.example.hazemnabil.islamictodo2.myCalender;

import com.example.hazemnabil.islamictodo2.colection.AppOptions;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by hazem.nabil on 6/11/2017.
 */

public class MyTime extends PrayTime {
    public ArrayList<String> prayerTimes;
    private static final String[] timeNames = { " منتصف الليل","الفجر","شروق الشمس","الظهر","العصر","المغرب","العشاء","منتصف الليل"};

    public MyTime() {
        super();

        this.setTimeFormat(FLOATING);
        this.setCalcMethod(AppOptions.time_CalcMethod);
        this.setAsrJuristic(AppOptions.time_AsrJuristic);
        this.setAdjustHighLats(AppOptions.time_AdjustHighLats);
        this.tune(AppOptions.time_offsets);


        setPrayerTimes(Calendar.getInstance());
    }

    public MyTime(Calendar cal) {
        this();
        setPrayerTimes(cal);

    }

    public void setPrayerTimes(Calendar cal) {  // size 9:    Mid Night_s , Fajr , Sunrise , Dhuhr , Asr , Sunset , Maghrib , Isha , Mid Night_e
        prayerTimes = this.getPrayerTimes(cal, AppOptions.time_latitude, AppOptions.time_longitude, AppOptions.time_tZone);
        prayerTimes.add("23.99");
        prayerTimes.add(0,"0.0");
      //  String[] prayerNames = this.getTimeNames();
        prayerTimes.remove(5);

    }
    public String getPayerTimeAt(int pos) {
        return prayerTimes.get(pos);
    }
    public String getPayerTimeAt(int pos,int TimeFormat) {
        Float time = Float.valueOf(prayerTimes.get(pos));
        String result;

        if (TimeFormat == TIME_12) {
            result = (floatToTime12(time, false));
        } else if (TimeFormat == TIME_12_NS) {
            result = (floatToTime12(time, true));
        } else  if (TimeFormat == TIME_24) {
            result = (floatToTime24(time));
        }else{
            result = time.toString();
        }

        return result;

    }

    public String getPayerNameAt(int pos) {
        return timeNames[pos];
    }

    public Float time24ToFloat(int hour, int minute) {
        return (Float) (hour + minute / 60f);
    }


    public String checkWhen_str (int hour, int minute){
        int placeInTimes = checkWhen(hour,minute);
        String mean = "";
        switch (placeInTimes){
            case 0:
                mean = "قبل الفجر الساعة ";
                break;
            case 1:
                mean = "بعد الفجر الساعة ";
                break;
            case 2:
                mean = "بعد الشروق الساعة ";
                break;
            case 3:
                mean = "بعد الظهر الساعة ";
                break;
            case 4:
                mean = "بعد العصر الساعة ";
                break;
            case 5:
                mean = "بعد المغرب الساعة ";
                break;
            case 6:
                mean = "بعد المغرب الساعة ";
                break;
            case 7:
                mean = "بعد العشاء الساعة ";
                break;
        }

        return mean+" ( "+placeInTimes+")" ;
    }

    public int checkWhen(int hour, int minute) {
        float time = time24ToFloat(hour, minute);
        int result = prayerTimes.size();
        for (int i = 0; i < prayerTimes.size(); i++) {
//            if (time < Float.parseFloat(prayerTimes.get(0))) {
//                result = 0;
//                break;
//            }
            if (  i <= prayerTimes.size() - 1 && time >= Float.parseFloat(prayerTimes.get(i)) && time < Float.parseFloat(prayerTimes.get(i + 1))) {
                result = i;
                break;
            }

        }
        return result;
    }
}
