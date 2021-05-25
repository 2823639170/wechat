package com.guojianyong.web;


import com.guojianyong.model.ServiceResult;
import com.guojianyong.service.UploadService;
import com.guojianyong.service.factory.ServiceProxyFactory;
import com.guojianyong.service.impl.UploadServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

import static com.guojianyong.utils.ControllerUtils.returnJsonObject;

/**
 * 负责上传文件流程
 */
public class UploadServlet extends BaseServlet {
    private final UploadService uploadService = (UploadService) new ServiceProxyFactory().getProxyInstance(new UploadServiceImpl());

    public void uploadPhoto(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Part part = req.getPart("photo");
        Object id = req.getParameter("id");
        String tableName = req.getParameter("table");
        ServiceResult result;
        result = uploadService.uploadPhoto(part, id, tableName);
        returnJsonObject(resp, result);
    }

    public void uploadBackground(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Part part = req.getPart("photo");
        Object id = req.getParameter("id");
        ServiceResult result;
        result = uploadService.uploadBackground(part, id);
        returnJsonObject(resp, result);
    }


    public void uploadfile(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Part part = req.getPart("file");
        ServiceResult result;
        result = uploadService.uploadFile(part);
        returnJsonObject(resp, result);
    }
}
