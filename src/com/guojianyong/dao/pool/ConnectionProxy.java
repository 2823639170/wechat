package com.guojianyong.dao.pool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

public class ConnectionProxy implements InvocationHandler{

    private Connection realConnection;
    private Connection proxyConnection;
    private MyDataSource myDataSource;

    /**
     * 构造方法
     * @param realConnection
     * @param myDataSource
     */
    public ConnectionProxy(Connection realConnection, MyDataSource myDataSource) {
        //初始化真实连接
        this.realConnection = realConnection;
        //初始化数据源
        this.myDataSource = myDataSource;
        //初始化代理连接
        this.proxyConnection = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(),
                                                        new Class<?>[]{Connection.class},
                                                        this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String methodName = method.getName();
        if("close".equals(methodName)){
            myDataSource.closeConnection(this);
            return null;
        }
        return method.invoke(realConnection ,args);
    }







    public Connection getRealConnection() {
        return realConnection;
    }

    public void setRealConnection(Connection realConnection) {
        this.realConnection = realConnection;
    }

    public Connection getProxyConnection() {
        return proxyConnection;
    }

    public void setProxyConnection(Connection proxyConnection) {
        this.proxyConnection = proxyConnection;
    }

    public MyDataSource getMyDataSource() {
        return myDataSource;
    }

    public void setMyDataSource(MyDataSource myDataSource) {
        this.myDataSource = myDataSource;
    }
}
