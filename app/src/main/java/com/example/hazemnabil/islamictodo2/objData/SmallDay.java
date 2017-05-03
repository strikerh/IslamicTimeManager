package com.example.hazemnabil.islamictodo2.objData;

import java.util.Calendar;

/**
 * Created by hazem.nabil on 5/2/2017.
 */

public class SmallDay {
    public int day;
    public int month011;
    public int year;

    public SmallDay(int day, int month011, int year) {
        this.set( day, month011, year);
    }
    public SmallDay(Calendar cal) {
        this.set(cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.MONTH),cal.get(Calendar.YEAR));

    }
    public void set(int day, int month011, int year){
        this.day = day;
        this.month011 = month011;
        this.year = year;

    }
}
