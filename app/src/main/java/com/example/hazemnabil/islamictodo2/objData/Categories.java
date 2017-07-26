package com.example.hazemnabil.islamictodo2.objData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.SparseArray;

import com.example.hazemnabil.islamictodo2.DbConnections;

import java.text.ParseException;

/**
 * Created by hazem.nabil on 5/18/2017.
 */

public class Categories {

    //public Category[] mCategories;
    private SparseArray<Category> mCategoriesMap;
    private Context mContext;

    public Categories(Context context) {
        this.mContext = context;

    }

    public SparseArray<Category> getCategories(){
        db_getDataAndFillMe();

        return  mCategoriesMap;
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

    private void db_getDataAndFillMe() {

        //String where = "datetime(sdate) =    datetime('2017-04-06')" ;
        DbConnections db = new DbConnections(mContext);
        Cursor cursor = db.getListOfRows(DbConnections.TABLE_CATEGORIES,null);

        if (cursor.getCount() >0) {
            this.mCategoriesMap = new SparseArray<Category>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Category Category1 = new Category(this.mContext);
                int id = Category1.fillThisTask(cursor);
                this.mCategoriesMap.put(id,Category1);

                cursor.moveToNext();

            }
            cursor.close();
            db.close();
        }





    }



















     public class Category {
        private Context mContext;
        public ContentValues _CategoryContent;

        public Integer _id;
        public String _name;
        public String _color;
        public String _description;
        public String _source;

        public Category(Context context) {
            this.mContext = context;
            _CategoryContent = new ContentValues();
        }

        public Category(Context context, ContentValues categoryContent) throws ParseException {
            this.mContext = context;
            _CategoryContent = new ContentValues();
            this.fillThisTask(categoryContent);

        }


        public int fillThisTask(Cursor categoryCursor)  {
            ContentValues categoryContent = new ContentValues();
            DatabaseUtils.cursorRowToContentValues(categoryCursor, categoryContent);
            fillThisTask(categoryContent);
            return this._id;
        }


        public void fillThisTask(ContentValues categoryContent) {

            if (categoryContent.get(Col.ID) != null)
                this._id = Integer.parseInt((String) categoryContent.get(Col.ID));
            if (categoryContent.get(Col.NAME) != null)
                this._name = (String) categoryContent.get(Col.NAME);
            if (categoryContent.get(Col.COLOR) != null)
                this._color = (String) categoryContent.get(Col.COLOR);
            if (categoryContent.get(Col.DESCRIPTION) != null)
                this._description = (String) categoryContent.get(Col.DESCRIPTION);
            if (categoryContent.get(Col.SOURCE) != null)
                this._source = (String) categoryContent.get(Col.SOURCE);

        }


        /// Getter
        public void getById(int id) {
            this._id = id;
            if (!this.db_getDataAndFillMeById()) {

                this._id = null;

            }
        }

        public String getCategoryName() {
            return _name;
        }

        public String getCategoryColor() {
            return _color;
        }




        public Long db_saveMe() {

            DbConnections dbConnections = new DbConnections(mContext);
            _CategoryContent.putNull(Col.ID);
            long newRowId = dbConnections.insertData(DbConnections.TABLE_CATEGORIES, _CategoryContent);

            return newRowId;

        }

        public boolean db_getDataAndFillMeById() {


            DbConnections dbConnections = new DbConnections(mContext);
            ContentValues row = dbConnections.getFirstRow(DbConnections.TABLE_CATEGORIES, "id = " + _id);
            fillThisTask(row);

            return true;

        }

        public int db_updateMe() {
            DbConnections dbConnections = new DbConnections(mContext);
            return dbConnections.updateRow(DbConnections.TABLE_CATEGORIES, "id = " + _id, _CategoryContent);
        }

        public void db_deleteMe() {
            //Todo:Delete
        }

    }
}