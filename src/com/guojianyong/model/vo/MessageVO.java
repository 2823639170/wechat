
package com.guojianyong.model.vo;


import com.alibaba.fastjson.annotation.JSONField;
import com.guojianyong.model.Message;

/**
 * 用于传输用户发送的消息
 */
public class MessageVO extends Message {
    @JSONField(name = "sender_name")
    private String senderName;
    @JSONField(name = "sender_photo")
    private String senderPhoto;



    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhoto() {
        return senderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        this.senderPhoto = senderPhoto;
    }
}
