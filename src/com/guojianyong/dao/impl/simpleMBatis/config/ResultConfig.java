package com.guojianyong.dao.impl.simpleMBatis.config;

import com.guojianyong.dao.impl.simpleMBatis.contant.ReturnType;

/**
 * 保存返回的class和SQL的调用方法
 */
public class ResultConfig {

    private Class  Clazz;
    private ReturnType returnType;

    public ResultConfig() {
    }

    public Class getClazz() {
        return Clazz;
    }

    public void setClazz(Class clazz) {
        Clazz = clazz;
    }

    public ReturnType getReturnType() {
        return returnType;
    }

    public void setReturnType(ReturnType returnType) {
        this.returnType = returnType;
    }
}
