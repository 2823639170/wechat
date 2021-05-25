package com.guojianyong.service.constants;

public enum ChatType {
    /**
     * 群聊
     */
    GROUP,
    /**
     * 私聊
     */
    FRIEND;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}
