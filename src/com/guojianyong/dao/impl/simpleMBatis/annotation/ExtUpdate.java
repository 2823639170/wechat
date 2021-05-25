package com.guojianyong.dao.impl.simpleMBatis.annotation;


import com.guojianyong.dao.impl.simpleMBatis.contant.SQLType;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExtUpdate {
    //如果有SQL语句，就写下来
    String value() default "";

    //针对没有SQL语句的通用增删改类型判断
    SQLType sqlType() default  SQLType.NOMAL;

}
