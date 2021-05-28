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

        for (Field f : fields) {
            /**
             * 获取每个属性的String类型参数的构造器
             */
            Constructor cons = null;
            try {
                cons = f.getType().getConstructor(String.class);
            } catch (Exception e) {
                /**
                 * 某些属性确实没有这种构造器，在这里不需要处理这个问题
                 */
                throw new RuntimeException("从请求参数转化成对象时缺少String类型的构造器 ： " +f.getName());

            }
            /**
             * 构造属性
             */
            String[] param = map.get(f.getName());
            if (param != null && param[0] != null) {
                Object value = null;
                try {
                    if (cons != null) {
                        value = cons.newInstance(param[0]);

                    }
                    for (Method m : methods) {

                        if (m.getName().equalsIgnoreCase(StringUtils.field2SetMethod(f.getName()))) {
                            m.invoke(obj, value);
                        }
                    }
                } catch (Exception e) {
                    /**
                     * 某些属性可能由于非法输入而无法初始化，这里无需处理
                     */
                    e.printStackTrace();
                    throw new RuntimeException("从请求参数转化成对象时无法初始化[属性] ： " +f.getName()+ " [属性值]：" + param[0] );
                }
            }
        }
        return obj;
    }





    public static Object jsonToJavaObject(InputStream inputStream, Class targetClass) {
        /**
         * 将输入流中的json数据转化成java对象
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
