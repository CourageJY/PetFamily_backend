package com.pet.community.entity;

import com.pet.models.CommonPost;

public class CommonPostDetail extends PostBriefInfo{

    public CommonPostDetail(){}

    public CommonPostDetail(CommonPost commonPost){
        this.title=commonPost.getTitle();
        this.content=commonPost.getContent();
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
        this.type="普通";
        this.thumbsUp=commonPost.getThumbsUp();
        this.userName=commonPost.getUser().getName();
        this.userId=commonPost.getUser().getId();
        this.status=commonPost.getStatus();
    }
}
