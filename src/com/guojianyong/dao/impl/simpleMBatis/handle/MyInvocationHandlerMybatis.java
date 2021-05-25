package com.guojianyong.dao.impl.simpleMBatis.handle;


import com.guojianyong.dao.impl.simpleMBatis.annotation.ExtSelect;
import com.guojianyong.dao.impl.simpleMBatis.annotation.ExtUpdate;
import com.guojianyong.dao.impl.simpleMBatis.executor.Executor;
import com.guojianyong.dao.impl.simpleMBatis.executor.MySimpleExecutor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


/**
 * 使用反射动态代理技术 拦截接口防范
 */
public class MyInvocationHandlerMybatis implements InvocationHandler {
    private Object object;
    private Executor executor = new MySimpleExecutor();
    public MyInvocationHandlerMybatis(Object object) {
        this.object = object;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("使用动态代理技术拦截接口方法开始");

        ExtUpdate extUpdate = method.getDeclaredAnnotation(ExtUpdate.class);
            if (extUpdate != null) {

                    return executor.extUpdate(extUpdate, proxy, method, args);

            }


        ExtSelect extSelect = method.getDeclaredAnnotation(ExtSelect.class);
        if (extSelect != null) {

            return executor.extSelect(extSelect, proxy, method, args);
        }



        return method.invoke(proxy , args);
    }

    //        ExtDelete extDelete = method.getDeclaredAnnotation(ExtDelete.class);
//            if (extDelete != null) {
//                String sql = extDelete.value();
//                if(Contant.NOSQLEXECUYOR.equals(sql)){
//
//                }else{
//                    return executor.extUpdate(extUpdate, proxy, method, args);
//                }
//
//            }
//        ExtInsert extInsert = method.getDeclaredAnnotation(ExtInsert.class);
//            if (extInsert != null) {
//                String sql = extInsert.value();
//                if(Contant.NOSQLEXECUYOR.equals(sql)){
//
//                }else{
//                    return executor.extUpdate(extUpdate, proxy, method, args);
//                }
//
//            }

}



