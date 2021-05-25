package com.guojianyong.dao.impl.simpleMBatis.sqlMapper;

import com.guojianyong.dao.impl.simpleMBatis.contant.SQLType;

public class SQLMapperFactory {

    public static SQLMapper getSQLMapper(SQLType type, Object obj , String table){

        switch (type){
            case INSERT:
                return SQLInsertMapper.getInstance( obj , table);
            case DALETE:
                return SQLDeleteMapper.getInstance( obj , table);
            case UPDATE:
                return SQLUpdateMapper.getInstance( obj , table);
             default: return null;
        }


    }



}
