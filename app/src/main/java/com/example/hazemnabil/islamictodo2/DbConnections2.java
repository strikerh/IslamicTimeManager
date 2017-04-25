package com.example.hazemnabil.islamictodo2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hazem.nabil on 4/13/2017.
 */

public class DbConnections2 extends SQLiteOpenHelper {

    public static final int version = 1;
    public static final String dbName = "Tasks.db";

    public DbConnections2(Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

