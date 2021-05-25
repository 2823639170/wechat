package com.guojianyong.dao;

import com.guojianyong.dao.impl.simpleMBatis.annotation.ExtSelect;
import com.guojianyong.model.Friend;

import java.util.List;


public interface FriendDao extends BaseDao {


    /**
     * 通过用户id和朋友id获取一条好友记录
     * @param userId
     * @param friendId
     * @return
     */
    @ExtSelect(value = "select * from friend where user_id = ? and friend_id = ? ")
    Friend getFriendByUIDAndFriendId(Object userId, Object friendId);


    /**
     * 通过用户id查询好友列表
     * @param userId
     * @return
     */
    @ExtSelect(value = "select f.id,f.user_id,f.friend_id,f.chat_id,f.group_id,f.alias,f.description,u.photo as photo,f.status,f.gmt_create,f.gmt_modified " +
            "from friend as f,user as u where f.user_id = ? and u.id = f.friend_id ")
    List listByUserId(Object userId);


    /**
     * 通过朋友关系id查询一个朋友关系
     * @param id
     * @return
     */
    @ExtSelect(value = "select * from friend where id = ? ")
    Friend getFriendById(Object id);

}
