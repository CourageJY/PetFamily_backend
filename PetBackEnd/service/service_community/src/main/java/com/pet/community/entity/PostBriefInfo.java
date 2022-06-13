package com.pet.community.entity;

import com.pet.models.CommonPost;
import com.pet.models.HelpPost;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PostBriefInfo implements Comparable<PostBriefInfo>{
    //帖子
    public String postId;
    public String status;
    public String title;
    public Instant time;
    //用户
    public String userId;
    public String userName;
    public String avatar;
    //点赞次数
    public int thumbsUp;
    //前一百字截断
    public String content;
    //图片列表
    public List<String> photos=new ArrayList<>();
    //类型
    public String type;

    //城市
    public String city;

    public PostBriefInfo(){}

    public PostBriefInfo(CommonPost commonPost){
        this.title=commonPost.getTitle();
        if(commonPost.getContent().length()<100){
            this.content=commonPost.getContent();
        }
        else{//截断
            this.content=commonPost.getContent().substring(0,100)+"...";
        }
        this.postId=commonPost.getId();
        this.time=commonPost.getTime();
        if(commonPost.getPhotoOne()!=null){
            this.photos.add(commonPost.getPhotoOne());
        }
        if(commonPost.getPhotoTwo()!=null){
            this.photos.add(commonPost.getPhotoTwo());
        }
        if(commonPost.getPhotoThree()!=null){
            this.photos.add(commonPost.getPhotoThree());
        }
        this.avatar=commonPost.getUser().getAvatar();
        this.thumbsUp=commonPost.getThumbsUp();
        this.userName=commonPost.getUser().getName();
        this.userId=commonPost.getUser().getId();
        this.status=commonPost.getStatus();
        this.type="普通";
        time=commonPost.getTime();
    }

    public PostBriefInfo(HelpPost helpPost){
        this.title=helpPost.getTitle();
        if(helpPost.getContent().length()<100){
            this.content=helpPost.getContent();
        }
        else{//截断
            this.content=helpPost.getContent().substring(0,100)+"...";
        }
        this.postId=helpPost.getId();
        this.time=helpPost.getTime();
        if(helpPost.getPhotoOne()!=null){
            this.photos.add(helpPost.getPhotoOne());
        }
        if(helpPost.getPhotoTwo()!=null){
            this.photos.add(helpPost.getPhotoTwo());
        }

        this.avatar=helpPost.getUser().getAvatar();
        this.thumbsUp=helpPost.getThumbUp();
        this.userName=helpPost.getUser().getName();
        this.userId=helpPost.getUser().getId();
        this.status=helpPost.getStatus();
        this.type="求助";
        this.city=helpPost.getCity();
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfile() {
        return avatar;
    }

    public void setProfile(String profile) {
        this.avatar = profile;
    }

    public int getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(int thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    @Override
    public int compareTo(PostBriefInfo postBriefInfo) {
        // 重写Comparable接口的compareTo方法，根据年龄升序排列，降序修改相减顺序即可
        return postBriefInfo.getTime().compareTo(this.time);
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
