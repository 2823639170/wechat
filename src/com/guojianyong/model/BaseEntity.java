package com.guojianyong.model;


import java.math.BigInteger;
import java.util.Date;

/**
 * @program wechat
 * @description 所有数据库记录的父类
 */
public abstract class BaseEntity {

    private BigInteger id;
    private Integer status;
    private Date gmtCreate;
    private Date gmtModified;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
    this.id = id;
}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

}