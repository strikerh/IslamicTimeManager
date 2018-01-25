package com.example.hazemnabil.islamictodo2.monthCalender;

import android.database.Cursor;

/**
 * Created by hazem.nabil on 5/30/2017.
 */

public class MoTask{
    public int id;

    public String category_name;
    public String category_color;
    public int date_day;
    public int date_month;
    public int date_year;
    public boolean isDone;

    public MoTask() {

    }

    public int fillThisTask(Cursor taskCursor) {
       // ContentValues taskContent =new ContentValues();
       // DatabaseUtils.cursorRowToContentValues(taskCursor, taskContent);


        if (taskCursor.getString(taskCursor.getColumnIndex("id")) != null)              this.id                =  taskCursor.getInt(taskCursor.getColumnIndex("id"));
        if (taskCursor.getString(taskCursor.getColumnIndex("cat_name")) != null)        this.category_name     =   taskCursor.getString(taskCursor.getColumnIndex("cat_name"));
        if (taskCursor.getString(taskCursor.getColumnIndex("cat_color")) != null)       this.category_color     =   taskCursor.getString(taskCursor.getColumnIndex("cat_color"));
        if (taskCursor.getString(taskCursor.getColumnIndex("d_day")) != null)           this.date_day     =  Integer.valueOf(taskCursor.getString(taskCursor.getColumnIndex("d_day")));
        if (taskCursor.getString(taskCursor.getColumnIndex("d_month")) != null)         this.date_month     =   Integer.valueOf(taskCursor.getString(taskCursor.getColumnIndex("d_month")));
        if (taskCursor.getString(taskCursor.getColumnIndex("d_year")) != null)          this.date_year     = Integer.valueOf(taskCursor.getString(taskCursor.getColumnIndex("d_year")));
        if (taskCursor.getString(taskCursor.getColumnIndex("is_done")) != null)         this.isDone     =  (taskCursor.getInt(taskCursor.getColumnIndex("is_done")) == 1) ;

        return id;
    }
}
