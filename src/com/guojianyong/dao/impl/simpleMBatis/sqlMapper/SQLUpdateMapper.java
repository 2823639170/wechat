package com.guojianyong.dao.impl.simpleMBatis.sqlMapper;

import com.guojianyong.exception.DaoException;

import java.lang.reflect.InvocationTargetException;

public class SQLUpdateMapper implements SQLMapper{


    private  Object obj;
    private String table;

    private static SQLUpdateMapper sqlUpdateMapper;

    private SQLUpdateMapper(Object obj, String table){
        this.obj = obj;
        this.table = table;
    }


    public static SQLUpdateMapper getInstance(Object obj, String table){
        sqlUpdateMapper = new SQLUpdateMapper(obj , table);
        return sqlUpdateMapper;
    }

    @Override
    public String doMap(Object... params) throws DaoException {
        /**
         * updateRunner会传入一个属性值LinkedList
         */
        Object[] fieldNames = params;
        /**
         * 根据属性名生成预编译sql更新语句
         */
        Object id = null;
        StringBuilder sql = new StringBuilder("update " + table + " set ");
        for (Object name : fieldNames) {
            sql.append(name + " = ?,");
        }
        sql.setCharAt(sql.length() - 1, ' ');
        try {
            for (Class clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    id = clazz.getDeclaredMethod("getId").invoke(obj);
                } catch (NoSuchMethodException e) {
                    /**
                     * 此处不能终止循环
                     */
                }
            }
            sql.append(" where id = \"" + id + "\"");
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new DaoException("反射执行getId方法异常：无法执行getId方法", e);
        }
        return sql.toString();
    }
}
