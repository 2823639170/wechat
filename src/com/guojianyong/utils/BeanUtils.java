package com.guojianyong.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

import static com.guojianyong.dao.impl.simpleMBatis.utils.MapperUtils.getAllFields;
import static com.guojianyong.dao.impl.simpleMBatis.utils.MapperUtils.getAllMethods;
import static com.guojianyong.utils.StringUtils.toLegalText;

public class BeanUtils {


    /**
     * 负责将request中的parameterMap映射成为一个对象
     * @param map
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T populate(Map<String, String[]> map, Class<T> cls) {

        ArrayList<Method> methods = null;
        ArrayList<Field> fields = null;
        T obj = null;
        try {
            obj = cls.newInstance();
            methods = getAllMethods(obj);
            fields = getAllFields(obj);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("无法实例化类：" + cls.getName());
        }

        for(Field fld:fields) {
            //获取该fld对象所代表的属性名
            String fldName = fld.getName();
            //根据属性名，到map中去读取数据，只有数据非空才需要给该属性设置值
            Object value = map.get(fldName);
            if (value == null) {//如果map中不存在对应的属性数据，我们在这里给出提示信息
                System.out.println(fldName + "的数据为空");
            } else {
                //如果map中存在对应的属性数据，则由属性名得出它的setter方法的名字
                String mothodName = "set" + fldName.substring(0, 1).toUpperCase() + fldName.substring(1);
                for (Method method : methods) {

                    if (method.getName().equalsIgnoreCase(mothodName)) {

                        try {
                            method.invoke(obj, value);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException(obj + "与" + value + "参数类型不匹配");
                        }
                    }
                }
            }
        }
        return obj;
    }





    public static Object jsonToJavaObject(InputStream inputStream, Class targetClass) {
        /**
         * 将输入流中的json数据转化成java对象
         * @name jsonToJavaObject
         * @param inputStream 输入json数据的输入流
         * @notice none
         * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
         * @date 2019/5/5
         */
        JSONObject jsonObject = null;
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder builder = new StringBuilder();
            String jsonData;
            while ((jsonData = streamReader.readLine()) != null) {
                builder.append(jsonData);
            }
            jsonObject = JSONObject.parseObject(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.toJavaObject(jsonObject, targetClass);
    }

}
