package com.pet.userManage.entity;

public class UserInfo {
//    private String id;
    private String name;
    private String gender;
    private String phoneNumber;
    private String email;
    private String profile;
    private String avatar;
    private String credits;


    public UserInfo(String name, String gender, String phoneNumber, String email, String profile, String avatar,String credits) {
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.profile = profile;
        this.avatar = avatar;
        this.credits=credits;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getProfile() {
        return profile;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

//    public String getId() {
//        return id;
//    }
}
