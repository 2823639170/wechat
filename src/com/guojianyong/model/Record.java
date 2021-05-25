
package com.guojianyong.model;


import java.math.BigInteger;

public class Record  extends BaseEntity {
    private BigInteger userId;
    private BigInteger messageId;

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getMessageId() {
        return messageId;
    }

    public void setMessageId(BigInteger messageId) {
        this.messageId = messageId;
    }


}
