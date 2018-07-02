package com.holoc284_v001.tracuuvanban.model;

public class User {

    private int userId;
    private String userName;
    private String password ;
    private String name ;
    private String email ;
//    private String role ;
//    private String organization;
    private String status;

    public User(String email) {
        this.email = email;
    }

    public User(int userId, String userName, String password, String name, String email, String status) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.email = email;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

}
