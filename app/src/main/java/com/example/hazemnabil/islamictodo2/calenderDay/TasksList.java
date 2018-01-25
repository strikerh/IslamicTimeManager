package com.example.hazemnabil.islamictodo2.calenderDay;



import android.content.Context;
import android.database.Cursor;

import com.example.hazemnabil.islamictodo2.DbConnections;
import com.example.hazemnabil.islamictodo2.colection.Do;
import com.example.hazemnabil.islamictodo2.myCalender.MyTime;
import com.example.hazemnabil.islamictodo2.myCalender.PrayTime;
import com.example.hazemnabil.islamictodo2.objData.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hazem.nabil on 4/25/2017.
 */

public class TasksList {
    private Context mContext;
    private List<Task> _timeSplitterOnly;
    private List<Task> _timeSplitterOnly_noTime;
    private List<Task> _tasksList_timeSpliter;
    private Map<Integer, Task> _tasksMap;
    private Task[] _tasksArray;


    public TasksList(Context mContext) {
        this.mContext = mContext;
        this._tasksList_timeSpliter = new ArrayList<Task>();
        this._tasksMap = new HashMap<Integer, Task>();
    }

    public void prepareDayTasks(int day, int month011, int year) {

        try {
            //"datetime(sdate) <    datetime('"+year+"-"+ Do.to2Digits(month)+"-"+Do.to2Digits(day)+"')"
           // db_getDataAndFillMe("date(sdate) = date('" + year + "-" + Do.to2Digits(month011 + 1) + "-" + Do.to2Digits(day) + "');");
            db_getDataAndFillMe(day,  month011,  year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void prepareDayTasks_WithNoTime(int day, int month011, int year) {

        try {
            //"datetime(sdate) <    datetime('"+year+"-"+ Do.to2Digits(month)+"-"+Do.to2Digits(day)+"')"
            // db_getDataAndFillMe("date(sdate) = date('" + year + "-" + Do.to2Digits(month011 + 1) + "-" + Do.to2Digits(day) + "');");
            db_getDataAndFillMe_NoTime(day,  month011,  year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    ////////    Getter
    public Task[] getTasksArray() {
        return _tasksArray;
    }

    public Map<Integer, Task> getTasksMap() {

        return _tasksMap;
    }

    @Deprecated
    public List<Task> getTasksList() {
//      if(_tasksArray != null){
//        _tasksList_timeSpliter =   new ArrayList<>(Arrays.asList(_tasksArray));
//      }else
//          _tasksList_timeSpliter = null;

        return _tasksList_timeSpliter;
    }

    public int count() {
        return _tasksArray.length;
    }


    /************************************************************************************************************
     *            DATABASE (  GetRows )
     * **********************************************************************************************************/

    public void db_getDataAndFillMe(int day, int month011, int year) throws ParseException {

        //String where = "datetime(sdate) =    datetime('2017-04-06')" ;
        Calendar cal = Calendar.getInstance();
        cal.set(year, month011, day);
        MyTime mytime = new MyTime(cal);

        DbConnections db = new DbConnections(mContext);

        Cursor cursor = getDataFromDb( day,  month011,  year);

        if (cursor.getCount() > 0) {
            _timeSplitterOnly = new ArrayList<>();


            this._tasksArray = new Task[cursor.getCount()];
            int i = 0;
            int oldPosInTimes = -1;
            int newPosInTimes = 0;
            cursor.moveToFirst();
            for (int j = 0; j < mytime.prayerTimes.size(); j++) {
                _timeSplitterOnly.add(new Task(j,mytime.getPayerNameAt(j), mytime.getPayerTimeAt(j, PrayTime.TIME_12) ) ) ;
                _tasksList_timeSpliter.add(_timeSplitterOnly.get(_timeSplitterOnly.size()-1) );
            }





            while (!cursor.isAfterLast()) {
                Task tempTask = new Task(this.mContext);
                int id = tempTask.fillThisTask(cursor, mytime);
                //this._tasksList_timeSpliter.add(tempTask);
                // this._tasksMap.put(id,tempTask);
                this._tasksArray[i] = tempTask;


                int index = _tasksList_timeSpliter.indexOf(_timeSplitterOnly.get(tempTask._posInTimes));

                this._tasksList_timeSpliter.add(index+1,tempTask);

                cursor.moveToNext();
                i++;
            }
            cursor.close();
        }


    }

    public void db_getDataAndFillMe_NoTime(int day, int month011, int year) throws ParseException {
        _timeSplitterOnly_noTime = new ArrayList<>();
        //String where = "datetime(sdate) =    datetime('2017-04-06')" ;
        Calendar cal = Calendar.getInstance();
        cal.set(year, month011, day);
        MyTime mytime = new MyTime(cal);

        DbConnections db = new DbConnections(mContext);

        Cursor cursor = getDataFromDb_noTime( day,  month011,  year);

        if (cursor.getCount() > 0) {
            _timeSplitterOnly = new ArrayList<>();

            this._tasksArray = new Task[cursor.getCount()];
            int i = 0;
            int oldPosInTimes = -1;
            int newPosInTimes = 0;
            cursor.moveToFirst();
           /* for (int j = 0; j < mytime.prayerTimes.size(); j++) {
                _timeSplitterOnly.add(new Task(j,mytime.getPayerNameAt(j), mytime.getPayerTimeAt(j, PrayTime.TIME_12) ) ) ;
                _tasksList_timeSpliter.add(_timeSplitterOnly.get(_timeSplitterOnly.size()-1) );
            }*/

            _timeSplitterOnly_noTime.add(new Task(1,"Today", "No Time" ) ) ;
            _timeSplitterOnly_noTime.add(new Task(2,"No Day", "No Time" ) ) ;

            _tasksList_timeSpliter.add(_timeSplitterOnly_noTime.get(0));
            _tasksList_timeSpliter.add(_timeSplitterOnly_noTime.get(1));


/////////// NO Date ////////////////////////

            while (!cursor.isAfterLast()) {
                Task tempTask = new Task(this.mContext);
                int id = tempTask.fillThisTask(cursor, mytime);

                JSONObject hh = tempTask.getDateTimeFromObj();
                int dateStatue = 1;
                if(tempTask._date_time_from == null){
                    dateStatue = 0;
                }
                if(hh != null)
                try {
                    dateStatue = hh.getInt("datestatue");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //this._tasksList_timeSpliter.add(tempTask);
                // this._tasksMap.put(id,tempTask);
                this._tasksArray[i] = tempTask;

                if (dateStatue == Task.HAS_DATE_NO_TIME) {
                     int index = _tasksList_timeSpliter.indexOf(_timeSplitterOnly_noTime.get(1));

                    this._tasksList_timeSpliter.add(index , tempTask);
                }else if(dateStatue == Task.NO_DATE_No_TIME){
                    this._tasksList_timeSpliter.add( tempTask);
                }
                cursor.moveToNext();
                i++;
            }
            cursor.close();
        }


    }

//    public void db_getDataAndFillMe(String where) throws ParseException {
//        db_getDataAndFillMe("*", where);
//    }

    private Cursor getDataFromDb(int day, int month011, int year) {

        String taskSlct = "SELECT tasks.id, tasks.name, tasks.description, tasks.date_time_from, " +
                "tasks.date_time_to, tasks.repeat, tasks.importance, tasks._subTasks, tasks.user, " +
                " tasks.category, tasks.tags, tasks.is_done, tasks.is_archived, " +
                " date(tasks.sdate),strftime('%H',tasks.sdate) as stime_h ,strftime('%M',tasks.sdate) as stime_m ,strftime('%S',tasks.sdate) as stime_s " +
                ", date(tasks.created_date), date(tasks.done_date), date(tasks.archived_date) " ;




        String rawSelection = "";
        rawSelection += taskSlct;
        rawSelection +=", categories.name as cat_name, categories.color as cat_color  ";
        rawSelection +=" FROM tasks INNER JOIN categories";
        rawSelection +=" on categories.id = tasks.category";
        rawSelection +=" WHERE date(sdate) = date('" + year + "-" + Do.to2Digits(month011 + 1) + "-" + Do.to2Digits(day) + "')";
        rawSelection +=  "  order by tasks.sdate;";

        DbConnections db = new DbConnections(mContext);


        return  db.rawSelection(rawSelection);
    }
    private Cursor getDataFromDb_noTime(int day, int month011, int year) {

        String taskSlct = "SELECT tasks.id, tasks.name, tasks.description, tasks.date_time_from, " +
                "tasks.date_time_to, tasks.repeat, tasks.importance, tasks._subTasks, tasks.user, " +
                " tasks.category, tasks.tags, tasks.is_done, tasks.is_archived, " +
                " date(tasks.sdate),strftime('%H',tasks.sdate) as stime_h ,strftime('%M',tasks.sdate) as stime_m ,strftime('%S',tasks.sdate) as stime_s " +
                ", date(tasks.created_date), date(tasks.done_date), date(tasks.archived_date) " ;




        String rawSelection = "";
        rawSelection += taskSlct;
        rawSelection +=", categories.name as cat_name, categories.color as cat_color  ";
        rawSelection +=" FROM tasks INNER JOIN categories";
        rawSelection +=" on categories.id = tasks.category";
        rawSelection +=" WHERE date(sdate) = date('" + year + "-" + Do.to2Digits(month011 + 1) + "-" + Do.to2Digits(day) + "') OR sdate is null ";
        rawSelection +=  "  order by tasks.sdate;";

        DbConnections db = new DbConnections(mContext);


        return  db.rawSelection(rawSelection);
    }
}






