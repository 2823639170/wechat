package com.guojianyong.model;

import java.math.BigInteger;

/**
 * 动态表的实体类
 */
public class News  extends BaseEntity {
    private BigInteger userId;
    private BigInteger momentId;
    private Boolean loved;
    private Boolean shared;
    private Boolean viewed;
    private Boolean collected;

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getMomentId() {
        return momentId;
    }

    public void setMomentId(BigInteger momentId) {
        this.momentId = momentId;
    }

    public Boolean getLoved() {
        return loved;
    }

    public void setLoved(Boolean loved) {
        this.loved = loved;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    public Boolean getViewed() {
        return viewed;
    }

    public void setViewed(Boolean viewed) {
        this.viewed = viewed;
    }

    public Boolean getCollected() {
        return collected;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }
}
