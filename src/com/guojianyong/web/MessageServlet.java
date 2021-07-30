package com.guojianyong.web;

import com.guojianyong.model.ServiceResult;
import com.guojianyong.service.MessageService;
import com.guojianyong.service.constants.ServiceMessage;
import com.guojianyong.service.factory.ServiceProxyFactory;
import com.guojianyong.service.impl.MessageServiceImpl;
import com.guojianyong.web.annotation.Action;
import com.guojianyong.web.constant.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;

import static com.guojianyong.utils.ControllerUtils.returnJsonObject;

/**
 * 提供消息记录的服务流程控制
 */
public class MessageServlet extends BaseServlet {
    private final MessageService messageService = (MessageService) new ServiceProxyFactory().getProxyInstance(new MessageServiceImpl());


    /**
     * 提供获取一个聊天的所有聊天记录的服务
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Action(method = RequestMethod.LIST_DO)
    public void listMessage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        String chatId = req.getParameter("chat_id");
        String page = req.getParameter("page");
        ServiceResult result = null;
        result = messageService.listAllMessage(new BigInteger(userId), new BigInteger(chatId), new Integer(page));
        returnJsonObject(resp, result);
    }


    /**
     * 提供获取所有未读消息的服务
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Action(method = RequestMethod.UNREAD_DO)
    public void listUnreadMessage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        String page = req.getParameter("page");
        String chatId = req.getParameter("chat_id");
        ServiceResult result;
        //如果没有写chatId，则加载所有未读消息
        if (chatId == null) {
            result = messageService.listAllUnreadMessage(new BigInteger(userId), new Integer(page));
        } else {
            result = messageService.listUnreadMessage(new BigInteger(userId), new BigInteger(chatId), new Integer(page));
        }
        returnJsonObject(resp, result);
    }


    /**
     * 提供将一个聊天中的消息设置为已读的服务
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Action(method = RequestMethod.READ_DO)
    public void read(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        String chatId = req.getParameter("chat_id");
        ServiceResult result = new ServiceResult();
        result.setMessage(ServiceMessage.ALREADY_READ.message);
        messageService.setAlreadyRead(new BigInteger(userId), new BigInteger(chatId));
        returnJsonObject(resp, result);
    }

    /**
     * 提供将一个聊天中的消息记录清除服务
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Action(method = RequestMethod.CLEAR_DO)
    public void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        String chatId = req.getParameter("chat_id");
        ServiceResult result;
        result = messageService.removeAllMessage(new BigInteger(userId), new BigInteger(chatId));
        returnJsonObject(resp, result);
    }

    /**
     * 提供将一个聊天中的消息记录导出的服务
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Action(method = RequestMethod.EXPORT_DO)
    public void export(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        String chatId = req.getParameter("chat_id");
        ServiceResult result;
        result = messageService.exportMessage(new BigInteger(userId), new BigInteger(chatId));
        returnJsonObject(resp, result);
    }


}
