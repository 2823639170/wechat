package com.guojianyong.service;


import com.guojianyong.model.Remark;
import com.guojianyong.model.ServiceResult;

import java.math.BigInteger;

public interface RemarkService {
    /**
     * 添加一条评论
     * @param remark
     * @return
     */
    ServiceResult addRemark(Remark remark);

    /**
     * 查询一条朋友圈的评论
     * @param momentId
     * @param page
     * @return
     */
    ServiceResult listRemark(BigInteger momentId, int page);

    /**
     * 删除一条评论
     * @param remarkId
     * @return
     */
    ServiceResult removeRemark(BigInteger remarkId);


}
