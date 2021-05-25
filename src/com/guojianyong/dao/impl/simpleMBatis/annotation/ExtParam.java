package com.guojianyong.dao.impl.simpleMBatis.annotation;


import com.guojianyong.dao.impl.simpleMBatis.contant.Contant;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ExtParam {

    String value() default Contant.JAVABEAN ;


}
