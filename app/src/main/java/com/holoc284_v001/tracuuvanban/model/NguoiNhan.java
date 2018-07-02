package com.holoc284_v001.tracuuvanban.model;

/**
 * Created by holoc on 6/12/2018.
 */

public class NguoiNhan {
    private int userId;
    private String email;
    private boolean isSeleted;

    public NguoiNhan() {
    }

    public NguoiNhan(int userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public NguoiNhan(String email, boolean isSeleted) {
        this.email = email;
        this.isSeleted = isSeleted;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSeleted() {
        return isSeleted;
    }

    public void setSeleted(boolean seleted) {
        isSeleted = seleted;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
