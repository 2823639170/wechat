package com.guojianyong.dao;


import com.guojianyong.dao.impl.simpleMBatis.annotation.ExtSelect;
import com.guojianyong.model.Member;

import java.math.BigInteger;
import java.util.List;

public interface MemberDao extends BaseDao {

    /**
     * 通过成员id查询一个成员
     * @param id
     * @return
     */
    @ExtSelect(value = "select * from member where id = ? ")
    Member getMemberById(Object id);

    /**
     * 通过用户id和聊天id查询一个成员
     * @param userId
     * @param chatId
     * @return
     */
    @ExtSelect(value = "select * from member where user_id = ? and chat_id = ? ")
    Member getMemberByUIdAndChatId(BigInteger userId, BigInteger chatId);

    /**
     * 返回一个聊天中的所有成员
     * @param chatId
     * @return
     */
    @ExtSelect(value = "select * from member where chat_id = ? ")
    List<Member> listMemberByChatId(Object chatId);

}
