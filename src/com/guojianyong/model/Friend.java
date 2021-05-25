package com.guojianyong.model;


import java.math.BigInteger;


public class Friend extends BaseEntity {
    private BigInteger userId;
    private BigInteger friendId;
    private BigInteger chatId;
    private String photo;
    private BigInteger groupId;
    private String alias;
    private String description;

    public BigInteger getChatId() {
        return chatId;
    }

    public void setChatId(BigInteger chatId) {
        this.chatId = chatId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getFriendId() {
        return friendId;
    }

    public void setFriendId(BigInteger friendId) {
        this.friendId = friendId;
    }

    public BigInteger getGroupId() {
        return groupId;
    }

    public void setGroupId(BigInteger groupId) {
        this.groupId = groupId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
