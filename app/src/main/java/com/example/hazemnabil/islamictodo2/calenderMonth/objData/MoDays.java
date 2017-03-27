package com.example.hazemnabil.islamictodo2.calenderMonth.objData;

import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MoDays {

    public String dayOfWeek;
    public int dayNum;
    public int dayNumAlt;
    public String dayWithMonthAlt;
    public int monthNum;
    public int monthNumAlt;
    public MoTask[] tasks;
    public MoMonth parentMonth;

    public MoDays(MoMonth parentMonth, int dayNum, MoTask[] tasks) {
        this.parentMonth = parentMonth;
        this.dayOfWeek = dayOfWeek;
        this.dayNum = dayNum;
        this.dayNumAlt = dayNumAlt;
        this.tasks = tasks;
        //TODO: any DayAlt must auto calculated
        //TODO: get the month from parentMonth
    }

    /// Getter
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getDayNum() {
        return dayNum;
    }

    public int getDayNumAlt() {
        return dayNumAlt;
    }

    public int getMonthNum() {
        return monthNum;
    }

    public int getMonthNumAlt() {
        return monthNumAlt;
    }

    public MoTask[] getTasks() {
        return tasks;
    }


    public String getMonthName() {
        return "Name of the month";
    }

    public String getMonthNameAlt() {
        return "Name of the Alt month";
    }

    public String getMonthAndDaysAlt() {
       /* UmmalquraCalendar cal2 = new UmmalquraCalendar();
        cal2.set(Calendar.YEAR,1395);
        cal2.set(UmmalquraCalendar.MONTH,2);
        cal2.get(Calendar.YEAR);       // 1436
        cal2.get(Calendar.MONTH);        // 5 <=> Jumada al-Akhirah
        cal2.get(Calendar.DAY_OF_MONTH); // 14
        String mmm = cal2.getDisplayName(Calendar.MONTH,Calendar.SHORT,new Locale("ar"));*/

        GregorianCalendar gCal = new GregorianCalendar(parentMonth.year, parentMonth.monthNum, dayNum);
        Calendar uCal = new UmmalquraCalendar();
        uCal.setTime(gCal.getTime());
        String mmm2 = String.valueOf(uCal.get(Calendar.DAY_OF_MONTH));
         mmm2 +=" "+ uCal.getDisplayName(Calendar.MONTH,Calendar.SHORT,new Locale("ar"));

        //Toast.makeText(this,":"+cal2.get(Calendar.YEAR)+":"+ cal2.get(Calendar.YEAR)+":"+ cal2.get(Calendar.DAY_OF_MONTH),Toast.LENGTH_SHORT).show();

        return mmm2;
    }




}

