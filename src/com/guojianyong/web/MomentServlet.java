package com.guojianyong.web;


import com.guojianyong.model.Moment;
import com.guojianyong.model.ServiceResult;
import com.guojianyong.service.MomentService;
import com.guojianyong.service.factory.ServiceProxyFactory;
import com.guojianyong.service.impl.MomentServiceImpl;
import com.guojianyong.web.annotation.Action;
import com.guojianyong.web.constant.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;

import static com.guojianyong.utils.BeanUtils.jsonToJavaObject;
import static com.guojianyong.utils.ControllerUtils.returnJsonObject;

/**
 * 负责朋友圈相关流程
 */
public class MomentServlet extends BaseServlet {
    private final MomentService momentService = (MomentService) new ServiceProxyFactory().getProxyInstance(new MomentServiceImpl());

    /**
     * 提供发布朋友圈的业务流程
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Action(method = RequestMethod.ADD_DO)
    public void postMoment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Moment moment = (Moment) jsonToJavaObject(req.getInputStream(), Moment.class);
        ServiceResult result;
        result = momentService.insertMoment(moment);
        returnJsonObject(resp, result);
    }

    /**
     * 提供删除朋友圈的业务流程
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Action(method = RequestMethod.DELETE_DO)
    public void deleteMoment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String momentId = req.getParameter("moment_id");
        ServiceResult result;
        result = momentService.removeMoment(new BigInteger(momentId));
        returnJsonObject(resp, result);
    }

    /**
     * 提供更新朋友圈的业务流程
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Action(method = RequestMethod.UPDATE_DO)
    public void updateMoment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Moment moment = (Moment) jsonToJavaObject(req.getInputStream(), Moment.class);
        ServiceResult result;
        result = momentService.updateMoment(moment);
        returnJsonObject(resp, result);
    }

    /**
     * 提供获取个人朋友圈的业务流程
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Action(method = RequestMethod.MOMENT_DO)
    public void listMoment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        String page = req.getParameter("page");
        ServiceResult result;
        result = momentService.listMyMoment(new BigInteger(userId), new Integer(page));
        returnJsonObject(resp, result);
    }

    /**
     * 提供获取朋友圈动态的业务流程
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Action(method = RequestMethod.NEWS_DO)
    public void listNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        String page = req.getParameter("page");
        ServiceResult result;
        result = momentService.listNews(new BigInteger(userId), new Integer(page));
        returnJsonObject(resp, result);
    }


    /**
     * 提供获取朋友圈照片的业务流程
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Action(method = RequestMethod.PHOTO_DO)
    public void listPhoto(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        String page = req.getParameter("page");
        ServiceResult result;
        result = momentService.listPhoto(new BigInteger(userId), new Integer(page));
        returnJsonObject(resp, result);
    }

    /**
     * 提供朋友圈点赞的服务
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Action(method = RequestMethod.LOVE_DO)
    public void love(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        String momentId = req.getParameter("moment_id");
        ServiceResult result;
        result = momentService.love(new BigInteger(userId), new BigInteger(momentId));
        returnJsonObject(resp, result);
    }
}
