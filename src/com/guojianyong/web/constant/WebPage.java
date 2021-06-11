package com.guojianyong.web.constant;

/**
 * 界面的地址常量
 */
public enum WebPage {


    /**
     * 注册界面
     */
    REGISTER_JSP,
    /**
     * 错误页面
     */
    ERROR_JSP,
     /**
     * 主页面
     */
    INDEX_JSP,

    /**
     * 登陆界面
     */
    LOGIN_JSP;

    @Override
    public String toString() {
        return "/pages/"+super.toString().toLowerCase().replaceAll("_", ".");
    }
}