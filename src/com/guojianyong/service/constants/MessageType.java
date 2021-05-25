package com.guojianyong.service.constants;

public enum MessageType {
    /**
     * 用户之间的文本消息
     */
    USER,
    /**
     * 图片消息
     */
    IMG,
    /**
     * 文件消息
     */
    FILE,
    /**
     * 加好友通知
     */
    FRIEND;
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    }
