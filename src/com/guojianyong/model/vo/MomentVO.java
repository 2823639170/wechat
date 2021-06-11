package com.guojianyong.model.vo;


import com.alibaba.fastjson.annotation.JSONField;
import com.guojianyong.model.Moment;

/**
 * 朋友圈的视图层对象
 */
public class MomentVO extends Moment {

    @JSONField(name = "user_name")
    private String userName;
    private Boolean loved;
    private Boolean shared;
    private Boolean viewed;
    private Boolean collected;
    @JSONField(name = "user_photo")
    private String userPhoto;

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean getLoved() {
        return loved;
    }

    public void setLoved(boolean loved) {
        this.loved = loved;
    }

    public boolean getShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public boolean getViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public boolean getCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}
