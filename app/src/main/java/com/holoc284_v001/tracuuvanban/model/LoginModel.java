package com.holoc284_v001.tracuuvanban.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.holoc284_v001.tracuuvanban.sqlite.SQLiteQueryUser;
import com.holoc284_v001.tracuuvanban.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LoginModel implements Response.Listener<String>, Response.ErrorListener{

    private Context mContext;
    private String domain;

//    private User user;
//    private String userName;
//    private String password;

//    public User getUser() {
//        return user;
//    }

    public LoginModel(Context mContext) {
        this.mContext = mContext;
    }

    public LoginModel(Context mContext, String domain) {
        this.mContext = mContext;
        this.domain = domain;
    }

    public void addUserIntoSQLite(){


        //domain = domainCH.getString("domainDonVi","");
//        this.userName = userName;
//        this.password = password;
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        //Toast.makeText(mContext, "Hello tao ne" + domain, Toast.LENGTH_SHORT).show();LoginActivity.domainCH.getString("domainDonVi","")

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+domain+"/api/tracuuvanban/danhSachUser", this, this);

        requestQueue.add(stringRequest);
    }


    public User checkLogIn(String userName, String password){

        SQLiteQueryUser sqLiteQueryUser = SQLiteQueryUser.getINSTANCE(mContext);

        User user = sqLiteQueryUser.getUser(userName, password);

        if(user!=null){

            //Log.e("test", user.getUserName()+"-"+user.getPassword());
            sqLiteQueryUser.update(user);
            return user;
        }else return null;
    }

    public User noLogin(){
        SQLiteQueryUser sqLiteQueryUser = SQLiteQueryUser.getINSTANCE(mContext);
        //sqLiteQueryUser.delete();
        List<User> users = sqLiteQueryUser.getAllUser();


        for(int i = 0; i < users.size(); i++){
            User user = users.get(i);
            Log.e("size", user.getStatus());
            if(user.getStatus().equals(Utils.STATUS_LOGIN)){
                return user;
            }
        }
        return null;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("error", error.toString());
    }

    @Override
    public void onResponse(String response) {
        try {

            JSONArray jsonArray = new JSONArray(response);
            if(jsonArray.length()>0){
                SQLiteQueryUser sqLiteQueryUser = SQLiteQueryUser.getINSTANCE(mContext);
                sqLiteQueryUser.delete();
                for(int i= 0; i< jsonArray.length(); i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                    int userID = jsonObject.getInt("userId");
                    String screenName = jsonObject.getString("screenName");
                    String password = jsonObject.getString("password_");
                    String fName = jsonObject.getString("firstName");
                    String mName = jsonObject.getString("middleName");
                    String lName = jsonObject.getString("lastName");
                    String email = jsonObject.getString("emailAddress");

                    String name = String.format("%s%s%s%s%s", fName, " ", mName, " ", lName).trim();

                    sqLiteQueryUser.insert(new User(userID, screenName, password, name, email, null));
                }
                Log.e("size", sqLiteQueryUser.getAllUser().size()+"");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
