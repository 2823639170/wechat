package com.guojianyong.dao;

import com.guojianyong.dao.impl.simpleMBatis.annotation.ExtSelect;
import com.guojianyong.model.Moment;

import java.util.List;


public interface MomentDao extends BaseDao {

    /**
     * 通过朋友圈id查询一个朋友圈
     * @param id
     * @return
     */
    @ExtSelect(value = "select * from moment  where id = ? ")
    Moment getMomentById(Object id);

    /**
     * 通过用户id和状态查询一个朋友圈
     * @param ownerId
     * @param stauts
     * @return
     */
    @ExtSelect(value = "select * from moment  where owner_id = ? and status = ? ")
    Moment getMomentByOwnerIdAndStatus(Object ownerId, Object stauts);


    /**
     * 通过用户id逆序查询所有自己发布的朋友圈
     * @param ownerId
     * @param limit
     * @param offset
     * @return
     */
    @ExtSelect("select * from moment  where owner_id = ?  order by time desc limit ? offset ?  ")
    List<Moment> listMyMomentByOwnerIdDesc(Object ownerId, int limit, int offset);

    /**
     * 通过用户id查询所有自己发布的朋友圈
     * @param ownerId
     * @param limit
     * @param offset
     * @return
     */
    @ExtSelect("select * from moment  where owner_id = ?  order by time limit ? offset ?  ")
    List<Moment> listMyMomentByOwnerId(Object ownerId, int limit, int offset);


    /**
     * 通过用户id查询所有自己发布的朋友圈中的图片
     * @param ownerId
     * @param limit
     * @param offset
     * @return
     */
    @ExtSelect("select photo from moment  where owner_id = ?  order by time desc limit ? offset ?  ")
    List<Moment> listPhoto(Object ownerId, int limit, int offset);
}
