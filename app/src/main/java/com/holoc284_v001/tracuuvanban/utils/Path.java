package com.holoc284_v001.tracuuvanban.utils;

import com.holoc284_v001.tracuuvanban.login.view.LoginActivity;

public class Path {
    public static String LOCALHOST = LoginActivity.domainCH.getString("domainDonVi","");
    public static String domainCauHinh = "";
    public static String PATH_GET_ALL_USER = "http://"+LoginActivity.domainCH.getString("domainDonVi","")+"/api/tracuuvanban/danhSachUser";
}
