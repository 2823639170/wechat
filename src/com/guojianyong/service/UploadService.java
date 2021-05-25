package com.guojianyong.service;

import com.guojianyong.model.ServiceResult;

import javax.servlet.http.Part;


public interface UploadService {

    /**
     * 负责将文件写入文件，并将数据库表对应的记录上的photo属性值修改为文件名
     * @param part
     * @param id
     * @param tableName
     * @return
     */
    ServiceResult uploadPhoto(Part part, Object id, String tableName);

    /**
     * 负责将文件写入文件，并将数据库表对应聊天背景属性值修改为文件名
     * @param part
     * @param id
     * @return
     */
    ServiceResult uploadBackground(Part part, Object id);


    /**
     * 负责将文件写入文件，并返回文件名
     * @param part
     * @return
     */
    ServiceResult uploadFile(Part part);


}
