

package com.guojianyong.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;
import java.sql.Timestamp;

public class Message  extends BaseEntity {
    @JSONField(name = "sender_id")
    private BigInteger senderId;
    @JSONField(name = "chat_id")
    private BigInteger chatId;
    private String content;
    private String type;
    private Timestamp time;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public BigInteger getSenderId() {
        return senderId;
    }

    public void setSenderId(BigInteger senderId) {
        this.senderId = senderId;
    }

    public BigInteger getChatId() {
        return chatId;
    }

    public void setChatId(BigInteger chatId) {
        this.chatId = chatId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
