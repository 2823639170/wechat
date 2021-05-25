package com.guojianyong.dao.impl.simpleMBatis.annotation;


import com.guojianyong.dao.impl.simpleMBatis.contant.Contant;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExtInsert {
    String value() default Contant.NOSQLEXECUYOR;
}
