package com.pet.login.entity;


public class LoginInfo {
    public String userID_or_email;

    public String password;

    public String getUserID_or_email() {
        return userID_or_email;
    }

    public void setUserID_or_email(String userID_or_email) {
        this.userID_or_email = userID_or_email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
