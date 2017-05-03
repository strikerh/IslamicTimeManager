package com.example.hazemnabil.islamictodo2.objData;

import android.content.Context;

import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by hazem.nabil on 5/1/2017.
 */

public class Day {
    private Context mContext;

    public String   _dayOfWeek_s;
    public int      _dayOfWeek_n;
    public int      _day_n       ,_day_alt_n;
    public int _month_n112, _month_alt_n112;
    public int _month_n011, _month_alt_n011;

    public int      _year_n      ,_year_alt_n;
    public String   _month_s     ,_month_alt_s;
    public String _dayWithMonth_s,_dayWithMonth_alt_s;

    public Locale locateAr = new Locale("ar");


    public Task[] tasks;
    public Calendar mCal;
    public Calendar  hCal;

    public Day(Context mContext, int dayNum,int _month_n011,int year) {
        this.mContext = mContext;
        this.tasks = tasks;
        this.setCalenders (CalOption.MILADY,year,_month_n011,dayNum);
        this.setAllAttribute();

    }
    public Day(Context mContext,Calendar cal) {
        this.mContext = mContext;
        this.tasks = tasks;
        this.setCalenders (CalOption.MILADY,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
        this.setAllAttribute();
    }



    /// Setter

    public void setCalenders (int MorH , int year,int month_011, int day){
        if (MorH == CalOption.MILADY) {
            mCal = Calendar.getInstance();
            mCal.set(year, month_011, day);

            hCal = new UmmalquraCalendar();
            hCal.setTime(mCal.getTime());

        }else if(MorH == CalOption.HEJRY){
            hCal = new UmmalquraCalendar();
            hCal.set(year, month_011, day);

            mCal = Calendar.getInstance();
            mCal.setTime(hCal.getTime());


        }

    }
    public void setAllAttribute(){

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

    public void fillItWithTasks(){
        tasks =  getTasksArray();
    }




    public TasksList getTasks() {
//        Random rand = new Random();
//
//        int  n = rand.nextInt(10) ;
//
//        Task tasks[] = new Task[n];
//
//        for (int i = 0; i < n; i++) {
//            tasks[i] = createRandomTask();
//        }

        TasksList tasksList = new TasksList(mContext);
        tasksList.prepareDayTasks(this._day_n,this._month_n011,this._year_n);

        return tasksList;
    }

    public Task[] getTasksArray() {
        return  getTasks().getTasksArray();
    }
    public List<Task> getTasksList() {
        return  getTasks().getTasksList();
    }







}
