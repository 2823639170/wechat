package com.guojianyong.service;


import com.guojianyong.model.Friend;
import com.guojianyong.model.Moment;
import com.guojianyong.model.ServiceResult;

import java.math.BigInteger;


public interface MomentService {

    /**
     * 插入一条朋友圈
     * @param moment
     * @return
     */
    ServiceResult insertMoment(Moment moment);


    /**
     * 给好友双方初始化朋友圈，互相添加动态
     * @param friend
     * @return
     */
    ServiceResult initNews(Friend friend);

    /**
     * 删除一条朋友圈
     * @param momentId
     * @return
     */
    ServiceResult removeMoment(BigInteger momentId);

    /**
     * 更新一条朋友圈
     * @param moment
     * @return
     */
    ServiceResult updateMoment(Moment moment);

    /**
     * 查询一个用户所发的所有朋友圈
     * @param userId
     * @param page
     * @return
     */
    ServiceResult listMyMoment(BigInteger userId, int page);

    /**
     * 查询一个用户可见的所有朋友圈，包括自己的和朋友的
     * @param userId
     * @param page
     * @return
     */
    ServiceResult listNews(BigInteger userId, int page);

    /**
     * 更新一个用户对一条朋友圈的点赞状态
     * @param userId
     * @param momentId
     * @return
     */
    ServiceResult love(BigInteger userId, BigInteger momentId);

    /**
     * 查询一个用户朋友圈中的所有图片
     * @param userId
     * @param page
     * @return
     */
    ServiceResult listPhoto(BigInteger userId, int page);

}
