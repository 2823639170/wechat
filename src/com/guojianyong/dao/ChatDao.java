package com.guojianyong.dao;


import com.guojianyong.dao.impl.simpleMBatis.annotation.ExtSelect;
import com.guojianyong.model.Chat;

import java.util.List;

public interface ChatDao extends BaseDao {


    /**
     * 通过聊天id查询一个聊天
     * @param id
     * @return
     */
    @ExtSelect(value = "select * from chat where id = ? ")
    Chat getChatById(Object id);

    /**
     * 通过用户id查询该用户所处的所有聊天
     * @param userId
     * @return
     */
    @ExtSelect(value = "select c.id,c.number,c.owner_id,c.name,c.type,c.member,c.photo,c.status,c.gmt_create,c.gmt_modified " +
            "from chat as c,member as m where c.id = m.chat_id and m.user_id = ?")
    List<Chat> listByUserId(Object userId);


    /**
     *  将对方的头像作为聊天头像，将对方的昵称作为聊天名称<br/>
     * 查询一个聊天信息
     * @param chatId
     * @param userId
     * @return
     */
    @ExtSelect(value = "select c.id,c.number,c.owner_id,u.name as name,c.member,u.photo as photo,c.type,c.status,c.gmt_create,c.gmt_modified " +
            "from chat as c,member as m,user as u where c.id = ? and m.chat_id = c.id and u.id = m.user_id and u.id <> ?")
    Chat toFriendChat(Object chatId, Object userId);


    /**
     * 通过聊天编号查询一个聊天
     *
     * @param number 聊天编号
     * @return
     * @name getByChatNumber
     */
    @ExtSelect(value = "select * from chat where number = ? ")
    Chat getByChatNumber(Object number);
}
