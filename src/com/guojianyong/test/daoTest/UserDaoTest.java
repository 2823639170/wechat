package com.guojianyong.test.daoTest;

import com.guojianyong.dao.UserDao;
import com.guojianyong.dao.impl.simpleMBatis.session.SqlSession;
import com.guojianyong.model.User;
import org.junit.Test;

import java.math.BigInteger;

public class UserDaoTest {

    UserDao userDao = SqlSession.getMapper(UserDao.class);

    @Test
    public void getUserById() {

//            User  user= userDao.getUserById(1);
//            System.out.println(user);
        User user1 = new User("123456", "123456666", "123456");
        BigInteger i = BigInteger.valueOf(1);
        user1.setId(i);
        userDao.delete(user1);
    }

    @Test
    public void getUserByEmail() {
    }

    @Test
    public void getUserByWechatId() {
    }

    @Test
    public void listByName() {
    }

    @Test
    public void listLikeName() {
    }


}
