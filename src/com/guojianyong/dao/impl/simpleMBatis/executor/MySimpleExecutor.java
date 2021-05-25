package com.guojianyong.dao.impl.simpleMBatis.executor;

import com.guojianyong.dao.impl.simpleMBatis.annotation.ExtSelect;
import com.guojianyong.dao.impl.simpleMBatis.annotation.ExtUpdate;
import com.guojianyong.dao.impl.simpleMBatis.config.ResultConfig;
import com.guojianyong.dao.impl.simpleMBatis.contant.ReturnType;
import com.guojianyong.dao.impl.simpleMBatis.contant.SQLType;
import com.guojianyong.dao.impl.simpleMBatis.parse.parseSelectSql;
import com.guojianyong.dao.impl.simpleMBatis.sqlMapper.SQLMapper;
import com.guojianyong.dao.impl.simpleMBatis.sqlMapper.SQLMapperFactory;
import com.guojianyong.dao.impl.simpleMBatis.utils.JDBCUtils;
import com.guojianyong.dao.impl.simpleMBatis.utils.MapperUtils;

import java.lang.reflect.Method;
import java.util.LinkedList;

public class MySimpleExecutor implements Executor {

        @Override
        public Object extUpdate(ExtUpdate extUpdate, Object proxy, Method method, Object[] args) {


            SQLType type = extUpdate.sqlType();
            switch (type) {
                case NOMAL:

                    String sql = extUpdate.value();
                    return JDBCUtils.update(sql, args);

                case UPDATE:
                case DALETE:
                case INSERT:
                    Object obj = args[0];
                    String table = getTableName(obj.getClass());
                    return executeUpdate(obj , type , table );

                default:
                    return 0;
            }

        }

    /**
     * 执行查询操作
     * @param extSelect
     * @param proxy
     * @param method
     * @param args
     * @return
     */
        @Override
        public Object extSelect(ExtSelect extSelect, Object proxy, Method method, Object[] args) {
            ResultConfig resultConfig = parseSelectSql.parseMethodSQl(method);
            ReturnType type = resultConfig.getReturnType();
            Class<?> clazz = resultConfig.getClazz();
            System.out.println(clazz);
            String sql = extSelect.value();
            if(clazz != null){
            switch (type) {
                case BEAN:
                    //这边需要一个Class
                    return JDBCUtils.queryForOne(clazz, sql, args);

                case LIST:

                    return JDBCUtils.queryForList(clazz, sql, args);
                case VALUE:

                    return JDBCUtils.queryForSingleValue(sql, args);
                default:
                    return null;
            }
            }
            return null;
        }

    /**
     * 执行SQL语句为空操作
     * @param obj
     * @param type
     * @param table
     * @return
     */
        public int executeUpdate(Object obj ,SQLType type , String table)  {
            if (obj == null) {
                return 0;
            }
            SQLMapper sqlMapper = SQLMapperFactory.getSQLMapper( type , obj , table);

            LinkedList fieldNames = new LinkedList<>();
            LinkedList fieldValues = new LinkedList<>();
            /**
             * 使用fieldMapper将对象映射成要更新的字段名集合和字段值集合
             */
            MapperUtils.fieldMapper(obj, fieldNames, fieldValues);
            /**
             * 使用sqlMapper将字段名映射成sql语句，使用paramMapper将字段值映射成属性值数组
             */
            String sql = null;
            sql = sqlMapper.doMap(fieldNames.toArray());
            Object[] params = fieldValues.toArray();
            int result = 0;
            result = JDBCUtils.update(sql, params);
            return result;
        }

        public String getTableName(Class clazz){
            return clazz.getSimpleName().toLowerCase();
        }


    }