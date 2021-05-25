package com.guojianyong.dao;

import com.guojianyong.dao.impl.simpleMBatis.annotation.ExtSelect;
import com.guojianyong.model.User;

import java.util.List;


public interface UserDao extends BaseDao {

    /**
     * 通过id查询一个用户
     * @param id
     * @return
     */
    @ExtSelect(value = "select * from user where id = ? ")
    User getUserById(Object id);


    /**
     * 通过邮箱查询一个用户
     * @param email
     * @return
     */
    @ExtSelect(value = "select * from user where email = ? ")
    User getUserByEmail(String email);


    /**
     * 通过用户名查询一个用户
     * @param wechatId
     * @return
     */
    @ExtSelect(value = "select * from user where wechat_id = ? ")
    User getUserByWechatId(String wechatId);


    @ExtSelect(value = "select * from user where name = ?" )
    List<User> listByName(String name);


    /**
     * 通过关键词模糊查询用户昵称相近的用户
     * @param keyWord
     * @return
     */
    @ExtSelect(value = "select * from user where name like ? " )
    List<User> listLikeName(String keyWord);


}
