package com.guojianyong.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;
import java.sql.Timestamp;

public class Reply  extends BaseEntity{


    @JSONField(name = "user_id")
    private BigInteger userId;
    @JSONField(name = "to_uid")
    private BigInteger toUid;
    @JSONField(name = "remark_id")
    private BigInteger remarkId;
    private Timestamp time;
    private String content;
    private long love;
    private long collect;

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }


    public BigInteger getToUid() {
        return toUid;
    }

    public void setToUid(BigInteger toUid) {
        this.toUid = toUid;
    }


    public BigInteger getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(BigInteger remarkId) {
        this.remarkId = remarkId;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public long getLove() {
        return love;
    }

    public void setLove(long love) {
        this.love = love;
    }


    public long getCollect() {
        return collect;
    }

    public void setCollect(long collect) {
        this.collect = collect;
    }




}
