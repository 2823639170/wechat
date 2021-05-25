package com.guojianyong.utils;

import java.util.ArrayList;

public class StringUtils {

    /**
     *
     * @param keyWord
     * @return
     */
    public static String[] toLikeSql(String keyWord) {
        //TODO 添加每个字符的模糊搜索
        ArrayList<String> list = new ArrayList<>();
        String sql = "%" + keyWord + "%";
        list.add(sql);
        String[] strings = new String[list.size()];
        return list.toArray(strings);
    }

    /**
     * 去除输入中的不合法字符，防止标签注入
     * @param str
     * @return
     */
    public static String toLegalText(String str) {
        if (str == null || str.trim().isEmpty()) {
            return "";
        }
        str = toLegalTextIgnoreTag(str);
        String htmlLabel = "<[^>]+>";
        str = str.replaceAll(htmlLabel, "");
        str = str.replace("\"", "");
        str = str.replaceAll("\t|\r|\n", "");
        return str;
    }

    /**
     * 去除输入中的不合法字符，忽略html标签
     * @param str
     * @return
     */
    public static String toLegalTextIgnoreTag(String str) {
        if (str == null || str.trim().isEmpty()) {
            return "";
        }
        String styleLabel = "<style[^>]*?>[\\s\\S]*?<\\/style>";
        String scriptLabel = "<script[^>]*?>[\\s\\S]*?<\\/script>";
        str = str.replaceAll(styleLabel, "");
        str = str.replaceAll(scriptLabel, "");
        return str;
    }

    /**
     * 设置属性
     * @param field
     * @return
     */
    public static String field2SetMethod(String field) {
        StringBuilder method = new StringBuilder("set" + field);
        method.setCharAt(3, (char) (method.charAt(3) - 32));
        return method.toString();
    }



}
