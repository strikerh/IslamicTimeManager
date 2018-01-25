package com.example.hazemnabil.islamictodo2.objData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.util.SparseArray;

import com.example.hazemnabil.islamictodo2.DbConnections;
import com.example.hazemnabil.islamictodo2.colection.Do;
import com.example.hazemnabil.islamictodo2.colection.Vars;
import com.example.hazemnabil.islamictodo2.myCalender.MyDate;
import com.example.hazemnabil.islamictodo2.myCalender.MyTime;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Created by hazem.nabil on 4/24/2017.
 */

public class Task {






    private Context mContext;
    public ContentValues _TaskContent;

    public boolean isSplitter = false;
    public int splitter_TimesNum;
    public String splitter_TimesName;
    public String splitter_time;

    public Integer _id;
    public String _name;
    public String _description;


    public String _date_time_from;
    public String _date_time_to;
    public String _repeat;
    public int _importance;

    public JSONObject _json_date_time_from ;
    public JSONObject _json_date_time_to ;
    public JSONObject _json_repeat;

    public int _subTasks;
    public int _userId;
    public int _category;
    public int _tags;

    public boolean _isDone;
    public boolean _isArchived;

    public String _sdate;
    public String _created_date;
    public String _done_date;
    public String _archived_date;

    public int _stime_h;
    public int _stime_m;
    public int _stime_s;
    public long _stime_timestamp;

    public String _catName;
    public String _catColor;
    public Categories.Category _categoryObj;
    public SparseArray<Categories.Category> allCategories;

    private MyTime myTime;
    public String _sTime_string;
    public int _posInTimes;


    public Task(Context context)  {
        this.mContext = context;
        _TaskContent = new ContentValues();
    }
    public Task(Context context,SparseArray<Categories.Category> allCategories )  {
        this.mContext = context;
        this.allCategories = allCategories;
        _TaskContent = new ContentValues();
    }
    //Default
    public Task(Context context, ContentValues taskContent) throws ParseException {
        this.mContext = context;
        _TaskContent = new ContentValues();
        this.fillThisTask(taskContent);

    }
    public Task(int splitter_timeNum, String splitter_timeName,String splitter_time)  {
        this.isSplitter = true;
        this.splitter_time = splitter_time;
        this.splitter_TimesNum = splitter_timeNum;
        this.splitter_TimesName = splitter_timeName;

    }


    // JUST for test and must deleted
    @Deprecated
    public Task(MoDays parentDay, String taskName, int taskCategory, int taskCategoryColor, int taskTags, Boolean isDone) {

        this._name = taskName;
        this._category = taskCategory;
        taskCategoryColor = 0xff5544;
        this._tags = taskTags;
        this._isDone = isDone;
    }


    public void setName(String name){
        this._name = name;
    }
    public void setDescription(String description){
        this._description = description;
    }


    /* dateStatue VARS  */
    public static final int NO_DATE_No_TIME = 0;
    public static final int HAS_DATE_HAS_TIME = 1;
    public static final int HAS_DATE_NO_TIME = 2;
    public static final int No_DATE_HAS_TIME = 3;

    private JSONObject createJsonDateTime(int dateType, int dateStatue, int day, int month, int year, int timeType, int hours24_Or_TimeName, int minutes_Or_timeModifyByMinutes){
        JSONObject dateTimeFrom = new JSONObject();


        try {
            dateTimeFrom.put("version", 2);
            dateTimeFrom.put("type", dateType);
            dateTimeFrom.put("datestatue", dateStatue);
            dateTimeFrom.put("date", year +"/" +Do.to2Digits(month)+  "/" +Do.to2Digits(day));
            dateTimeFrom.put("year", year);
            dateTimeFrom.put("month",Do.to2Digits(month));
            dateTimeFrom.put("day", Do.to2Digits(day));

            if(timeType == Vars.TIME.SPECIFIC) {
                dateTimeFrom.put("timetype", Vars.TIME.SPECIFIC);
                dateTimeFrom.put("time", hours24_Or_TimeName + ":" + minutes_Or_timeModifyByMinutes + ":" + "00");
                dateTimeFrom.put("hours", hours24_Or_TimeName);
                dateTimeFrom.put("minutes", minutes_Or_timeModifyByMinutes);

                dateTimeFrom.put("tname", "");
                dateTimeFrom.put("tnamemodify", "");
            }else {
                dateTimeFrom.put("timetype", Vars.TIME.RELATED);
                dateTimeFrom.put("tname",hours24_Or_TimeName );
                dateTimeFrom.put("tnamemodify", minutes_Or_timeModifyByMinutes);

                dateTimeFrom.put("time", "");
                dateTimeFrom.put("hours", "");
                dateTimeFrom.put("minutes", "");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dateTimeFrom;
    }

    public static final int DT_DATE_TYPE = 0;
    public static final int DT_DATE_STATUE = 1;
    public static final int DT_DAY = 2;
    public static final int DT_MONTH = 3;
    public static final int DT_YEAR = 4;
    public static final int DT_TIME_TYPE = 5;
    public static final int DT_HOURS_24_OR_TIME_NAME = 6;
    public static final int DT_MINUTES_OR_TIME_MODIFY_BY_MINUTES = 7;

public JSONObject getDateTimeFromObj(){
    if(_json_date_time_from == null){
        if(_date_time_from != null)
        try {
            _json_date_time_from = new JSONObject( _date_time_from);
            return _json_date_time_from;
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    return null;
}

    public void setStartingTime(int dateType, int dateStatue, int day,int month011,int year, int timeType, int hours24_Or_TimeName, int minutes_Or_timeModifyByMinutes){

        JSONObject dateTimeFrom = createJsonDateTime( dateType, dateStatue,  day, month011+1, year,timeType,  hours24_Or_TimeName,  minutes_Or_timeModifyByMinutes);

        if(year >1800)
            _sdate  = year+"-"+ Do.to2Digits(month011+1)+"-"+ Do.to2Digits(day)+" "+ Do.to2Digits(hours24_Or_TimeName)+":"+ Do.to2Digits(minutes_Or_timeModifyByMinutes)+":"+ "00";
        else {
            Calendar cal = MyDate.convertToMilady(year,month011,day);
            _sdate = cal.get(Calendar.YEAR) + "-" + Do.to2Digits(cal.get(Calendar.MONTH) + 1) + "-" + Do.to2Digits(cal.get(Calendar.DAY_OF_MONTH)) + " " + Do.to2Digits(hours24_Or_TimeName) + ":" + Do.to2Digits(minutes_Or_timeModifyByMinutes) + ":" + "00";
        }
        _date_time_from = dateTimeFrom.toString();
    }




    public void setEndTime(int dateType, int dateStatue, int day,int month,int year, int timeType, int hours24_Or_TimeName, int minutes_Or_timeModifyByMinutes){

        JSONObject dateTimeFrom = createJsonDateTime( dateType, dateStatue,  day, month, year,timeType,  hours24_Or_TimeName,  minutes_Or_timeModifyByMinutes);
       _date_time_to = dateTimeFrom.toString();
    }




    /**
     * @param type - can be :
     *           - Vars.REPEAT.DAILY
     *           - Vars.REPEAT.WEEKLY
     *           - Vars.REPEAT.MONTHLY
     *
     * @param counts (int) the count of repeat
     *
     * @param weeklydays (String)
     *           Ex: " 0235 "    -> mean: saturday-monday-tuesday-thursday
     *           or  " 0123456 " -> mean: all days of week
     *           or " "          -> mean: no day selected
     */
    public void setRepeat(int type,int counts,String toDate,String weeklydays) {
        JSONObject repeat = new JSONObject();
        try {
            repeat.put("type", type);
            repeat.put("counts", counts);
            repeat.put("to", toDate.toString());
            repeat.put("weeklydays", weeklydays);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this._repeat = repeat.toString();

    }
    public void setImportance(int importance){
        this._importance = importance;

    }
    public void setCategory(int categoryId){
        this._category = categoryId;
    }
    public void setTags(int tagId){
        this._tags = tagId;
        //Todo...
    }
    public void setSubTasks(){
        //Todo...

    }


    public int fillThisTask(Cursor taskCursor,  MyTime myTime)throws ParseException {
        this.myTime = myTime;
        ContentValues taskContent =new ContentValues();
        DatabaseUtils.cursorRowToContentValues(taskCursor, taskContent);
        fillThisTask(taskContent);
        return this._id;
    }


    public void fillThisTask(ContentValues taskContent)throws ParseException {

       // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (taskContent.get(Col.ID) != null)              this._id                = taskContent.getAsInteger(Col.ID) ;
        if (taskContent.get(Col.NAME) != null)            this._name              = taskContent.getAsString(Col.NAME) ;

        if (taskContent.get(Col.DATE_TIME_FROM) != null)  this._date_time_from    = taskContent.getAsString(Col.DATE_TIME_FROM) ;
        if (taskContent.get(Col.DATE_TIME_TO) != null)    this._date_time_to      = taskContent.getAsString(Col.DATE_TIME_TO) ;
        if (taskContent.get(Col.REPEAT) != null)          this._repeat            = taskContent.getAsString(Col.REPEAT) ;
        if (taskContent.get(Col.IMPORTANCE) != null)      this._importance        = taskContent.getAsInteger(Col.IMPORTANCE) ;
        if (taskContent.get(Col.SUB_TASKS) != null)       this._subTasks          = taskContent.getAsInteger(Col.SUB_TASKS)  ;
        if (taskContent.get(Col.USER) != null)            this._userId            = taskContent.getAsInteger(Col.USER) ;
        if (taskContent.get(Col.CATEGORY) != null)        this._category              = taskContent.getAsInteger(Col.CATEGORY) ;
        if (taskContent.get(Col.TAGS) != null)            this._tags              = taskContent.getAsInteger(Col.TAGS) ;
        if (taskContent.get(Col.DESCRIPTION) != null)     this._description       = taskContent.getAsString(Col.DESCRIPTION) ;
        if (taskContent.get(Col.IS_DONE) != null)         this._isDone            = taskContent.getAsInteger(Col.IS_DONE)  == 1 ;
        if (taskContent.get(Col.IS_ARCHIVED) != null)     this._isArchived        = taskContent.getAsInteger(Col.IS_ARCHIVED)  == 1;


        if (taskContent.get(Col.SDATE) != null)           this._sdate             =  taskContent.getAsString(Col.SDATE);
        if (taskContent.get(Col.CREATED_DATE) != null)    this._created_date      =  taskContent.getAsString(Col.CREATED_DATE);
        if (taskContent.get(Col.DONE_DATE) != null)       this._done_date         =  taskContent.getAsString(Col.DONE_DATE);
        if (taskContent.get(Col.ARCHIVED_DATE) != null)   this._archived_date     =  taskContent.getAsString(Col.ARCHIVED_DATE);

        if (taskContent.get("stime_h") != null)           this._stime_h           =  taskContent.getAsInteger("stime_h");
        if (taskContent.get("stime_m") != null)           this._stime_m           =  taskContent.getAsInteger("stime_m");
        if (taskContent.get("stime_s") != null)           this._stime_s           =  taskContent.getAsInteger("stime_s");
        if (taskContent.get("stime_timestamp") != null)   this._stime_timestamp   =  taskContent.getAsLong("stime_timestamp");

        if (taskContent.get("cat_name") != null)           this._catName          =  taskContent.getAsString("cat_name");
        if (taskContent.get("cat_color") != null)           this._catColor        =  taskContent.getAsString("cat_color");

        if(myTime== null) {
            Calendar cal = Calendar.getInstance();
            if(this._stime_timestamp != 0) {
                cal.setTimeInMillis(this._stime_timestamp * 1000);

                myTime = new MyTime(cal);
                this._sTime_string = myTime.checkWhen_str(this._stime_h, this._stime_m);
                this._posInTimes = myTime.checkWhen(this._stime_h, this._stime_m);
            }

        }else{
            this._sTime_string = myTime.checkWhen_str(this._stime_h, this._stime_m);
            this._posInTimes = myTime.checkWhen(this._stime_h, this._stime_m);

        }
//        if (taskContent.get(Col.CATEGORY) != null) {
//            this._category  = Integer.parseInt((String) taskContent.get(Col.CATEGORY) ) ;
//            this._categoryObj = allCategories.get(this._category);
//           // this._categoryObj.getById(this._category);
//        }



    }

    public void fillTaskContentWhisThisTask() {

       // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ContentValues taskContent = new ContentValues();

        if (this._id                != null)       taskContent.put(Col.ID               , this._id                );
        if (this._name              != null)       taskContent.put(Col.NAME             , this._name              );
        if (this._date_time_from    != null)       taskContent.put(Col.DATE_TIME_FROM   , this._date_time_from    );
        if (this._date_time_to      != null)       taskContent.put(Col.DATE_TIME_TO     , this._date_time_to      );
        if (this._repeat            != null)       taskContent.put(Col.REPEAT           , this._repeat            );
        if (this._importance        != -1)         taskContent.put(Col.IMPORTANCE       , this._importance        );
        if (this._subTasks          != -1)         taskContent.put(Col.SUB_TASKS        , this._subTasks          );
        if (this._userId            != -1)         taskContent.put(Col.USER             , this._userId            );
        if (this._category          != -1)         taskContent.put(Col.CATEGORY         , this._category          );
        if (this._tags              != -1)         taskContent.put(Col.TAGS             , this._tags              );
        if (this._description       != null)       taskContent.put(Col.DESCRIPTION      , this._description       );

        taskContent.put(Col.IS_DONE      , false);
        taskContent.put(Col.IS_ARCHIVED  , false);

        if ( this._sdate            != null)       taskContent.put(Col.SDATE           ,  this._sdate            );
        if (this._done_date       != null)       taskContent.put(Col.DONE_DATE      , this._done_date       );
        if (this._archived_date       != null)       taskContent.put(Col.ARCHIVED_DATE      , this._archived_date       );
        if (this._created_date       != null)       taskContent.put(Col.CREATED_DATE      , this._created_date       );



        _TaskContent = taskContent;


    }

    @Deprecated
    public ContentValues createJson() throws JSONException {
        _TaskContent = new ContentValues();
        this._TaskContent.put(Col.ID,_id);
        this._TaskContent.put(Col.NAME,_name);
        this._TaskContent.put(Col.SDATE,_sdate);
        this._TaskContent.put(Col.DATE_TIME_FROM,_date_time_from);
        this._TaskContent.put(Col.DATE_TIME_TO,_date_time_to);
        this._TaskContent.put(Col.REPEAT,_repeat);
        this._TaskContent.put(Col.IMPORTANCE,_importance);
        this._TaskContent.put(Col.SUB_TASKS,_subTasks);
        this._TaskContent.put(Col.USER,_userId);
        this._TaskContent.put(Col.CATEGORY,_category);
        this._TaskContent.put(Col.TAGS,_tags);
        this._TaskContent.put(Col.DESCRIPTION,_description);
        this._TaskContent.put(Col.IS_DONE,_isDone);
        this._TaskContent.put(Col.IS_ARCHIVED,_isArchived);
        this._TaskContent.put(Col.CREATED_DATE,_created_date);
        this._TaskContent.put(Col.DONE_DATE,_done_date);
        this._TaskContent.put(Col.ARCHIVED_DATE,_archived_date);



        return _TaskContent;
    }




    /// Getter

    public void getById(int id) {
        this._id = id;
        if(!this.db_getDataAndFillMeById()) {
            this._id = null;
        }
    }
    public String getTaskName() {
        return _name;
    }
    public String getTaskCategory() {
        return String.valueOf(_category);
    }
    public int getTaskCategoryColor() {
        return Color.parseColor(_categoryObj._color);
    }
    public String getTaskTags() {
        //TODO: need to add other method to get array of tasks (not just strings with column",")
        return "";
    }
    public Boolean getDone() {
        return _isDone;
    }

    public String getTime(){
        return _stime_h+":"+_stime_m;   // +":"+_stime_s;
    }
    /// Setter
    public boolean setDoneAndSave(Boolean done) {
        if(_id!= null) {
            boolean old = _isDone;
            _isDone = done;

            ContentValues  TaskContent1 = new ContentValues();
            TaskContent1.put(Col.ID,_id);
            TaskContent1.put(Col.IS_DONE,_isDone);

            if (db_updateMe(TaskContent1) == 0) {
                _isDone = old;
            }
        }

        return _isDone;
    }



/************************************************************************************************************
*            DATABASE  CreateTable | as OneTask:( AddRow | GetRow | EditRow |  DeleteRow )
* **********************************************************************************************************/
    public class Col {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";

        public static final String SDATE = "sdate";
        public static final String DATE_TIME_FROM = "date_time_from";
        public static final String DATE_TIME_TO = "date_time_to";

        public static final String REPEAT = "repeat";
        public static final String IMPORTANCE = "importance";
        public static final String SUB_TASKS = "_subTasks";
        public static final String USER = "user";
        public static final String CATEGORY = "category";
        public static final String TAGS = "tags";

        public static final String IS_DONE = "is_done";
        public static final String IS_ARCHIVED = "is_archived";
        public static final String CREATED_DATE = "created_date";
        public static final String DONE_DATE = "done_date";
        public static final String ARCHIVED_DATE = "archived_date";


    }
    public static String createDbTableString() {
        String SQL_CREATE_ENTRIES = "";
        SQL_CREATE_ENTRIES = "CREATE TABLE if not exists \"" + DbConnections.TABLE_TASKS + "\"      (" +
                Col.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Col.NAME + " TEXT, " +
                Col.SDATE + " INTEGER, " +
                Col.DATE_TIME_FROM + " TEXT, " +
                Col.DATE_TIME_TO + " TEXT, " +
                Col.REPEAT + " TEXT, " +
                Col.IMPORTANCE + " TEXT, " +
                Col.SUB_TASKS + " INTEGER, " +
                Col.USER + " INTEGER, " +
                Col.CATEGORY + " INTEGER, " +
                Col.TAGS + " INTEGER, " +
                Col.DESCRIPTION + " TEXT, " +
                Col.IS_DONE + " INTEGER, " +
                Col.IS_ARCHIVED + " INTEGER, " +
                Col.CREATED_DATE + " INTEGER, " +
                Col.DONE_DATE + " INTEGER, " +
                Col.ARCHIVED_DATE + " INTEGER        );";

        return SQL_CREATE_ENTRIES;
    }

    public Long db_saveMe(){

        DbConnections dbConnections = new DbConnections(mContext);
        _TaskContent.putNull(Col.ID);
        fillTaskContentWhisThisTask();
        long newRowId = dbConnections.insertData(DbConnections.TABLE_TASKS, _TaskContent);

        return newRowId;

    }
    public boolean db_getDataAndFillMeById(){


        DbConnections dbConnections = new DbConnections(mContext);
      // ContentValues row = dbConnections.getFirstRow(DbConnections.TABLE_TASKS, "id = "+_id);

        String rawSelection = "";
        rawSelection += "SELECT tasks.* , tasks.sdate, strftime('%d',sdate) as d_day, strftime('%m',sdate) as d_month, strftime('%Y',sdate) as d_year, strftime('%s',sdate) as stime_timestamp,";
        rawSelection +=" categories.name as cat_name, categories.color as cat_color  ";
        rawSelection +=" FROM tasks INNER JOIN categories";
        rawSelection +=" on categories.id = tasks.category";
        rawSelection +=" WHERE tasks.id = "+_id;
        DbConnections db = new DbConnections(mContext);

        ContentValues row =  new ContentValues() ;
        Cursor row5 = db.rawSelection(rawSelection);
        row5.moveToFirst();
        DatabaseUtils.cursorRowToContentValues(row5, row);


        try {
            fillThisTask( row );
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public int      db_updateMe(ContentValues TaskUpdate){
        DbConnections dbConnections = new DbConnections(mContext);
        return   dbConnections.updateRow(DbConnections.TABLE_TASKS,"id = "+_id,TaskUpdate);
    }
    public void     db_deleteMe(){
        //Todo:Delete
    }


}
