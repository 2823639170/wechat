package com.guojianyong.dao;


import com.guojianyong.dao.impl.simpleMBatis.annotation.ExtUpdate;
import com.guojianyong.dao.impl.simpleMBatis.contant.SQLType;

/**
 *
 */
public interface BaseDao {

    /**
     * 把一个对象插入一张表
     * sqlType表明出来类型
     * @param obj
     * @return
     */
    @ExtUpdate(sqlType = SQLType.INSERT)
    int insert(Object obj);

    /**
     * 根据传入的对象，从该表中更新一条记录
     *
     * @param obj
     * @return
     */
    @ExtUpdate(sqlType = SQLType.UPDATE)
    int update(Object obj);


    /***
     *  将一个对象从表中删除
     *  sqlType表明出来类型
     * @param obj
     * @return
     */
    @ExtUpdate(sqlType = SQLType.DALETE)
    int delete(Object obj);
}
