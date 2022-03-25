package com.pet.community.entity;

import com.pet.models.CommonPost;
import com.pet.models.HelpPost;

public class HelpPostDetail extends PostBriefInfo{
    //求助信息
    public String lost_time;

    public String lost_place;

    public String getLost_time() {
        return lost_time;
    }

    public HelpPostDetail(HelpPost helpPost){
        this.title=helpPost.getTitle();
        this.content=helpPost.getContent();
        this.postId=helpPost.getId();
        this.time=helpPost.getTime();
        if(helpPost.getPhotoOne()!=null){
            this.photos.add(helpPost.getPhotoOne());
        }
        if(helpPost.getPhotoTwo()!=null){
            this.photos.add(helpPost.getPhotoTwo());
        }
        this.avatar=helpPost.getUser().getAvatar();
        this.type="求助";
        this.thumbsUp=helpPost.getThumbUp();
        this.userName=helpPost.getUser().getName();
        this.userId=helpPost.getUser().getId();
        this.status=helpPost.getStatus();
        this.lost_place=helpPost.getLostPlace();
        this.lost_time=helpPost.getLostTime();
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
}
