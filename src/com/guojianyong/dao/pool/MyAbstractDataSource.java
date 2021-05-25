package com.guojianyong.dao.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class MyAbstractDataSource implements MyDataSourceInterface{

    private String url;
    private String driver;
    private String username;
    private String password;

    //最大的正在使用的连接数
    private int poolMaxActiveConnections = 10;
    //最大的休闲的连接数
    private int poolMaxIdleConnections = 10;
    //最大等待时间，单位毫秒
    private int poolTimeTOWait = 30000;

    public int getPoolMaxActiveConnections() {
        return poolMaxActiveConnections;
    }

    public void setPoolMaxActiveConnections(int poolMaxActiveConnections) {
        this.poolMaxActiveConnections = poolMaxActiveConnections;
    }

    public int getPoolMaxIdleConnections() {
        return poolMaxIdleConnections;
    }

    public void setPoolMaxIdleConnections(int poolMaxIdleConnections) {
        this.poolMaxIdleConnections = poolMaxIdleConnections;
    }

    public int getPoolTimeTOWait() {
        return poolTimeTOWait;
    }

    public void setPoolTimeTOWait(int poolTimeTOWait) {
        this.poolTimeTOWait = poolTimeTOWait;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(username ,password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return doGetConnection(username , password);
    }

    /**
     * 获取数据库连接
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    private Connection doGetConnection(String username , String password) throws SQLException {

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return  DriverManager.getConnection(url , username , password);
    }



}
