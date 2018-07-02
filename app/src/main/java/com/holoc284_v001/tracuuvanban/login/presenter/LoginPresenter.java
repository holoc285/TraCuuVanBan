package com.holoc284_v001.tracuuvanban.login.presenter;

import android.content.Context;
import android.util.Log;

import com.holoc284_v001.tracuuvanban.model.User;
import com.holoc284_v001.tracuuvanban.model.LoginModel;
import com.holoc284_v001.tracuuvanban.login.view.ViewLogin;


public class LoginPresenter implements LoginPresenterItf {
    private Context mContext;
    private ViewLogin mViewLogin;
    private LoginModel mLoginModel;

    public LoginPresenter(Context mContext, ViewLogin mViewLogin) {
        this.mContext = mContext;
        this.mViewLogin = mViewLogin;
        this.mLoginModel = new LoginModel(mContext);
    }

    public LoginPresenter(Context mContext, ViewLogin mViewLogin, String mDomain) {
        this.mContext = mContext;
        this.mViewLogin = mViewLogin;
        this.mLoginModel = new LoginModel(mContext, mDomain);
    }

    @Override
    public void checkLogin(String userName, String password) {
//        mLoginModel.addUserIntoSQLite(userName, password);
        User user = mLoginModel.checkLogIn(userName, password);

        if(user != null){
            mViewLogin.loginSuccess(user.getUserId(), userName);
        }else {
            mViewLogin.loginFail();
        }
    }



    @Override
    public void noLogin() {
        User user = mLoginModel.noLogin();
        if(user != null){
            Log.e("test", "no");
            mViewLogin.loginSuccess(user.getUserId(), user.getUserName());
        }else {
            Log.e("test", "yes");
            mLoginModel.addUserIntoSQLite();
        }
    }



}
