package com.guojianyong.service;


import com.guojianyong.model.Message;
import com.guojianyong.model.ServiceResult;

import java.math.BigInteger;

/**
 * 负责提供消息保存，聊天记录管理的服务
 */
public interface MessageService {

    /**
     * 将一条消息存入数据库，同时给聊天中的所有成员生成一份聊天记录
     * @param message
     */
    void insertMessage(Message message);

    /**
     * 获取一个用户在一个聊天中的所有消息记录，不包括被删除的消息记录
     * @param userId
     * @param chatId
     * @param page
     * @return
     */
    ServiceResult listAllMessage(Object userId, Object chatId, int page);

    /**
     * 获取一个用户在一个聊天中的所有未读的消息
     * @param userId
     * @param chatId
     * @param page
     * @return
     */
    ServiceResult listUnreadMessage(Object userId, Object chatId, int page);


    /**
     * 获取一个用户的所有未读的消息
     * @param userId
     * @param page
     * @return
     */
    ServiceResult listAllUnreadMessage(Object userId, int page);

    /**
     *  将一条消息从一个用户的消息记录中移除，并不会删除这条消息
     * @param userId
     * @param messageId
     * @return
     */
    ServiceResult removeMessage(BigInteger userId, BigInteger messageId);


    /**
     * 删除一个用户在一个聊天中的所有记录
     * @param userId
     * @param chatId
     * @return
     */
    ServiceResult removeAllMessage(BigInteger userId, BigInteger chatId);

    /**
     * 导出一个用户在一个聊天中的所有消息记录，返回文件名
     * @param userId
     * @param chatId
     * @return
     */
    ServiceResult exportMessage(Object userId, Object chatId);


    /**
     * 撤回一条消息
     * @param userId
     * @param messageId
     * @return
     */
    ServiceResult drawBackMessage(BigInteger userId, BigInteger messageId);


    /**
     * 将一个用户在一个聊天中收到的消息记录设置为已读
     * @param userId
     * @param chatId
     */
    void setAlreadyRead(Object userId, Object chatId);
}
