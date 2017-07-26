package com.example.hazemnabil.islamictodo2.objData;

import com.example.hazemnabil.islamictodo2.monthCalender.MoTask;
import com.example.hazemnabil.islamictodo2.myCalender.MyDate;

import java.util.ArrayList;


public class MoDayHolder {
    public MyDate myDate;
    public ArrayList<MoTask> tasksList;
    public boolean isOutOfTheMonth = false;


    public MoDayHolder(MyDate myDate, ArrayList<MoTask> tasksList) {
        this.tasksList = tasksList;
        this.myDate = myDate;

    }
    public void  setTasksList(ArrayList<MoTask> tasksList) {
        this.tasksList = tasksList;

    }

    public boolean checkIsOutOfTheMonth(int otherMonth011){
        if(otherMonth011 != myDate.getMonth011() ){
            isOutOfTheMonth = true;

        }
        return isOutOfTheMonth;
    }


}

