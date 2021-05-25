package com.guojianyong.dao.impl.simpleMBatis.utils;

import com.guojianyong.dao.pool.MyDataSource;
import com.guojianyong.dao.pool.MyDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class BaseJDBCUtils {

    private static MyDataSource dataSource;
//    private static DruidDataSource dataSource;

    static {
        try {
            Properties properties = new Properties();
            //读取jdbc.properties数据
            InputStream inputStream = BaseJDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(inputStream);
//            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
            dataSource = MyDataSourceFactory.creatMyDataSource(properties);
//            System.out.println("11");
//            System.out.println(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        Connection conn = null;

        try{
          conn = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void main(String[] args) {
        Connection connection = getConnection();
        System.out.println(connection);

    }
    /**
     * 关闭数据库连接
     * @param conn
     */
    public static void closeResource(Connection conn){
        if(conn != null){
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResource(Connection conn , Statement ps) {

        closeResource(conn);

        try {
            if(ps != null)
                ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void closeResource(Connection conn , Statement ps , ResultSet rs) {

        closeResource(conn, ps);
        try {
            if(rs != null)
                rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
