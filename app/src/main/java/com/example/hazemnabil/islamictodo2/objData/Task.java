package com.example.hazemnabil.islamictodo2.objData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;

import com.example.hazemnabil.islamictodo2.DbConnections;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hazem.nabil on 4/24/2017.
 */

public class Task {






    private Context mContext;
    public ContentValues _TaskContent;
    private MoDays parentDay;

    public Integer _id;
    public String _name;
    public String _description;

    public Date _sdate;
    public String _date_time_from;
    public String _date_time_to;
    public String _repeat;
    public String _importance;

    public int _subTasks;
    public int _userId;
    public int _category;
    public int _tags;

    public boolean _isDone;
    public boolean _isArchived;
    public Date _created_date;
    public Date _done_date;
    public Date _archived_date;

    { /*public Task(int _id, String _name, Date _sdate, String _date_time_from, String _date_time_to, String _repeat, String _importance, int _subTasks, int _userId, int _category,int _tags, String _description, boolean _isDone, boolean _isArchived, Date _created_date, Date _done_date, Date _archived_date)  {

        this._id = _id;
        this._name = _name;
        this._sdate = _sdate;
        this._date_time_from = _date_time_from;
        this._date_time_to = _date_time_to;
        this._repeat = _repeat;
        this._importance = _importance;
        this._subTasks = _subTasks;
        this._userId = _userId;
        this._category = _category;
        this._tags = _tags;
        this._description = _description;
        this._isDone = _isDone;
        this._isArchived = _isArchived;
        this._created_date = _created_date;
        this._done_date = _done_date;
        this._archived_date = _archived_date;

    }
*/}
    public Task(Context context)  {
        this.mContext = context;
        _TaskContent = new ContentValues();
    }
    public Task(Context context, ContentValues taskContent ) throws ParseException {
        this.mContext = context;
        _TaskContent = new ContentValues();
        this.fillThisTask(taskContent);

    }

    // JUST for test and must deleted
    public Task(MoDays parentDay, String taskName, int taskCategory, int taskCategoryColor, int taskTags, Boolean isDone) {
        this.parentDay = parentDay;
        this._name = taskName;
        this._category = taskCategory;
        taskCategoryColor = 0xff5544;
        this._tags = taskTags;
        this._isDone = isDone;
    }




    public int fillThisTask(Cursor taskCursor)throws ParseException {
        ContentValues taskContent =new ContentValues();
        DatabaseUtils.cursorRowToContentValues(taskCursor, taskContent);
        fillThisTask(taskContent);
        return this._id;
    }


    public void fillThisTask(ContentValues taskContent)throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (taskContent.get(Col.ID) != null)              this._id                = Integer.parseInt((String) taskContent.get(Col.ID) );
        if (taskContent.get(Col.NAME) != null)            this._name              = (String) taskContent.get(Col.NAME) ;
        if (taskContent.get(Col.SDATE) != null)           this._sdate             = dateFormat.parse((String) taskContent.getAsString(Col.SDATE));
        if (taskContent.get(Col.DATE_TIME_FROM) != null)  this._date_time_from    = (String) taskContent.get(Col.DATE_TIME_FROM) ;
        if (taskContent.get(Col.DATE_TIME_TO) != null)    this._date_time_to      = (String) taskContent.get(Col.DATE_TIME_TO) ;
        if (taskContent.get(Col.REPEAT) != null)          this._repeat            = (String) taskContent.get(Col.REPEAT) ;
        if (taskContent.get(Col.IMPORTANCE) != null)      this._importance        = (String) taskContent.get(Col.IMPORTANCE) ;
        if (taskContent.get(Col.SUB_TASKS) != null)       this._subTasks          = Integer.parseInt((String)  taskContent.get(Col.SUB_TASKS) ) ;
        if (taskContent.get(Col.USER) != null)            this._userId            = Integer.parseInt((String) taskContent.get(Col.USER) ) ;
        if (taskContent.get(Col.CATEGORY) != null)        this._category          = Integer.parseInt((String) taskContent.get(Col.CATEGORY) ) ;
        if (taskContent.get(Col.TAGS) != null)            this._tags              = Integer.parseInt((String)  taskContent.get(Col.TAGS) ) ;
        if (taskContent.get(Col.DESCRIPTION) != null)     this._description       = (String) taskContent.get(Col.DESCRIPTION) ;
        if (taskContent.get(Col.IS_DONE) != null)         this._isDone            = Boolean.parseBoolean( (String) taskContent.get(Col.IS_DONE) ) ;
        if (taskContent.get(Col.IS_ARCHIVED) != null)     this._isArchived        = Boolean.parseBoolean( (String) taskContent.get(Col.IS_ARCHIVED) );
        if (taskContent.get(Col.CREATED_DATE) != null)    this._created_date      = dateFormat.parse( taskContent.getAsString(Col.SDATE));
        if (taskContent.get(Col.DONE_DATE) != null)       this._done_date         = dateFormat.parse( taskContent.getAsString(Col.SDATE));
        if (taskContent.get(Col.ARCHIVED_DATE) != null)   this._archived_date     = dateFormat.parse( taskContent.getAsString(Col.SDATE));
    }

    public ContentValues createJson() throws JSONException {
        _TaskContent = new ContentValues();
        this._TaskContent.put(Col.ID,_id);
        this._TaskContent.put(Col.NAME,_name);
        this._TaskContent.put(Col.SDATE,_sdate.getTime());
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
        this._TaskContent.put(Col.CREATED_DATE,_created_date.getTime());
        this._TaskContent.put(Col.DONE_DATE,_done_date.getTime());
        this._TaskContent.put(Col.ARCHIVED_DATE,_archived_date.getTime());



        return _TaskContent;
    }




    /// Getter
    public String getTaskName() {
        return _name;
    }
    public String getTaskCategory() {
        return String.valueOf(_category);
    }
    public int getTaskCategoryColor() {
        return 0xffff4433;
    }
    public String getTaskTags() {
        //TODO: need to add other method to get array of tasks (not just strings with column",")
        return "";
    }
    public Boolean getDone() {
        return _isDone;
    }

    /// Setter
    public boolean setDone(Boolean done) {
        if(_id!= null) {
            boolean old = _isDone;
            _isDone = done;
            if (db_updateMe() == 0) {
                _isDone = old;
            }
        }

        return _isDone;
    }



/************************************************************************************************************
*            DATABASE  CreateTable | as OneTask:( AddRow | GetRow | EditRow |  DeleteRow )
* **********************************************************************************************************/
    public class Col {
    public static final String ID  ="id";
    public static final String NAME  ="name";
    public static final String DESCRIPTION  ="description";

    public static final String SDATE  ="sdate";
    public static final String DATE_TIME_FROM  ="date_time_from";
    public static final String DATE_TIME_TO  ="date_time_to";

    public static final String REPEAT  ="repeat";
    public static final String IMPORTANCE  ="importance";
    public static final String SUB_TASKS  ="_subTasks";
    public static final String USER  ="user";
    public static final String CATEGORY  ="category";
    public static final String TAGS  ="tags";

    public static final String IS_DONE  ="is_done";
    public static final String IS_ARCHIVED  ="is_archived";
    public static final String CREATED_DATE  ="created_date";
    public static final String DONE_DATE  ="done_date";
    public static final String ARCHIVED_DATE  ="archived_date";



}
    public static String createDbTableString(){
        String SQL_CREATE_ENTRIES ="";
        SQL_CREATE_ENTRIES  = "CREATE TABLE if not exists \""+ DbConnections.TABLE_TASKS +"\"      ("+
                Col.ID         +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Col.NAME               +" TEXT, " +
                Col.SDATE              +" TEXT, " +
                Col.DATE_TIME_FROM     +" TEXT, " +
                Col.DATE_TIME_TO       +" TEXT, " +
                Col.REPEAT             +" TEXT, " +
                Col.IMPORTANCE         +" TEXT, " +
                Col.SUB_TASKS          +" INTEGER, " +
                Col.USER               +" INTEGER, " +
                Col.CATEGORY           +" INTEGER, " +
                Col.TAGS               +" INTEGER, " +
                Col.DESCRIPTION        +" TEXT, " +
                Col.IS_DONE            +" INTEGER, " +
                Col.IS_ARCHIVED        +" INTEGER, " +
                Col.CREATED_DATE       +" TEXT, " +
                Col.DONE_DATE          +" TEXT, " +
                Col.ARCHIVED_DATE      +" TEXT        );";

        return SQL_CREATE_ENTRIES;
    }

    public Long     db_saveMe(){

        DbConnections dbConnections = new DbConnections(mContext);
        _TaskContent.putNull(Col.ID);
        long newRowId = dbConnections.insertData(DbConnections.TABLE_TASKS, _TaskContent);

        return newRowId;

    }
    public boolean db_getDataAndFillMeById(){


        DbConnections dbConnections = new DbConnections(mContext);
        ContentValues row = dbConnections.getFirstRow(DbConnections.TABLE_TASKS, "id = "+_id);

        try {
            fillThisTask( row );
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
    public int      db_updateMe(){
        DbConnections dbConnections = new DbConnections(mContext);
        return   dbConnections.updateRow(DbConnections.TABLE_TASKS,"id = "+_id,_TaskContent);
    }
    public void     db_deleteMe(){
        //Todo:Delete
    }


}
