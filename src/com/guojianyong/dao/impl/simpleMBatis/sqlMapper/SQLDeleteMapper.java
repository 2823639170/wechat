package com.guojianyong.dao.impl.simpleMBatis.sqlMapper;

public class SQLDeleteMapper implements SQLMapper{

    private  Object obj;
    private String table;

    private static SQLDeleteMapper sqlDeleteMapper;

    private SQLDeleteMapper(Object obj, String table){
            this.obj = obj;
        this.table = table;
    }


    public static SQLDeleteMapper getInstance(Object obj, String table){
        sqlDeleteMapper = new SQLDeleteMapper(obj , table);
        return sqlDeleteMapper;
    }

    @Override
    public String doMap(Object... params) {
        /**
         * updateRunner会传入一个属性值LinkedList
         */
        Object[] fieldNames = params;
        /**
         * 根据属性名生成预编译sql更新语句
         */
        StringBuilder sql = new StringBuilder("delete from " + table);
        sql.append(whereMapper(params, "and", "="));
        return sql.toString();
    }

    public String whereMapper(Object[] whereFields, String conj, String condition) {

        StringBuilder sql = new StringBuilder();
        if (whereFields.length == 0) {
            return sql.toString();
        }
        sql.append(" where ");
        for (int i = 0; i < whereFields.length; i++) {
            sql.append(whereFields[i] + " " + condition + "?  " + conj + " ");

        }
        sql.delete(sql.length() - 4, sql.length());
        return sql.toString();
    }
}
