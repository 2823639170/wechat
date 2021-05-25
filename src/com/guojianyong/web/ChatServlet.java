package com.guojianyong.web;


import com.guojianyong.model.*;
import com.guojianyong.service.ChatService;
import com.guojianyong.service.MessageService;
import com.guojianyong.service.constants.ServiceMessage;
import com.guojianyong.service.constants.Status;
import com.guojianyong.service.factory.ServiceProxyFactory;
import com.guojianyong.service.impl.ChatServiceImpl;
import com.guojianyong.service.impl.MessageServiceImpl;
import com.guojianyong.service.serve.ChatServer;
import com.guojianyong.utils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;

import static com.guojianyong.utils.BeanUtils.jsonToJavaObject;
import static com.guojianyong.utils.ControllerUtils.returnJsonObject;


public class ChatServlet extends BaseServlet {

    private final ChatService chatService = (ChatService) new ServiceProxyFactory().getProxyInstance(new ChatServiceImpl());
    private final MessageService messageService = (MessageService) new ServiceProxyFactory().getProxyInstance(new MessageServiceImpl());

    /**
     * 提供用户创建群聊的服务流程，不提供创建个人私聊
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void createGroupChat(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Chat chat = (Chat) jsonToJavaObject(req.getInputStream(), Chat.class);
        ServiceResult result;
        //创建一个群聊
        result = chatService.createChat(chat, true);
        if (Status.ERROR.equals(result.getStatus())) {
            returnJsonObject(resp, result);
            return;
        }
        //从chatService中重新获取chat的信息
        chat = (Chat) result.getData();
        //将用户加入群聊
        Member member = new Member();
        member.setUserId(chat.getOwnerId());
        member.setChatId(chat.getId());
        result = chatService.joinChat(new Member[]{member});
        if (Status.ERROR.equals(result.getStatus())) {
            returnJsonObject(resp, result);
            return;
        }
        //如果两个操作都成功，返回创建群聊成功和群号，覆盖下层service的消息
        result.setMessage(ServiceMessage.CREATE_GROUP_CHAT_SUCCESS.message + chat.getNumber() + ServiceMessage.PLEASE_JOIN_CHAT.message);
        returnJsonObject(resp, result);
    }

    /**
     * 提供用户加入群聊的服务
     * @param req
     * @param resp
     * @throws IOException
     */
    public void joinGroupChat(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String number = req.getParameter("number");
        String userId = req.getParameter("user_id");
        String apply = req.getParameter("apply");
        ServiceResult result;
        result = chatService.joinChatByNumber(new BigInteger(userId), number, apply);
        if (Status.ERROR.equals(result.getStatus())) {
            returnJsonObject(resp, result);
            return;
        }
        //加群成功后通知群里所有在线用户
        Member member = (Member) result.getData();
        //生成打招呼消息
        Message message = chatService.getHelloMessage(member);
        messageService.insertMessage(message);
        ChatServer.addMember(member, message);
        returnJsonObject(resp, result);
    }

    /**
     * 提供退出群聊的服务
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void quitChat(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Member member = (Member) jsonToJavaObject(req.getInputStream(), Member.class);
        ServiceResult result;
        result = chatService.quitChat(member);
        returnJsonObject(resp, result);
    }

    /**
     * 提供将一个聊天成员移除的服务
     * @param req
     * @param resp
     * @throws IOException
     */
    public void remove(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String memberId = req.getParameter("member_id");
        ServiceResult result;
        //检查操作用户
        User user = (User) req.getSession().getAttribute("login");
        if (chatService.isOwner(new BigInteger(memberId), user.getId())) {
            result = chatService.removeFromChat(new BigInteger(memberId));
        }else{
            result = new ServiceResult(Status.ERROR,ServiceMessage.NOT_OWNER.message,null);
        }
        returnJsonObject(resp, result);
    }


    /**
     * 提供获取一个用户的聊天列表的服务
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void listChat(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) BeanUtils.populate(req.getParameterMap(), User.class);
        ServiceResult result;
        result = chatService.listChat(user);
        returnJsonObject(resp, result);
    }

    /**
     * 提供获取一个用户的一个聊天的服务
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void getChat(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String number = req.getParameter("number");
        String userId = req.getParameter("user_id");
        ServiceResult result;
        result = chatService.getChat(number, new BigInteger(userId));
        returnJsonObject(resp, result);
    }

    /**
     * 提供查看群成员的服务
     * @param req
     * @param resp
     * @throws IOException
     */
    public void listMember(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String chatId = req.getParameter("chat_id");
        ServiceResult result;
        result = chatService.listMember(new BigInteger(chatId));
        returnJsonObject(resp, result);
    }


}
