package com.example.hazemnabil.islamictodo2.myCalender;

import java.util.Calendar;

/**
 * Created by hazem.nabil on 5/2/2017.
 */

public class SmallDate {
    public int day;
    public int month011;
    public int year;

    public SmallDate(int day, int month011, int year) {
        this.set( day, month011, year);
    }
    public SmallDate(Calendar cal) {
        this.set(cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.MONTH),cal.get(Calendar.YEAR));

    }
    public void set(int day, int month011, int year){
        this.day = day;
        this.month011 = month011;
        this.year = year;

    }
}
