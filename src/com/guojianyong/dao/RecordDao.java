package com.guojianyong.dao;

import com.guojianyong.dao.impl.simpleMBatis.annotation.ExtUpdate;

public interface RecordDao extends BaseDao {


    /**
     * 一次性更新一个用户在一个聊天中所有记录的状态
     *
     * @param status 记录状态
     * @param userId 用户id
     * @param chatId 聊天id
     * @name updateStatusInChat
     * @notice none
     * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
     * @date 2019/5/7
     */
    @ExtUpdate("update record as r inner join message as m set r.status = ? where r.user_id = ? and r.message_id = m.id and m.chat_id = ?")
    void updateStatusInChat(Object status, Object userId, Object chatId);


    /**
     * 一次性删除一个用户在一个聊天中所有记录的状态
     *
     * @param userId 用户id
     * @param chatId 聊天id
     * @name deleteAllRecordInChat
     * @notice none
     * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
     * @date 2019/5/10
     */
    @ExtUpdate("delete r from  record r inner join message  m on r.message_id = m.id where r.user_id = ? and m.chat_id = ?  " )
    void deleteAllRecordInChat(Object userId, Object chatId);


}
