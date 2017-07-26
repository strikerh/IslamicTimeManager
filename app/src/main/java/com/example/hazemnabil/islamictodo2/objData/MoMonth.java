package com.example.hazemnabil.islamictodo2.objData;

import android.content.Context;
import android.util.Log;

import com.example.hazemnabil.islamictodo2.calenderDay.TasksList;
import com.example.hazemnabil.islamictodo2.colection.AppOptions;
import com.example.hazemnabil.islamictodo2.colection.Vars;
import com.example.hazemnabil.islamictodo2.myCalender.MyCalender;
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by hazem.nabil on 27/03/2017.
 */
@Deprecated
public class MoMonth {

    private Context mContext;

    public String monthName;
    public String monthNameAlt; // always must be 2 months separated by ","
    public int month_n011;    //  0-11
    public int month_n112;    //  1-12
    public int year;
    public int yearAlt;
    public int daysCount;

    public MoDays[] calenderDaysTable;

    private Locale locateAr = new Locale("ar");
    public Calendar mycal;



    public MoMonth(Context mContext, int month_n011, int year) {
        this.mContext = mContext;

        this.month_n011 = month_n011;    // 0-11
        this.month_n112 = month_n011 + 1;    // 1-12
        this.year = year;

        this.setCalender(month_n011,year);
        this.monthName = mycal.getDisplayName(Calendar.MONTH,Calendar.SHORT,locateAr);

        this.daysCount = countDays();


        this.createCalenderDays();

        this.firstDayOfWeek();
    }

    public void createCalenderDays() {
        int count = 7*6; //42 +1
        mycal.get(Calendar.WEEK_OF_MONTH);
        int startingDate = mycal.get(Calendar.DAY_OF_WEEK);
        if(startingDate>=7) startingDate-=7;
        MoDays[] days = new MoDays[count+1];
       //if(mycal.getFirstDayOfWeek()>1)
        for (int i = 1-startingDate; i <= count-startingDate; i++) {
            days[i+startingDate] = new MoDays(mContext, this, i, null);
        }
        this.calenderDaysTable = days;
    }


    public void setCalender(int month011,int year){
        if(AppOptions.dateType == Vars.D.MILADY) {
            this.mycal = Calendar.getInstance();
            this.mycal.set(year, month011, 1);
            this.mycal.setFirstDayOfWeek(Calendar.SATURDAY);
        }else {

        }
    }

    public int countDays(){
        int daysCount = 0;
        daysCount = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
        Log.i("hazem", "countDays:daysCount: "+daysCount);
        return daysCount;
    }

    public String getMonthNameAlt(){

        UmmalquraCalendar m1 = MyCalender.convertToHijry(this.year, this.month_n011, 1);
        UmmalquraCalendar m2 = MyCalender.convertToHijry(this.year, this.month_n011,  countDays());
        this.monthNameAlt = m1.getDisplayName(Calendar.MONTH,Calendar.LONG,new Locale("ar")) +" , "+  m2.getDisplayName(Calendar.MONTH,Calendar.LONG,new Locale("ar")) +" "+ String.valueOf(m2.get(Calendar.YEAR))+"هـ" ;
        return this.monthNameAlt;

    }
    public String getYearNameAlt(){

        UmmalquraCalendar m1 = MyCalender.convertToHijry(this.year, this.month_n011, 1);
        UmmalquraCalendar m2 = MyCalender.convertToHijry(this.year, this.month_n011,  countDays());
        this.monthNameAlt = m1.getDisplayName(Calendar.MONTH,Calendar.SHORT,new Locale("ar")) +" , "+  m2.getDisplayName(Calendar.MONTH,Calendar.SHORT,new Locale("ar"));
        return this.monthNameAlt;

    }


    public MoDays  getMoDay(int position){
        MoDays tday = calenderDaysTable[position];
        return tday;
    }
    public String getCalDayDataAt(int position,String  whatINeed){
        MoDays tday = calenderDaysTable[position];



            if (whatINeed == "day_n") {
                return String.valueOf(tday._day_n);

            } else if (whatINeed == "dayWithMonth_alt_s") {
                return tday._dayWithMonth_alt_s;

            } else if (whatINeed == "month") {
                return String.valueOf(tday._month_n112);
            }else {
                return String.valueOf(tday._month_n112);
            }

    }
    public Task[] getMoTasksAt(int position){
        MoDays tday = calenderDaysTable[position];
        TasksList tasksList = tday.prepareTasks() ;

        Task task[] =tasksList.getTasksArray();


        return task;
    }

    public String getMonthName(){
        GregorianCalendar gCal = new GregorianCalendar(this.year, this.month_n011, 1);

        this.monthName = gCal.getDisplayName(Calendar.MONTH,Calendar.SHORT,new Locale("ar")) ;
        return this.monthName;

    }

    public void firstDayOfWeek(){
        mycal.getFirstDayOfWeek();
    }







    public ArrayList<Task>[] getListOfArray4Tasks(){
        TasksList tl = new TasksList(mContext);
       // tl.prepareMonthTasks(month_n011,year);

        Task[] allTasks = tl.getTasksArray();

        ArrayList<Task>[] arrayOfDayTask_month = new ArrayList[32];

        for (int i = 0; i < daysCount+1 ; i++) {
            ArrayList<Task> dayTaskList = new ArrayList<>();
            arrayOfDayTask_month[i]   = dayTaskList;
        }


        int day = 0;

        for (Task task : allTasks) {
           // DateFormat format2=new SimpleDateFormat("d");"sdate" -> "2017-06-08 22:07:49 "
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ", Locale.ENGLISH);
            Calendar cc = Calendar.getInstance();
            try {
                cc.setTime(sdf.parse(task._sdate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            day = cc.get(Calendar.DAY_OF_MONTH);

                arrayOfDayTask_month[day].add(task);


        }
        Log.i(Vars.TAG, "getListOfArray4Tasks: "+arrayOfDayTask_month.length);
        return arrayOfDayTask_month;
    }
}
