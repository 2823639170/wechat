package com.guojianyong.model;

import java.math.BigInteger;
import java.util.Date;


public class User extends BaseEntity{
    private BigInteger id;          //数据库主键id
    private Integer status;        //
    private Date gmtCreate;
    private Date gmtModified;
    private String email;
    private String wechatId;
    private String phone;
    private String password;
    private String gender;
    private String signature;
    private String name;
    private String photo;
    private String chatBackground;
    private String location;
    private String onlineStatus;

    public User() {
    }

    public User( String wechatId, String password ,String email) {
        this.email = email;
        this.wechatId = wechatId;
        this.password = password;
    }

    public User(BigInteger id, Integer status, Date gmtCreate, Date gmtModified, String email, String wechatId, String phone, String password, String gender, String signature, String name, String photo, String chatBackground, String location, String onlineStatus) {
        this.id = id;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.email = email;
        this.wechatId = wechatId;
        this.phone = phone;
        this.password = password;
        this.gender = gender;
        this.signature = signature;
        this.name = name;
        this.photo = photo;
        this.chatBackground = chatBackground;
        this.location = location;
        this.onlineStatus = onlineStatus;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getChatBackground() {
        return chatBackground;
    }

    public void setChatBackground(String chatBackground) {
        this.chatBackground = chatBackground;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", wechatId='" + wechatId + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", signature='" + signature + '\'' +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", chatBackground='" + chatBackground + '\'' +
                ", location='" + location + '\'' +
                ", onlineStatus='" + onlineStatus + '\'' +
                '}';
    }
}

