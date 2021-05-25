package com.guojianyong.dao.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyDataSource extends MyAbstractDataSource{

    //空闲连接池
    private final List<ConnectionProxy>  idleConnection= new ArrayList<ConnectionProxy>();

    //激活连接池
    private final List<ConnectionProxy>  activeConnection= new ArrayList<ConnectionProxy>();

    //监视器对象
    private final Object monitor = new Object();


    /**
     * 覆盖父类方法，获取一个动态代理连接
     * @return
     * @throws SQLException
     */
    @Override
    public Connection getConnection() throws SQLException {

        ConnectionProxy connectionProxy = getConnectionProxy(super.getUsername(),super.getPassword());
        return connectionProxy.getProxyConnection();

    }

    /**
     * 获取连接
     * @param username
     * @param password
     * @return
     */
    private ConnectionProxy getConnectionProxy(String username ,String password) throws SQLException {

        boolean wait = false;
        ConnectionProxy connectionProxy = null;

        while(connectionProxy == null){
            //做一个同步线程
            synchronized (monitor){
                //不为空，直接获取
                if(!idleConnection.isEmpty()){

                    connectionProxy = idleConnection.remove(0);
                }else{
                    //没有空闲连接，需要创建新连接
                    if(activeConnection.size() < super.getPoolMaxActiveConnections()){
                        //小于最大连接数，可以创建，反之亦然
                        connectionProxy = new ConnectionProxy(super.getConnection() , this);
                    }
                    //否则不能创建，需要等待,时间为
                }
            }

            if(!wait){
                wait = true;
            }

            if(connectionProxy == null){

                try {
                    //连接为空，需要等待
                    monitor.wait(super.getPoolTimeTOWait());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //被线程打断，退出循环
                    break;
                }
            }

        }

        if(connectionProxy != null){
            //不为空，说明有连接
            activeConnection.add(connectionProxy);
        }
        return connectionProxy;
    }

    /**
     * 关闭连接，但是不是关闭连接，而是返回连接池
     * @param connectionProxy
     */
    public void closeConnection(ConnectionProxy connectionProxy){
        synchronized (monitor){
            //关闭连接，从激活到休闲
            activeConnection.remove(connectionProxy);

            if(idleConnection.size() < super.getPoolMaxIdleConnections() ){
                idleConnection.add(connectionProxy);
            }
            //通知一下，唤醒是一个线程
            monitor.notify();
        }
    }


}
