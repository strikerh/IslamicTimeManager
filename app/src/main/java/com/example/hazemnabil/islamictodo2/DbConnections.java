package com.example.hazemnabil.islamictodo2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.hazemnabil.islamictodo2.colection.Do;
import com.example.hazemnabil.islamictodo2.colection.Vars;
import com.example.hazemnabil.islamictodo2.objData.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by hazem.nabil on 4/13/2017.
 */

public class DbConnections extends SQLiteOpenHelper {

    public static final int version = 68;
    public static final String dbName = "Tasks.db";

    public static final String TABLE_TASKS  = "tasks";
    public static final String TABLE_CATEGORIES  = "categories";
    public static final String TABLE_SUB_TASKS  = "subTasks";
    public static final String TABLE_TAGS  = "tags";
    public static final String TABLE_USERS  = "users";
    public static final String TABLE_SQLITE_SEQ = "sqlite_sequence";


    private boolean flagIsFistTime = false;
    private static final String TAG = Vars.TAG;
   // private  SQLiteDatabase db;


    public DbConnections(Context context) {
        super(context, dbName, null, version);
        SQLiteDatabase db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES ="";
       // db = this.getWritableDatabase();

        SQL_CREATE_ENTRIES  = Task.createDbTableString();

        db.execSQL(SQL_CREATE_ENTRIES);
        SQL_CREATE_ENTRIES = "CREATE TABLE if not exists \""+ TABLE_CATEGORIES +"\" (\"id\" INTEGER PRIMARY KEY AUTOINCREMENT, \"name\" TEXT, \"color\" TEXT, \"description\" TEXT, \"source\" TEXT);\n";
        db.execSQL(SQL_CREATE_ENTRIES);
        SQL_CREATE_ENTRIES = "CREATE TABLE if not exists \""+ TABLE_SUB_TASKS +"\" (\"id\" INTEGER PRIMARY KEY AUTOINCREMENT, \"TaskName\" TEXT, \"isDone\" TEXT, \"dateOfDone\" TEXT, \"order\" TEXT);\n";
        db.execSQL(SQL_CREATE_ENTRIES);
        SQL_CREATE_ENTRIES = "CREATE TABLE if not exists \""+ TABLE_TAGS +"\"     (\"id\" INTEGER PRIMARY KEY AUTOINCREMENT, \"name\" TEXT, \"color\" TEXT, \"defaultCategory\" TEXT, \"description\" TEXT);\n";
        db.execSQL(SQL_CREATE_ENTRIES);
        SQL_CREATE_ENTRIES = "CREATE TABLE if not exists \""+ TABLE_USERS +"\"    (\"id\" INTEGER PRIMARY KEY AUTOINCREMENT, \"name\" TEXT);";
        db.execSQL(SQL_CREATE_ENTRIES);
//        SQL_CREATE_ENTRIES += "CREATE TABLE if not exists \"date\"     (\"id\" INTEGER PRIMARY KEY AUTOINCREMENT, \"dateType\" TEXT, \"date\" TEXT, \"otherDate\" TEXT);";
//        SQL_CREATE_ENTRIES += "CREATE TABLE if not exists \"time\"     (\"id\" INTEGER PRIMARY KEY AUTOINCREMENT, \"isBoth\" TEXT, \"timeName\" TEXT, \"startTime\" TEXT, \"duration\" TEXT);";
//        SQL_CREATE_ENTRIES += "CREATE TABLE if not exists \"timeName\" (\"id\" INTEGER PRIMARY KEY AUTOINCREMENT, \"timeName\" TEXT, \"from\" TEXT, \"to\" TEXT, \"description\" TEXT);";
//        SQL_CREATE_ENTRIES += "CREATE TABLE if not exists \"importance\" (\"id\" INTEGER PRIMARY KEY AUTOINCREMENT, \"name\" TEXT, \"starsCounts\" TEXT);";
//        SQL_CREATE_ENTRIES += "CREATE TABLE if not exists \"repeat\"   (\"id\" INTEGER PRIMARY KEY AUTOINCREMENT, \"type\" TEXT, \"weeklyDays\" TEXT, \"counts\" TEXT);";
        Log.i(TAG, "onCreate: created database");




        //dummyData();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String SQL_DELETE_ENTRIES;
        //SQLiteDatabase db2 = this.getWritableDatabase();

        SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS \""+ TABLE_TASKS +"\" ;" ;
        db.execSQL(SQL_DELETE_ENTRIES);
        SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS \""+TABLE_CATEGORIES+"\" ;" ;
        db.execSQL(SQL_DELETE_ENTRIES);
        SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS \""+TABLE_SUB_TASKS+"\"; " ;
        db.execSQL(SQL_DELETE_ENTRIES);
        SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS \""+TABLE_TAGS+"\" " ;
        db.execSQL(SQL_DELETE_ENTRIES);
        SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS \""+TABLE_USERS+"\" ;" ;
        db.execSQL(SQL_DELETE_ENTRIES);
//      SQL_DELETE_ENTRIES += "DROP TABLE IF EXISTS \"date\"; " ;
//      SQL_DELETE_ENTRIES += "DROP TABLE IF EXISTS \"importance\" ;" ;
//      SQL_DELETE_ENTRIES += "DROP TABLE IF EXISTS \"repeat\" ;" ;
//      SQL_DELETE_ENTRIES += "DROP TABLE IF EXISTS \"timeName\" ;" ;



        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.i(TAG, "------- ++========== onOpen: before ");
        super.onOpen(db);
        Log.i(TAG, "------- ++========== onOpen: after ");
    }

/*********************************************************************************************************************************
 *                                   Insert init Data Randomly
 * *******************************************************************************************************************************   */

    public void dummyData() {
        //SQLiteDatabase db2 = this.getWritableDatabase();


        if (checkDbIsNew()) {
            ContentValues values = new ContentValues();
            //name" TEXT, "color" TEXT, "" TEXT, "source" TEXT
            insertCategory("عمل", "ff5533", "ghghgh" , "google.ocm");
            insertCategory("شخصي", null, "شخصي" , null);
            insertCategory("ديني", null, "ديني" , null);
            insertCategory("المنزل", null, "المنزل" , null);

            Random rand = new Random();
            insertUser("Hazem");
            int d= 1;
            int m = 4;
            for (int i = 0; i <40 ; i++) {
                if (d <30) {
                    d++;
                }else {
                    d=1;
                    m++;
                }
                for (int j = 0; j <rand.nextInt(7) ; j++) {
                    makeRandTask( d,m);
                }

            }







            }


    }
    public void makeRandTask( int day,int month) {
        String tasksNameStrings[] = new String[14];
        tasksNameStrings[0] =  "ترتيب الأفكار";
        tasksNameStrings[1] =  "تقييم الجدوى";
        tasksNameStrings[2] =  "فرصة للتعرف أكثر على السوق وعن قرب";
        tasksNameStrings[3] =  " بحث الاحتمالات الممكنة لتمويل وتنفيذ وتسويق المشروع";
        tasksNameStrings[4] =  "التخطيط ووضوح الطريق";
        tasksNameStrings[5] =  "التحقق من الجاهزية";
        tasksNameStrings[6] =  "استطلاع الصعوبات المتوقعة والاستعداد لها والاحتياط للطوارئ";
        tasksNameStrings[7] =   "تحديد المتطلبات بشكل أكثر دقة وواقعية";
        tasksNameStrings[8] =   " إظهار الجدية في العمل";
        tasksNameStrings[9] =  " تسهيل تقييم المشروع للحصول على دعم أو تمويل أو مشاركة";
        tasksNameStrings[10] =  " التقليل من احتمالية الإخفاق أو الفشل أو الخسائر";
        tasksNameStrings[11] =  " التحكم وضبط التكاليف";
        tasksNameStrings[12] =  "        التنفيذ";
        tasksNameStrings[13] =   " خطوات اعداد خطة العمل" ;

        Random rand = new Random();

        //String dateTimeFrom = "{\"type\":\"m\",\"date\":\"20171230\",\"time\":\"1530\",\"tname\":\"1\",\"tnameModify\":\"+5\"}";
        int hours = rand.nextInt(23);
        int m = rand.nextInt(59);
        int s = rand.nextInt(59);
        String sDate = "2017-"+ Do.to2Digits(month)+"-"+ Do.to2Digits(day)+" "+ Do.to2Digits(hours)+":"+ Do.to2Digits(m)+":"+ Do.to2Digits(s)+" ";
        JSONObject dateTimeFrom = new JSONObject();
        JSONObject dateTimeTo = new JSONObject();
        JSONObject repeat = new JSONObject();

        try {
            dateTimeFrom.put("type", "m");
            dateTimeFrom.put("date", "2017/0"+month+"/0"+day);
            dateTimeFrom.put("time", hours+":"+m+":"+s);
            dateTimeFrom.put("tname",rand.nextInt(7) );
            dateTimeFrom.put("tnamemodify", "+5min");

            dateTimeTo.put("type", "m");
            dateTimeTo.put("date", "2017/12/30");
            dateTimeTo.put("time", "15:30:00");
            dateTimeTo.put("tname", "1");
            dateTimeTo.put("tnamemodify", "+5min");

            repeat.put("type", "weekly");
            repeat.put("counts", 5);
            repeat.put("weeklydays", "0245");

        } catch (JSONException e) {
            e.printStackTrace();
        }



        String tasksName = tasksNameStrings[rand.nextInt(13)];


        int importance = rand.nextInt(3);
        int subTasks = 0;
        int userId = 1;
        String description = tasksNameStrings[rand.nextInt(13)];
        int isArchived = rand.nextInt(1);
        int isDone = rand.nextInt(1);
        String createdDate = "";

        Log.i(TAG, "makeRandTask: "+dateTimeFrom.toString());
        long h = insertTask( tasksName,sDate, dateTimeFrom.toString(), dateTimeTo.toString(), repeat.toString(), importance, subTasks, userId, description, isDone, isArchived,  createdDate);
        Log.i(TAG, "makeRandTask: is Done "+h);


    }
    public long insertCategory(String name,String color,String description ,String source){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("color", color);
        values.put("description", description);
        values.put("source", source);
        long newRowId = db.insert(TABLE_CATEGORIES, null, values);
        Log.i(TAG, "dummyData: Categoryid: " + newRowId);
        return newRowId;
    }
    public long insertUser(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);

        long newRowId = db.insert(TABLE_USERS, null, values);
        Log.i(TAG, "dummyData: UserId: " + newRowId);
        return newRowId;
    }

    public long insertTask(String name,String sDate,String dateTimeFrom,String dateTimeTo,String repeat,int importance,int subTasks,int userId,String description,int isDone,int isArchived, String createdDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Task.Col.NAME, name);
        values.put(Task.Col.DATE_TIME_FROM, dateTimeFrom);
        values.put(Task.Col.SDATE, sDate);
        values.put(Task.Col.DATE_TIME_TO, dateTimeTo);
        values.put(Task.Col.REPEAT, repeat);
        values.put(Task.Col.IMPORTANCE, importance);
        values.put(Task.Col.SUB_TASKS, subTasks);
        values.put(Task.Col.USER, userId);
        values.put(Task.Col.DESCRIPTION, description);
        values.put(Task.Col.IS_DONE, isDone);
        values.put(Task.Col.IS_ARCHIVED, isArchived);
        values.put(Task.Col.CREATED_DATE, createdDate);

        long newRowId = db.insert(TABLE_TASKS, null, values);
        Log.i(TAG, "dummyData: TagId: " + newRowId);
        return newRowId;
    }


    private boolean checkDbIsNew(){
        Log.i(TAG, "------- ++==========  checkDbIsNew: before");
        SQLiteDatabase db = this.getReadableDatabase();
        Log.i(TAG, "------- ++==========  checkDbIsNew: after");
        Cursor cursor = db.rawQuery("select * from "+TABLE_SQLITE_SEQ,null);
        Log.i(TAG, "checkDbIsNew: cursor.getCount(): "+cursor.getCount() );
        //cursor.moveToFirst();
        //Log.i(TAG, "checkDbIsNew: name: "+cursor.getString(0) +" -  seq: "+cursor.getString(1) );
        flagIsFistTime = cursor.getCount() == 0;


        Log.i(TAG, "checkDbIsNew: return flagIsFistTime: "+flagIsFistTime );
        cursor.close();
        return flagIsFistTime;
    }


    /*********************************************************************************************************************************
     *                                   GETing Data
     * *******************************************************************************************************************************   */
    public Cursor getMoTaskAtDay(int day, int month, int year){


        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor cursor = db2.rawQuery("select * from "+TABLE_TASKS +" WHERE datetime(sdate) =    datetime('2017-04-06') " ,null);

        Task[] moTasks ;
        Log.i(TAG, "checkDbIsNew: cursor.getCount(): "+cursor.getCount() );
//        if(cursor.getCount()>0) {
//            moTasks = new MoTask[cursor.getCount()] ;
//
//
//        }
        //cursor.close();
        return cursor;
    }


    public Cursor selectFromTask(String where){
        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor cursor = db2.rawQuery("select * from "+TABLE_TASKS +" "+where ,null);
        return cursor;
    }




    /**************************************************************************************
     *                    Inserting Any Data in Any Table
     * ************************************************************************************   */

    public long insertData(String tableName,ContentValues Data){
        SQLiteDatabase db = this.getWritableDatabase();
        long newRowId = db.insert(tableName, null, Data);
        return newRowId;
    }

    public ContentValues getFirstRow(String tableName,String whereQuery){
        ContentValues result = new ContentValues();
        SQLiteDatabase db2 = this.getReadableDatabase();

        if(whereQuery!=null)    whereQuery = "where "+whereQuery;

        Cursor cursor = db2.rawQuery("select * from "+tableName +" "+whereQuery ,null);
        cursor.moveToFirst();
        DatabaseUtils.cursorRowToContentValues(cursor, result);
        cursor.close();
        return result;


    }
    public Cursor getListOfRows(String tableName,String whereQuery){
        SQLiteDatabase db2 = this.getReadableDatabase();
        if(whereQuery!=null)    whereQuery = " where "+whereQuery;
        Cursor cursor = db2.rawQuery("select * from "+tableName + whereQuery ,null);
        return cursor;

    }
    public int updateRow(String tableName,String whereQuery,ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();
        int count = db.update(
                tableName,
                values,
                whereQuery,
                null);

        return count;
    }




/****************************************************
*                    OTHERS
* ***************************************************   */




}
