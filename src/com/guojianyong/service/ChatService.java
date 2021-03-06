package com.guojianyong.service;


import com.guojianyong.model.*;

import java.math.BigInteger;


public interface ChatService {

    /**
     * 创建一个聊天,如果是群聊，必须指定外部唯一标识（群号），如果是私聊则自动使用uuid作为唯一标识
     * @param chat
     * @param isGroupChat
     * @return
     */
    ServiceResult createChat(Chat chat, boolean isGroupChat);

    /**
     * 给已给好友关系创建一个聊天关系，并把两者加入到此聊天，并更新好友关系上的聊天关系id
     * @param friend
     * @return
     */
    ServiceResult createFriendChat(Friend friend);

    /**
     * 把用户添加到聊天中
     * @param members
     * @return
     */
    ServiceResult joinChat(Member[] members);

    /**
     * 通过群号将一个用户添加到群聊中
     * @param userId
     * @param number
     * @param apply
     * @return
     */
    ServiceResult joinChatByNumber(BigInteger userId, String number, String apply);

    /**
     * 把成员从聊天中移除，如果该成员是聊天的最后一个成员</br>
     * 就将该聊天一并删除
     * @param members
     * @return
     */
    ServiceResult quitChat(Member members);

    /**
     * 将一个成员从聊天中移除
     * @param memberId
     * @return
     */
    ServiceResult removeFromChat(BigInteger memberId);

    /**
     * 返回一个用户的所有聊天
     * @param user
     * @return
     */
    ServiceResult listChat(User user);

    /**通过用户id和群号获取一个聊天
     *
     * @param number
     * @param userId
     * @return
     */
    ServiceResult getChat(String number, BigInteger userId);

    /**
     * 删除一个聊天
     * @param chat
     */
    void removeChat(Chat chat);

    /**
     * 通过聊天编号查询聊天
     *
     * @param number
     */
    void getChatByNumber(Object number);

    /**
     * 查询一个聊天中所有成员的信息
     * @param chatId
     * @return
     */
    ServiceResult listMember(BigInteger chatId);

    /**
     * 获取打招呼消息
     * @param member
     * @return
     */
    Message getHelloMessage(Member member);

    /**
     * 用来检查一个操作移除群成员的用户是否是群主
     * @param memberId
     * @param userId
     * @return
     */
    boolean isOwner(BigInteger memberId, BigInteger userId);

}
