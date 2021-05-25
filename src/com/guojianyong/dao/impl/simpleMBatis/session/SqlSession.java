package com.guojianyong.dao.impl.simpleMBatis.session;

import com.guojianyong.dao.impl.simpleMBatis.handle.MyInvocationHandlerMybatis;

import java.lang.reflect.Proxy;

public class SqlSession {

    public static <T> T getMapper(Class<T> clazz){

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},new MyInvocationHandlerMybatis(clazz));

    }
}
