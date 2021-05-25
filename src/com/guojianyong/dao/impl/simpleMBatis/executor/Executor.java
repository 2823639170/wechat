package com.guojianyong.dao.impl.simpleMBatis.executor;

import com.guojianyong.dao.impl.simpleMBatis.annotation.ExtSelect;
import com.guojianyong.dao.impl.simpleMBatis.annotation.ExtUpdate;

import java.lang.reflect.Method;

public interface Executor {


    /**
     * 执行查询操作
     * @param extSelect
     * @param proxy
     * @param method
     * @param args
     * @return
     */
    Object extSelect(ExtSelect extSelect, Object proxy, Method method, Object[] args);


    /**
     * 执行修改操作
     * @param extUpdate
     * @param proxy
     * @param method
     * @param args
     * @return
     */
    Object extUpdate(ExtUpdate extUpdate, Object proxy, Method method, Object[] args);



}
