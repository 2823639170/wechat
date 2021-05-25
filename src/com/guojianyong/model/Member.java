

package com.guojianyong.model;

import java.math.BigInteger;

public class Member extends BaseEntity  {
    private BigInteger userId;
    private BigInteger chatId;
    private String groupAlias;
    private String apply;
    private String background;
    private Integer level;

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getChatId() {
        return chatId;
    }

    public void setChatId(BigInteger chatId) {
        this.chatId = chatId;
    }

    public String getGroupAlias() {
        return groupAlias;
    }

    public void setGroupAlias(String groupAlias) {
        this.groupAlias = groupAlias;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
