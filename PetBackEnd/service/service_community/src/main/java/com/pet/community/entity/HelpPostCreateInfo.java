package com.pet.community.entity;

public class HelpPostCreateInfo {
    //帖子
    public String title;
    //前一百字截断
    public String content;
    //图片列表
    public String[] photos;
    //丢失时间
    public String lost_time;
    //丢失地点
    public String lost_place;
    //所在城市
    public String city;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public String getLost_time() {
        return lost_time;
    }

    public void setLost_time(String lost_time) {
        this.lost_time = lost_time;
    }

    public String getLost_place() {
        return lost_place;
    }

    public void setLost_place(String lost_place) {
        this.lost_place = lost_place;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
