package com.guojianyong.service;


import com.guojianyong.model.Friend;
import com.guojianyong.model.ServiceResult;

public interface FriendService {
    /**
     * 添加好友关系
     * @param friend
     * @return
     */
    ServiceResult addFriend(Friend friend);


    /**
     * 返回一个用户的好友列表
     * @param userId
     * @return
     */
    ServiceResult listFriend(Object userId);

    /**
     * 返回一个用户的好友列表
     * @param friend
     * @return
     */
    ServiceResult updateFriend(Friend friend);


    /**
     * 移除好友
     * @param friend
     * @return
     */
    ServiceResult removeFriend(Friend friend);

    /**
     * 判断是否存在一条这样的朋友记录
     * @param friend
     * @return
     */
    boolean isFriend(Friend friend);

    /**
     * 通过用户id和朋友id查询朋友关系
     * @param uid
     * @param friendId
     * @return
     */
    Friend getByUidAndFriendId(Object uid, Object friendId);

}
