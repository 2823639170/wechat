package com.guojianyong.dao.impl.simpleMBatis.utils;

public class StingUtils {


    /**
     * 将一个字符转为mysql的命名方法，例如userId -> user_id
     * @param field
     * @return
     */
    public static String getFieldSqlName(String field) {

        StringBuilder name = new StringBuilder();
        for (int i = 0 ; i<field.length(); i++) {

            char c  = field.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                name.append('_');
            }
            name.append(c);
        }
        return name.toString();
    }
    /**
     * 将一个字符转为mysql的命名方法，例如user_id-> userId
     * @param field
     * @return
     */
    public static String getName(String field) {

        StringBuilder name = new StringBuilder();
        for (int i = 0 ; i<field.length(); i++) {

            char c  = field.charAt(i);
            if (c == '_') {
                c = (char) (field.charAt(++i)+'A'-'a');
            }
            name.append(c);
        }
        return name.toString();
    }

    /**
     * 转化为属性对应的set方法名字
     * @param field
     * @return
     */
    public static String getSetterName(String field){

        String name = getName(field);
        //将第一个字母大写
        byte[] byarray= name.getBytes();
        byarray[0] = (byte) ((char) byarray[0] - 'a' + 'A');
        String s =new String(byarray) ;
        return "set"+s;

    }

}
