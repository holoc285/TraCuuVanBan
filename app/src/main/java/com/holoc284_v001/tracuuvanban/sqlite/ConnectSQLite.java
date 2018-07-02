package com.holoc284_v001.tracuuvanban.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.holoc284_v001.tracuuvanban.utils.Utils;


public class ConnectSQLite extends SQLiteOpenHelper{

    private static final String DB_NAME = "USER";

    private static final int version = 1;

    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS "+ Utils.TABLE_NAME+"("
            + Utils.COLUMN_USER_ID+" text,"
            + Utils.COLUMN_USER_SCREEN_NAME+" text,"
            + Utils.COLUMN_PASSWORD+" text,"
            + Utils.COLUMN_NAME+" text,"
            + Utils.COLUMN_EMAIL+" text,"
            + Utils.COLUMN_STATUS+" text)";

    public ConnectSQLite(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("Drop table user");
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
