package com.holoc284_v001.tracuuvanban.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.holoc284_v001.tracuuvanban.model.User;
import com.holoc284_v001.tracuuvanban.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SQLiteQueryUser {

    private static SQLiteQueryUser INSTANCE;
    private ConnectSQLite connectSQLite;

    private SQLiteQueryUser(Context context) {
        connectSQLite = new ConnectSQLite(context);
    }

    public static SQLiteQueryUser getINSTANCE(Context context) {
        if(INSTANCE == null){
            return new SQLiteQueryUser(context);
        }else {
            return INSTANCE;
        }
    }

    public List<User> getAllUser(){
        List<User> users = new ArrayList<>();

        String[] column = {Utils.COLUMN_USER_ID, Utils.COLUMN_USER_SCREEN_NAME, Utils.COLUMN_PASSWORD, Utils.COLUMN_NAME, Utils.COLUMN_EMAIL, Utils.COLUMN_STATUS};
        SQLiteDatabase database = connectSQLite.getReadableDatabase();
        Cursor cursor = database.query(Utils.TABLE_NAME, column, null, null, null, null, null);

        if(cursor!=null && cursor.getCount()>0){
            while(cursor.moveToNext()){
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COLUMN_USER_ID));
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_USER_SCREEN_NAME));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_PASSWORD));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_NAME));
                String email  = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_EMAIL));
//                String role = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_ROLE));
//                String organization  = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_ORGANIZATION));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_STATUS));
                if(status != null)
                    Log.e("status", status);
                users.add(new User(userId, userName, password, name, email, status));
            }
        }
        if(cursor==null) cursor.close();
        database.close();

        return users;
    }

    public User getUser(String userNameIP, String passwordIP){
        User user = null;
        String[] column = {Utils.COLUMN_USER_ID, Utils.COLUMN_USER_SCREEN_NAME, Utils.COLUMN_PASSWORD, Utils.COLUMN_NAME, Utils.COLUMN_EMAIL, Utils.COLUMN_STATUS};
        SQLiteDatabase database = connectSQLite.getReadableDatabase();
        String selection = Utils.COLUMN_USER_SCREEN_NAME+" = ? AND "+ Utils.COLUMN_PASSWORD+" = ?";
        String[] selections = {userNameIP, passwordIP};

        Cursor cursor = database.query(Utils.TABLE_NAME, column, selection, selections, null, null, null);
        if(cursor!=null && cursor.getCount()>0){
            while (cursor.moveToNext()){
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COLUMN_USER_ID));
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_USER_SCREEN_NAME));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_PASSWORD));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_NAME));
                String email  = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_EMAIL));
//                String role = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_ROLE));
//                String organization  = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_ORGANIZATION));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_STATUS));

                user = new User(userId, userName, password, name, email, status);
            }
        }

        if(cursor==null) cursor.close();
        database.close();

        return user;
    }

    public List<User> getUserID(String userID){
        List<User> users = new ArrayList<>();

        String[] column = {Utils.COLUMN_USER_ID, Utils.COLUMN_USER_SCREEN_NAME, Utils.COLUMN_PASSWORD, Utils.COLUMN_NAME, Utils.COLUMN_EMAIL, Utils.COLUMN_STATUS};
        SQLiteDatabase database = connectSQLite.getReadableDatabase();
        String selection = Utils.COLUMN_USER_SCREEN_NAME+" = ?";
        String[] selections = {userID};
        Cursor cursor = database.query(Utils.TABLE_NAME, column, selection, selections, null, null, null);

        if(cursor!=null && cursor.getCount()>0){
            while(cursor.moveToNext()){
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COLUMN_USER_ID));
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_USER_SCREEN_NAME));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_PASSWORD));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_NAME));
                String email  = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_EMAIL));
//                String role = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_ROLE));
//                String organization  = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_ORGANIZATION));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_STATUS));
                if(status != null)
                    Log.e("status", status);
                users.add(new User(userId, userName, password, name, email, status));
            }
        }
        if(cursor==null) cursor.close();
        database.close();

        return users;

    }



    public void insert(User user){
        SQLiteDatabase database = connectSQLite.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Utils.COLUMN_USER_ID, user.getUserId());
        values.put(Utils.COLUMN_USER_SCREEN_NAME, user.getUserName());
        values.put(Utils.COLUMN_PASSWORD, user.getPassword());
        values.put(Utils.COLUMN_NAME, user.getName());
        values.put(Utils.COLUMN_EMAIL, user.getEmail());
        values.put(Utils.COLUMN_STATUS, "");

        database.insert(Utils.TABLE_NAME, null, values);
        database.close();
    }

    public void update(User user){
        SQLiteDatabase database = connectSQLite.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Utils.COLUMN_STATUS, Utils.STATUS_LOGIN);

        String selection = Utils.COLUMN_USER_SCREEN_NAME+" = ?";
        String[] selections = {user.getUserName()};

        database.update(Utils.TABLE_NAME, values, selection, selections);

        database.close();
    }

    public void delete(){
        SQLiteDatabase database = connectSQLite.getWritableDatabase();

        database.delete(Utils.TABLE_NAME, null, null);
        database.close();
    }
}
