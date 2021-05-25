package com.guojianyong.dao.impl.simpleMBatis.sqlMapper;

public class SQLInsertMapper implements SQLMapper {

    private  Object obj;
    private String table;

    private static SQLInsertMapper sqlInsertMapper;

    private SQLInsertMapper(Object obj, String table){
        this.obj = obj;
        this.table = table;
    }


    public static SQLInsertMapper getInstance(Object obj, String table){
        sqlInsertMapper = new SQLInsertMapper(obj , table);
        return sqlInsertMapper;
    }

    @Override
    public String doMap(Object... params) {
        /**
         * updateRunner会传入一个属性值集合
         */
        Object[] fieldNames = params;
        /**
         * 根据属性名生成预编译sql插入语句
         */
        StringBuilder sql = new StringBuilder("insert into " + table + " (");
        for (Object name : fieldNames) {
            sql.append(name.toString() + ",");
        }
        sql.setCharAt(sql.length() - 1, ')');
        sql.append(" values (");
        for (int i = 0; i < fieldNames.length; i++) {
            sql.append("?,");
        }
        sql.setCharAt(sql.length() - 1, ')');
        return sql.toString();
    }
}
