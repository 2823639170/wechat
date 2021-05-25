package com.guojianyong.test.serviceTest;

import com.guojianyong.model.ServiceResult;
import com.guojianyong.model.User;
import com.guojianyong.service.UserService;
import com.guojianyong.service.factory.ServiceProxyFactory;
import com.guojianyong.service.impl.UserServiceImpl;
import com.guojianyong.utils.md5Utils;
import org.junit.Test;


public class UserServiceTest {

  private UserService userService = (UserService) new ServiceProxyFactory().getProxyInstance(new UserServiceImpl());

    @Test
    public void checkRegister() {

        User user1 = new User("1234567", "1234567", "1234567");
        ServiceResult serviceResult = userService.checkPassword(user1);
        System.out.println(md5Utils.getDigest("1234567"));
        System.out.println(serviceResult.getStatus());
        System.out.println(serviceResult.getMessage());
        System.out.println(serviceResult.getData().toString());


    }

    @Test
    public void insertUser() {


    }

    @Test
    public void checkPassword() {
    }

    @Test
    public void checkWechatId() {
    }

    @Test
    public void getUser() {
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void updatePwd() {
    }

    @Test
    public void listUserLikeName() {
    }
}
