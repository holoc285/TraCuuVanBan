package com.holoc284_v001.tracuuvanban.utils;

import android.util.Log;

public final class Utils {

    public static final String KEY_INTENT_USER_ID = "userID";
    public static final String KEY_INTENT_USER_NAME = "userName";

    public static final String TABLE_NAME = "user";

    public static final String COLUMN_USER_SCREEN_NAME = "screenName";
    public static final String COLUMN_USER_ID = "userId";
    public static final String COLUMN_PASSWORD = "password_";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
//    public static final String COLUMN_ROLE = "role";
//    public static final String COLUMN_ORGANIZATION = "organization";
    public static final String COLUMN_STATUS = "status";

    public static final String STATUS_LOGIN = "login";



    public void test(String json){
        Log.e("json", json.replace(" ", "%20"));
    }
}
