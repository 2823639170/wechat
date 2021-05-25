package com.guojianyong.dao;

import com.guojianyong.dao.impl.simpleMBatis.annotation.ExtSelect;
import com.guojianyong.model.News;

import java.util.List;

public interface NewsDao extends BaseDao {

    /**
     * 通过用户id查询所有自己可见的所有朋友圈动态
     * @param userId
     * @param limit
     * @param offset
     * @return
     */
    @ExtSelect("select * from news where user_id = ?  order by gmt_create desc limit ? offset ?  ")
    List<News> listNewsByUserId(Object userId, int limit, int offset);


    /**
     * 通过朋友圈id和用户id查询一个朋友圈动态
     * @param momentId
     * @param userId
     * @return
     */
    @ExtSelect(value = "select * from news where moment_id = ? and user_id = ? ")
    News getNewsByMomentIdAndUserId(Object momentId, Object userId);
}
