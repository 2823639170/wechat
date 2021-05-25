package com.guojianyong.dao.impl.simpleMBatis.utils;

import com.guojianyong.exception.DaoException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static com.guojianyong.dao.impl.simpleMBatis.utils.StingUtils.getFieldSqlName;


public class MapperUtils {


    /**
     * 获取一个对象的的全部方法
     * @param obj
     * @return
     */
    public static ArrayList<Method> getAllMethods(Object obj ) {
        return getAllMethods(obj.getClass() );
    }

    /**
     * 获取一个类的全部方法
     * @param clazz
     * @return
     */
    public static ArrayList<Method> getAllMethods(Class clazz ) {
        ArrayList<Method> methods = new ArrayList<>();
        for (Class cla = clazz; cla != Object.class; cla = cla.getSuperclass()) {
            methods.addAll(Arrays.asList(cla.getDeclaredMethods()));
        }
        return methods;
    }

    /**
     * 获取一个对象的全部属性
     * @param obj
     * @return
     */
    public static ArrayList<Field> getAllFields(Object obj) {
        return getAllFields(obj.getClass());
    }

    /**
     * 获取一个类的全部属性
     * @param clazz
     * @return
     */
    public static ArrayList<Field> getAllFields(Class clazz) {
        ArrayList<Field> fields = new ArrayList<>();
        for (Class cla = clazz; cla != Object.class; cla = cla.getSuperclass()) {
            fields.addAll(Arrays.asList(cla.getDeclaredFields()));
        }
        return fields;
    }


    /**
     * 把一个对象映射成为属性名集合和属性值集合
     * @param obj         需要被映射的对象
     * @param fieldNames  将映射的属性名返回在这个集合中
     * @param fieldValues 将映射的属性值返回在这个集合中
     * @throws DaoException
     */
    public static void fieldMapper(Object obj, LinkedList fieldNames, LinkedList fieldValues) throws DaoException {
        if (obj == null) {
            return;
        }

        /**
         * 取出包括父类在内的所有方法和属性
         */
        ArrayList<Method> methods = MapperUtils.getAllMethods(obj);
        ArrayList<Field> fields = MapperUtils.getAllFields(obj);
        for (Field field : fields) {
            /**
             * 取出每个属性的值
             */
            for (Method method : methods) {
                if (method.getName().startsWith("get") && method.getName().substring(3).equalsIgnoreCase(field.getName())) {
                    Object value = null;
                    try {
                        value = method.invoke(obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new DaoException("反射执行get方法异常：" + method.getName(), e);
                    }
                    /**
                     * 只添加不为null值的字段
                     */
                    if (value != null) {
                        fieldValues.add(value);
                        /**
                         * 取出该属性的名称，映射成数据库字段名
                         */
                        fieldNames.add(getFieldSqlName(field.getName()));
                    }
                }
            }
        }
    }

}
