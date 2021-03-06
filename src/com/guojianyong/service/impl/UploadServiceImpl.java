package com.guojianyong.service.impl;


import com.guojianyong.dao.MomentDao;
import com.guojianyong.dao.UserDao;
import com.guojianyong.dao.impl.simpleMBatis.session.SqlSession;
import com.guojianyong.exception.DaoException;
import com.guojianyong.model.Moment;
import com.guojianyong.model.ServiceResult;
import com.guojianyong.model.User;
import com.guojianyong.service.UploadService;
import com.guojianyong.service.constants.Status;

import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigInteger;

import static com.guojianyong.service.constants.ServiceMessage.*;
import static com.guojianyong.utils.UploadUtils.toFileName;
import static com.guojianyong.utils.UploadUtils.toPhotoName;


public class UploadServiceImpl implements UploadService {

    private final UserDao userDao = SqlSession.getMapper(UserDao.class);
    private final MomentDao momentDao = SqlSession.getMapper(MomentDao.class);

    /**
     * 负责将文件写入文件，并将数据库表对应的记录上的photo属性值修改为文件名
     * @param part
     * @param id
     * @param tableName
     * @return
     */
    @Override
    public ServiceResult uploadPhoto(Part part, Object id, String tableName) {
        String fileName;
        try {
            //获取名字并下载
            fileName = toPhotoName(part);
            if ("user".equalsIgnoreCase(tableName)) {
                User user = new User();
                user.setId(new BigInteger(String.valueOf(id)));
                user.setPhoto(fileName);
                if (userDao.update(user) != 1) {
                    return new ServiceResult(Status.ERROR, UPDATE_USER_FAILED.message, user);
                }
            } else if ("moment".equalsIgnoreCase(tableName)) {
                Moment moment = new Moment();
                moment.setId(new BigInteger(String.valueOf(id)));
                moment.setPhoto(fileName);
                if (momentDao.update(moment) != 1) {
                    return new ServiceResult(Status.ERROR, DATABASE_ERROR.message, moment);
                }
            } else {
                return new ServiceResult(Status.ERROR, UNSUPPROT_TABLE.message, tableName);
            }
        } catch (DaoException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, DATABASE_ERROR.message, id);
        } catch (IOException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, UPLOAD_FAILED.message, id);
        }
        return new ServiceResult(Status.SUCCESS, UPDATE_INFO_SUCCESS.message, fileName);
    }

    /**
     * 负责将文件写入文件，并将数据库表对应聊天背景属性值修改为文件名
     * @param part
     * @param id
     * @return
     */
    @Override
    public ServiceResult uploadBackground(Part part, Object id) {
        String fileName;
        try {
            fileName = toPhotoName(part);
            User user = new User();
            user.setId(new BigInteger(String.valueOf(id)));
            user.setChatBackground(fileName);
            if (userDao.update(user) != 1) {
                return new ServiceResult(Status.ERROR, UPDATE_USER_FAILED.message, user);
            }
        } catch (DaoException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, DATABASE_ERROR.message, id);
        } catch (IOException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, UPLOAD_FAILED.message, id);
        }
        return new ServiceResult(Status.SUCCESS, UPDATE_BACKGROUND_SUCCESS.message, fileName);
    }

    /**
     * 负责将文件写入文件，并返回文件名
     * @param part
     * @return
     */
    @Override
    public ServiceResult uploadFile(Part part) {
        String fileName;
        try {
            fileName = toFileName(part);
        } catch (IOException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, UPLOAD_FAILED.message, part);
        }
        return new ServiceResult(Status.SUCCESS, UPLOAD_SUCCESS.message, fileName);
    }
}
