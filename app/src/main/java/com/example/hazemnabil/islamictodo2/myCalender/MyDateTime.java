package com.example.hazemnabil.islamictodo2.myCalender;

import com.example.hazemnabil.islamictodo2.colection.Vars;

import java.util.Calendar;

/**
 * Created by hazem.nabil on 28/01/2018.
 */

public class MyDateTime {

    private MyDate myDate;
    private MyTime myTime;

    private int _dateType;
    private int _day;
    private int _month011;
    private int _year;

    private int _hour;
    private int _minute;
    private int _second;
    private int _timeNameId;

    private int _dateStatue;




    public MyDateTime(MyDate myDate, MyTime myTime) {


        if(myDate != null){
            this.setDate(myDate);

            if(myTime != null){
                this.setTime(myTime);
                _dateStatue = Vars.DATE_STATUE.HAS_DATE_HAS_TIME;

            }else{
                _dateStatue = Vars.DATE_STATUE.HAS_DATE_NO_TIME;
            }
        }else{
            _dateStatue = Vars.DATE_STATUE.NO_DATE_NO_TIME;
        }





    }

    public void setDate(MyDate myDate){
        this.myDate = myDate;
        this._dateType = myDate.getType();
        this._day = myDate.getDay();
        this._month011 = myDate.getMonth011();
        this._year = myDate.getYear();
    }
    public void setTime(MyTime myTime){
        this.myTime = myTime;
        this._hour = myTime.getTimeCalendar().get(Calendar.HOUR_OF_DAY);
        this._minute = myTime.getTimeCalendar().get(Calendar.MINUTE);
        this._second = myTime.getTimeCalendar().get(Calendar.SECOND);
        this._timeNameId = myTime.checkWhen(_hour,_minute);
    }

    public MyDate getMyDate(){
        return myDate;
    }
    public MyTime getMyTime(){
        return myTime;
    }

}
