package com.example.hazemnabil.islamictodo2.monthCalender;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.hazemnabil.islamictodo2.DbConnections;
import com.example.hazemnabil.islamictodo2.colection.Do;
import com.example.hazemnabil.islamictodo2.myCalender.MyDate;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by hazem.nabil on 27/03/2017.
 */

public class MoMonth2 {
    //-- moMonth Property
    private Context mContext;

    public String monthName;
    public String monthNameAlt; // always must be 2 months separated by ","
    public int month_n011;    //  0-11
    public int month_n112;    //  1-12
    public int year;
    public int yearAlt;
    public int daysCount;
    public int fieldCount = 7*6; //42
    public int firstDayOfMoMonth = -1000;

    public MyDate myDate;

    //-- Day Field
    public MoDayHolder[] dayField_Holders_arr;
    public MyDate[] dayField_myDates_arr;
    public ArrayList<MoTask>[] dayField_TasksLists_arr;


    public MoMonth2(Context mContext, int month_n011, int year) {
        this.mContext = mContext;
        this.setMoMonthDate(month_n011,year);


        this.month_n011 = month_n011;    // 0-11
        this.month_n112 = month_n011 + 1;    // 1-12
        this.year = year;
        this.monthName = myDate.getMonthName();//getCalendar().getDisplayName(Calendar.MONTH,Calendar.SHORT,locateAr);
        this.daysCount = countDays();


        this.createDayFields_MyDates_DayHolder();

        //this.firstDayOfWeek();
    }

    public void setMoMonthDate(int month011, int year){
        myDate = new MyDate(1,month011,year);
    }

    public int getFirstDayOfMoMonth(){
        if(firstDayOfMoMonth == -1000) {
            int DayOfWeek = myDate.getCalendar().get(Calendar.DAY_OF_WEEK);
            int firstDayOfWeek = myDate.getCalendar().getFirstDayOfWeek();

            firstDayOfMoMonth = firstDayOfWeek - (DayOfWeek);

            if (firstDayOfMoMonth <= -7) firstDayOfMoMonth += 7;
            if (firstDayOfMoMonth > 0) firstDayOfMoMonth -= 7;
        }
        return firstDayOfMoMonth+1; // adding 1 i don't know why??
    }

    public int countDays(){
        int daysCount = 0;
        daysCount = myDate.getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);
        Log.i("hazem", "countDays:daysCount: "+daysCount);
        return daysCount;
    }

    public void firstDayOfWeek(){
        myDate.getCalendar().getFirstDayOfWeek();
    }










    /*
    *       Some Function about preparation of TASKS list
    *      -------------------------------------------------
    *       1. getDataFromDb(int month011, int year)    return: Cursor
    *       2. createTasksArray(Cursor cursor)           return: MoTask[]
    *       3. prepareTasksLists_arr()                   return: ArrayList<MoTask>[]
    *
    */

    private Cursor getDataFromDb(int month011, int year) {

        // Get Categories From Database

      // Categories Categories = new Categories(mContext);
//        SparseArray<Categories.Category> allCategories = Categories.getCategories();

        // Get TASKS From Database
        //String theSelection = Task.Col.ID +" , "+ Task.Col.NAME +" , "+ Task.Col.SDATE +" , strftime('%s',"+ Task.Col.SDATE +") as secs , "+ Task.Col.IS_DONE  ;

        String rawSelection = "";
        rawSelection += "SELECT tasks.id,tasks.is_done, tasks.sdate, strftime('%d',sdate) as d_day, strftime('%m',sdate) as d_month, strftime('%Y',sdate) as d_year,";
        rawSelection +=" categories.name as cat_name, categories.color as cat_color  ";
        rawSelection +=" FROM tasks INNER JOIN categories";
        rawSelection +=" on categories.id = tasks.category";
        rawSelection +=" WHERE strftime('%m-%Y',sdate) ='" + Do.to2Digits(month011 + 1) +"-"+year + "' ;";
        DbConnections db = new DbConnections(mContext);


        return  db.rawSelection(rawSelection);
    }

    private MoTask[] createTasksArray(Cursor cursor){
        MoTask[] tasksArray = null;
        if (cursor.getCount() > 0) {
            tasksArray = new MoTask[cursor.getCount()];
            int i = 0;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                MoTask tempTask = new MoTask();
                tempTask.fillThisTask(cursor);
                tasksArray[i] = tempTask;
                cursor.moveToNext();
                i++;
            }
            cursor.close();
        }
        return tasksArray;
    }

    //////// get List Of ArrayOfTasks()
    public ArrayList<MoTask>[] prepareTasksLists_arr(){      // return: dayField_TasksLists_arr
        ArrayList<MoTask> tl = new ArrayList<>();
        Cursor cursor = getDataFromDb(month_n011,year);

        MoTask[] allTasks = createTasksArray(cursor);

         dayField_TasksLists_arr = new ArrayList[32]; // cause the ArrayList[0] is always empty

        //create Empty Array of ArrayList
        for (int i = 0; i < daysCount+1 ; i++) {
            dayField_TasksLists_arr[i]   = new ArrayList<MoTask>();
        }

        //Fill the Empty Array of ArrayList

        if (allTasks!= null) {
            for (MoTask motask : allTasks) {
                // DateFormat format2=new SimpleDateFormat("d");
                dayField_TasksLists_arr[motask.date_day].add(motask);
            }
        }
       // Log.i(Vars.TAG, "getListOfArray4Tasks: "+ dayField_TasksLists_arr.length);
        return dayField_TasksLists_arr;
    }





    /*
    *       Some Function about the Day Fields (DAY HOLDER) {{{{GETs}}}}
    *       -------------------------------------------------
    *       1. createDayFields_MyDates_DayHolder()      return: void
    *       2. getTasksListAt(int field)                return: ArrayList<MoTask>
    *       3.getMyDateAt(int field)                    return: MyDate
    *       4.getDayHolderAt(int field)                 return: MoDayHolder
    *
    *
    *   The Field: mean the field on the calender table
    *            Field is DayHolder
    *
    *  Every DayField (DayHolder) has:
    *       - myDate    : Every thing about the date
    *       - moTasks[] : All Task inside this field.
    *       - metaData  : some information (Property) about this day field (like isInMonth, isToday,...)
    *
    */


    private void createDayFields_MyDates_DayHolder() {

        dayField_Holders_arr = new MoDayHolder[fieldCount];
        dayField_myDates_arr = new MyDate[fieldCount];
        //int tempDay = startingDate;
        int tempDay = getFirstDayOfMoMonth();
        for (int i = 0; i < fieldCount ; i++) {
            dayField_myDates_arr[i] = new MyDate(tempDay,myDate.getMonth011(),myDate.getYear());
            dayField_Holders_arr[i] = new MoDayHolder( dayField_myDates_arr[i],  null);

            dayField_Holders_arr[i].checkIsOutOfTheMonth(myDate.getMonth011());

            tempDay++;
        }

    }

    /**
     * @param field : Must be from 1 to 42
     * @return ArrayList<MoTask> :  dayField_TasksList
     */
    public ArrayList<MoTask> getTasksListAt(int field,boolean forceUpdate){
        if(dayField_TasksLists_arr == null || forceUpdate ){
            dayField_TasksLists_arr =null;
            prepareTasksLists_arr();
        }

        if(field>0 && field <= daysCount)
            return dayField_TasksLists_arr[field];
        else
            return null;
    }

    public MyDate getMyDateAt(int field){

        if(dayField_myDates_arr!= null){
            createDayFields_MyDates_DayHolder();
        }
        return dayField_myDates_arr[field];
    }

//    public String getDayMetaAt(int field,String askingFor){
//
//        switch (askingFor){
//            case "":
//              ;
//            case "2":
//                ;
//            case "3":
//               ;
//        }
//    }

    /**
     *   Get the DayHolder at any field in the calender Table
     *
     * @param field the field number of the calender table.<br> must be from 0 to 41
     * @return MoDayHolder
     *          filled with: 1.myDate  -  2.TaskList   -  3.Some property
     */
    public MoDayHolder getDayHolderAt(int field){
        if(dayField_Holders_arr!= null){
            createDayFields_MyDates_DayHolder();
        }
        dayField_Holders_arr[field].setTasksList(getTasksListAt(field,false));
        return dayField_Holders_arr[field];
    }
}
