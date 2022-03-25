package com.pet.community.entity;

public class CommonPostCreateInfo {
    //帖子
    public String title;
    //前一百字截断
    public String content;
    //图片列表
    public String[] photos;

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
}
