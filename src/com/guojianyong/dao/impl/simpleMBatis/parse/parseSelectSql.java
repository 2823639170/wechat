package com.guojianyong.dao.impl.simpleMBatis.parse;

import com.guojianyong.dao.impl.simpleMBatis.config.ResultConfig;
import com.guojianyong.dao.impl.simpleMBatis.contant.ReturnType;
import com.guojianyong.model.BaseEntity;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class parseSelectSql {

        public static ResultConfig parseMethodSQl(Method method) {

            ResultConfig resultConfig = new ResultConfig();
            //返回一个Type对象，表示由该方法对象表示的方法的正式返回类型。
            //比如public List<User> getAll();那么返回的是List<User>
            Type genericReturnType = method.getGenericReturnType();
            //获取实际返回的参数名
            String returnTypeName = genericReturnType.getTypeName();
            //判断是否是参数化类型
            if (genericReturnType instanceof ParameterizedType) {
                //如果是参数化类型,则强转
                ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
                //获取实际参数类型数组，比如List<User>，则获取的是数组[User]，Map<User,String> 则获取的是数组[User,String]
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                Type type = actualTypeArguments[0];
                    //强转
                    Class<?> actualTypeArgument = (Class<?>) type;
                    //设置type
                     resultConfig.setReturnType(ReturnType.LIST);
                     resultConfig.setClazz(actualTypeArgument);
                    return  resultConfig;

            } else {
                //不是参数化类型,直接获取返回值类型
                Class<?> classType = method.getReturnType();
                if(BaseEntity.class == classType.getSuperclass() ){
                     resultConfig.setReturnType(ReturnType.BEAN);
                }else {
                    resultConfig.setReturnType(ReturnType.VALUE);
                }
                    resultConfig.setClazz(classType);
                    return resultConfig;
            }
        }

}
