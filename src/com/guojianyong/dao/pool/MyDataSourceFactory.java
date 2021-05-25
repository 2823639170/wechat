package com.guojianyong.dao.pool;

import java.util.Properties;

public class MyDataSourceFactory {

    public static MyDataSource creatMyDataSource(Properties properties){

        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driver = properties.getProperty("driver");
        String poolMaxActiveConnections = properties.getProperty("MAXACTIVE");
        String poolMaxIdleConnections = properties.getProperty("MAXIDLE");
        MyDataSource myDataSource = new MyDataSource();
        myDataSource.setUsername(username);
        myDataSource.setPassword(password);
        myDataSource.setUrl(url);
        myDataSource.setDriver(driver);
        myDataSource.setPoolMaxActiveConnections(Integer.parseInt(poolMaxActiveConnections));
        myDataSource.setPoolMaxIdleConnections(Integer.parseInt(poolMaxIdleConnections));
        return myDataSource;
    }
}
