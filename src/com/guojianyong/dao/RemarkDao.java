package com.guojianyong.dao;


import com.guojianyong.dao.impl.simpleMBatis.annotation.ExtSelect;
import com.guojianyong.model.Remark;

import java.util.List;

public interface RemarkDao extends BaseDao{

    /**
     * 通过用户id逆序查询所有自己发布的朋友圈
     * @param momentId
     * @param limit
     * @param offset
     * @return
     */
    @ExtSelect("select * from remark where moment_id = ?  order by time limit ? offset ?  ")
    List<Remark> listRemarkDesc(Object momentId, int limit, int offset);

    /**
     * 通过评论id查询一个评论
     * @param id
     * @return
     */
    @ExtSelect(value = "select * from remark where id = ? ")
    Remark getRemarkById(Object id);
}
