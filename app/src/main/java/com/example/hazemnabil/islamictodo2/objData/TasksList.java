package com.example.hazemnabil.islamictodo2.objData;



import android.content.Context;
import android.database.Cursor;

import com.example.hazemnabil.islamictodo2.DbConnections;
import com.example.hazemnabil.islamictodo2.colection.Do;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hazem.nabil on 4/25/2017.
 */

public class TasksList {
    private  Context mContext;
    private List<Task> _tasksList;
    private Map<Integer, Task> _tasksMap;
    private Task[] _tasksArray;


    public TasksList(Context mContext) {
        this.mContext = mContext;
        this._tasksList = new ArrayList<Task>();
        this._tasksMap = new HashMap<Integer, Task>();
    }

    public void prepareDayTasks(int day,int month011,int year){
        try {
            //"datetime(sdate) <    datetime('"+year+"-"+ Do.to2Digits(month)+"-"+Do.to2Digits(day)+"')"
           db_getDataAndFillMe("date(sdate) = date('"+year+"-"+ Do.to2Digits(month011+1)+"-"+ Do.to2Digits(day)+"');");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    ////////    Getter
    public Task[] getTasksArray(){
        return _tasksArray;
    }
    public Map<Integer, Task> getTasksMap(){
        return _tasksMap;
    }
    public List<Task> getTasksList(){
        return _tasksList;
    }



    /************************************************************************************************************
     *            DATABASE (  GetRows )
     * **********************************************************************************************************/

    public void db_getDataAndFillMe(String where) throws ParseException {

        //String where = "datetime(sdate) =    datetime('2017-04-06')" ;

        DbConnections db = new DbConnections(mContext);
        Cursor cursor = db.getListOfRows(DbConnections.TABLE_TASKS,where);

        if (cursor.getCount() >0) {
            this._tasksArray = new Task[cursor.getCount()];
            int i = 0;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Task tempTask = new Task(this.mContext);
                int id = tempTask.fillThisTask(cursor);
                this._tasksList.add(tempTask);
                this._tasksMap.put(id,tempTask);
                this._tasksArray[i] = tempTask;
                cursor.moveToNext();
                i++;
            }
            cursor.close();
        }





    }





}






