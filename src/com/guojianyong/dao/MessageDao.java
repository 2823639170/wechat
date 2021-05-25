package com.guojianyong.dao;


import com.guojianyong.dao.impl.simpleMBatis.annotation.ExtSelect;
import com.guojianyong.model.Message;

import java.math.BigInteger;
import java.util.List;

/**
 * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
 * @description 用于message表的CRUD
 * @date 2019-05-03 13:06
 */
public interface MessageDao extends BaseDao {


    /**
     * 通过发送id和聊天id查询一个消息
     * @param senderId
     * @param chatId
     * @param time
     * @return
     */
    @ExtSelect(value = "select * from message where sender_id = ? and chat_id = ? and time = ? ")
    Message getMessageBySenderIdAndChatIdAndTime(BigInteger senderId, BigInteger chatId, Object time);


    /**
     * 通过用户id和聊天id并按时间顺序查询用户的所有消息记录
     * @param userId
     * @param chatId
     * @param limit
     * @param offset
     * @return
     */
    @ExtSelect(value = "select m.id, m.sender_id, m.chat_id, m.content , m.type , m.time ,m.status , " +
            "m.gmt_create, m.gmt_modified from message as m , record as r where m.id = r.message_id" +
            " and r.user_id = ? and m.chat_id = ? order by m.time limit ? offset ?  ")
    List<Message> listMessageByUserIdAndChatId(Object userId, Object chatId, int limit, int offset);


    /**
     * 通过用户id和聊天id并按时间顺序查询用户的所有消息记录
     * @param userId
     * @param chatId
     * @param limit
     * @param offset
     * @return
     */
    @ExtSelect(value = "select m.id, m.sender_id, m.chat_id, m.content , m.type , m.time ,m.status , " +
            "m.gmt_create, m.gmt_modified from message as m , record as r where m.id = r.message_id" +
            " and r.user_id = ? and m.chat_id = ? order by m.time desc limit ? offset ?  ")
    List<Message> listMessageByUserIdAndChatIdDesc(Object userId, Object chatId, int limit, int offset);


    /**
     * 通过用户id和聊天id并按时间顺序查询用户的所有消息记录并导出到文件
     * @param userId
     * @param chatId
     * @param fileName
     * @return
     */
    @ExtSelect(value = "select m.id, m.sender_id, m.chat_id, m.content , m.type , m.time ,m.status , " +
            "m.gmt_create, m.gmt_modified from message as m , record as r where m.id = r.message_id" +
            " and r.user_id = ? and m.chat_id = ? order by m.time desc into outfile ? fields terminated by '," +
            "' optionally enclosed by '\"' lines terminated by '\\r\\n'; ")
    int messageToFile(Object userId, Object chatId, Object fileName);

    /**
     * 通过用户id和聊天id查询用户的所有未读消息记录
     *
     * @param userId
     * @param chatId
     * @param limit
     * @param offset
     * @return
     */
    @ExtSelect(value = "select m.id, m.sender_id, m.chat_id, m.content , m.type , m.time ,m.status , " +
            "m.gmt_create, m.gmt_modified from message as m , record as r where m.id = r.message_id" +
            " and r.user_id = ? and m.chat_id = ? and r.status = 0 order by m.time limit ? offset ?  ")
    List<Message> listUnreadMessage(Object userId, Object chatId, int limit, int offset);


    /**
     * 通过用户id查询用户的所有未读消息记录
     * @param userId
     * @param limit
     * @param offset
     * @return
     */
    @ExtSelect(value = "select m.id, m.sender_id, m.chat_id, m.content , m.type , m.time ,m.status , " +
            "m.gmt_create, m.gmt_modified from message as m , record as r where m.id = r.message_id" +
            " and r.user_id = ?  and r.status = 0  order by m.time desc  limit ? offset ? ")
    List<Message> listUnreadMessageByUserId(Object userId, int limit, int offset);

}
