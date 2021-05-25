package com.guojianyong.web;


import com.guojianyong.model.Remark;
import com.guojianyong.model.ServiceResult;
import com.guojianyong.service.RemarkService;
import com.guojianyong.service.factory.ServiceProxyFactory;
import com.guojianyong.service.impl.RemarkServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;

import static com.guojianyong.utils.BeanUtils.jsonToJavaObject;
import static com.guojianyong.utils.ControllerUtils.returnJsonObject;

/**
 * 负责评论的业务流程
 */
public class RemarkServlet extends BaseServlet {

    RemarkService remarkService = (RemarkService) new ServiceProxyFactory().getProxyInstance(new RemarkServiceImpl());

    /**
     * 提供发布评论的业务流程
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void postRemark(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Remark remark = (Remark) jsonToJavaObject(req.getInputStream(), Remark.class);
        ServiceResult result;
        result = remarkService.addRemark(remark);
        returnJsonObject(resp, result);
    }

    /**
     * 提供获取朋友圈评论的业务流程
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void listRemark(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String momentId = req.getParameter("moment_id");
        String page = req.getParameter("page");
        ServiceResult result;
        result = remarkService.listRemark(new BigInteger(momentId), Integer.parseInt(page));
        returnJsonObject(resp, result);
    }

    /**
     * 提供删除朋友圈评论的业务流程
     * @param req
     * @param resp
     * @throws IOException
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String remarkId = req.getParameter("remark_id");
        ServiceResult result;
        result = remarkService.removeRemark(new BigInteger(remarkId));
        returnJsonObject(resp, result);
    }
}
