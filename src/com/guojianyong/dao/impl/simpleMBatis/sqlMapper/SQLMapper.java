package com.guojianyong.dao.impl.simpleMBatis.sqlMapper;

import com.guojianyong.exception.DaoException;

/**
 * 用于将属性映射成sql预编译语句
 */
public interface SQLMapper {


    /**
     * 用于将一个或多个参数映射成预编译sql语句
     * @param params
     * @return
     * @throws DaoException
     */
    String doMap(Object... params) throws DaoException;

}
