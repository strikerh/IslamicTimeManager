package com.example.hazemnabil.islamictodo2.objData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;

import com.example.hazemnabil.islamictodo2.DbConnections;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by hazem.nabil on 5/18/2017.
 */

public class DataModel {


    private Context mContext;
    public ContentValues _CategoryContent;

    public Integer _id;
    public String _name;
    public String _color;
    public String _description;
    public String _source;

    public DataModel(Context context)  {
        this.mContext = context;
        _CategoryContent = new ContentValues();
    }

    public DataModel(Context context, ContentValues categoryContent ) throws ParseException {
        this.mContext = context;
        _CategoryContent = new ContentValues();
        this.fillThisTask(categoryContent);

    }


    public int fillThisTask(Cursor categoryCursor)throws ParseException {
        ContentValues categoryContent =new ContentValues();
        DatabaseUtils.cursorRowToContentValues(categoryCursor, categoryContent);
        fillThisTask(categoryContent);
        return this._id;
    }




    public void fillThisTask(ContentValues categoryContent)throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (categoryContent.get(Col.ID) != null)              this._id                = Integer.parseInt((String) categoryContent.get(Col.ID) );
        if (categoryContent.get(Col.NAME) != null)            this._name              = (String) categoryContent.get(Col.NAME) ;
        if (categoryContent.get(Col.COLOR) != null)           this._color             =  (String) categoryContent.get(Col.COLOR);
        if (categoryContent.get(Col.DESCRIPTION) != null)     this._description       = (String) categoryContent.get(Col.DESCRIPTION) ;
        if (categoryContent.get(Col.SOURCE) != null)          this._source            = (String) categoryContent.get(Col.SOURCE) ;

    }


    /// Getter
    public void getById(int id) {
        this._id = id;
        if(!this.db_getDataAndFillMeById()) {

            this._id = null;

        }
    }

    public String getCategoryName() {
        return _name;
    }

    public String getCategoryColor() {
        return _color;
    }









    /************************************************************************************************************
     *            DATABASE  CreateTable | as OneTask:( AddRow | GetRow | EditRow |  DeleteRow )
     * **********************************************************************************************************/
    public class Col {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String COLOR = "color";
        public static final String DESCRIPTION = "description";
        public static final String SOURCE = "source";

    }


    public static String createDbTableString() {
        String SQL_CREATE_ENTRIES = "";
        SQL_CREATE_ENTRIES = "CREATE TABLE if not exists \"" + DbConnections.TABLE_CATEGORIES + "\"      (" +
                Col.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Col.NAME + " TEXT, " +
                Col.COLOR + " TEXT, " +
                Col.DESCRIPTION + " TEXT, " +
                Col.SOURCE + " TEXT        );";

        return SQL_CREATE_ENTRIES;
    }

    public Long db_saveMe(){

        DbConnections dbConnections = new DbConnections(mContext);
        _CategoryContent.putNull(Col.ID);
        long newRowId = dbConnections.insertData(DbConnections.TABLE_CATEGORIES, _CategoryContent);

        return newRowId;

    }
    public boolean db_getDataAndFillMeById(){


        DbConnections dbConnections = new DbConnections(mContext);
        ContentValues row = dbConnections.getFirstRow(DbConnections.TABLE_CATEGORIES, "id = "+_id);

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
        return   dbConnections.updateRow(DbConnections.TABLE_CATEGORIES,"id = "+_id,_CategoryContent);
    }
    public void     db_deleteMe(){
        //Todo:Delete
    }


}