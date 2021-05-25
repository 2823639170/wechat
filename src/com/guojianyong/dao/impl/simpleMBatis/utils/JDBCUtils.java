package com.guojianyong.dao.impl.simpleMBatis.utils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class JDBCUtils {
    /**
     * 通用的更新方法，考虑上了事务
     * @param
     * @param sql
     * @param args
     * @return
     */
    public static int update( String sql , Object ...args)  {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = BaseJDBCUtils.getConnection();

            ps = conn.prepareStatement(sql);

            for(int i = 0 ; i<args.length ; i++) {
                ps.setObject(i+1, args[i]);
            }
            System.out.println(sql);
            return ps.executeUpdate();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }	finally{
            BaseJDBCUtils.closeResource(null, ps);
        }
        return -1;
    }


    /**
     * 通用的查询单个数据的操作，考虑上了事务
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public static <T>  T queryForOne(Class<T> clazz , String sql , Object ...args)  {
        Connection conn  = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = BaseJDBCUtils.getConnection();

            ps = conn.prepareStatement(sql);

            for(int i = 0 ; i<args.length ; i++) {

                ps.setObject(i+1, args[i]);

            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while(rs.next()) {

                T t = clazz.newInstance();

                for(int i = 0 ; i < columnCount ; i++) {

                    String columnLabel = StingUtils.getName(rsmd.getColumnLabel(i+1));
                    String setName = StingUtils.getSetterName(columnLabel);
                    Method[] method = clazz.getMethods();
                    Object value = rs.getObject(i+1);
                    for(Method method1 : method){
                        if(method1.getName().equalsIgnoreCase(setName)){
                            method1.invoke(t , value);
                        }
                    }
                }
                return t;
            }
        } catch  (Exception e) {
            e.printStackTrace();
        }finally {

            BaseJDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }



    /**
     * 通用的查询多多个数据，考虑上事务
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> queryForList(Class<T> clazz , String sql , Object ...args)  {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = BaseJDBCUtils.getConnection();

            ps = conn.prepareStatement(sql);

            for(int i = 0 ; i<args.length ; i++) {

                ps.setObject(i+1, args[i]);

            }

            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            ArrayList<T> list = new ArrayList<T>();
            while(rs.next()) {

                T t = clazz.newInstance();

                for(int i = 0 ; i < columnCount ; i++) {

                    String columnLabel = StingUtils.getName(rsmd.getColumnLabel(i+1));
                    String setName = StingUtils.getSetterName(columnLabel);
                    Method[] method = clazz.getMethods();
                    Object value = rs.getObject(i+1);
                    for(Method method1 : method){
                       if(method1.getName().equalsIgnoreCase(setName)){
                           method1.invoke(t , value);
                       }
                    }
                }

                list.add(t);
            }
            return list;
        } catch  (Exception e) {
            e.printStackTrace();
        }finally {

            BaseJDBCUtils.closeResource(conn, ps, rs);
        }

        return null;
    }

    /**
     * 查询某个特定的值（一行一列）
     * @param sql
     * @param args
     * @return
     */
    public static Object queryForSingleValue( String sql , Object ...args)  {
        Connection conn  = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = BaseJDBCUtils.getConnection();

            ps = conn.prepareStatement(sql);

            for(int i = 0 ; i<args.length ; i++) {

                ps.setObject(i+1, args[i]);

            }
            rs = ps.executeQuery();

            while(rs.next()) {
                Object value = rs.getObject(1);

                return value;
            }
        } catch  (Exception e) {
            e.printStackTrace();
        }finally {

            BaseJDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }

}
