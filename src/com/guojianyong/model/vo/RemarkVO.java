
package com.guojianyong.model.vo;


import com.alibaba.fastjson.annotation.JSONField;
import com.guojianyong.model.Remark;


public class RemarkVO extends Remark {

    @JSONField(name = "user_name")
    private String userName;
    @JSONField(name = "user_photo")
    private String userPhoto;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }
}
