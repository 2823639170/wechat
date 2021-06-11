package com.guojianyong.web;


import com.guojianyong.model.ServiceResult;
import com.guojianyong.service.UploadService;
import com.guojianyong.service.factory.ServiceProxyFactory;
import com.guojianyong.service.impl.UploadServiceImpl;
import com.guojianyong.web.annotation.Action;
import com.guojianyong.web.constant.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

import static com.guojianyong.utils.ControllerUtils.returnJsonObject;

/**
 * 负责上传文件流程
 */

@MultipartConfig(location = "C:/Users/EVA/ideaProject2/wechat/web/upload")
public class UploadServlet extends BaseServlet {
    private final UploadService uploadService = (UploadService) new ServiceProxyFactory().getProxyInstance(new UploadServiceImpl());

    @Action(method = RequestMethod.UPLOADPHOTO_DO)
    public void uploadPhoto(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Part part = req.getPart("photo");
        Object id = req.getParameter("id");
        String tableName = req.getParameter("table");
        ServiceResult result;
        result = uploadService.uploadPhoto(part, id, tableName);
        returnJsonObject(resp, result);
    }
    @Action(method = RequestMethod.BACKGROUND_DO)
    public void uploadBackground(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Part part = req.getPart("photo");
        Object id = req.getParameter("id");
        ServiceResult result;
        result = uploadService.uploadBackground(part, id);
        returnJsonObject(resp, result);
    }

    @Action(method = RequestMethod.UPLOADFILE_DO)
    public void uploadfile(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Part part = req.getPart("file");
        ServiceResult result;
        result = uploadService.uploadFile(part);
        returnJsonObject(resp, result);
    }
}
