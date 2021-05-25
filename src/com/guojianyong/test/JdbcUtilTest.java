package com.guojianyong.test;

import com.guojianyong.dao.impl.simpleMBatis.utils.BaseJDBCUtils;
import com.guojianyong.dao.impl.simpleMBatis.utils.JDBCUtils;
import com.guojianyong.model.User;
import org.junit.Test;

import java.sql.Connection;

public class JdbcUtilTest {

    @Test
    public void testJdbcUtil(){

        System.out.println("1");
        Connection connoection = BaseJDBCUtils.getConnection();
            System.out.println(connoection);
            BaseJDBCUtils.closeResource(connoection);
        String  sql = "select * from user where email = ?";
        User user = JDBCUtils.queryForOne(User.class, sql, "1234567");
        System.out.println(user);
    }

}
