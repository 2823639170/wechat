package com.guojianyong.web;


import com.guojianyong.model.Chat;
import com.guojianyong.model.Friend;
import com.guojianyong.model.Message;
import com.guojianyong.model.ServiceResult;
import com.guojianyong.service.ChatService;
import com.guojianyong.service.FriendService;
import com.guojianyong.service.MessageService;
import com.guojianyong.service.MomentService;
import com.guojianyong.service.constants.MessageType;
import com.guojianyong.service.constants.ServiceMessage;
import com.guojianyong.service.factory.ServiceProxyFactory;
import com.guojianyong.service.impl.*;
import com.guojianyong.server.ChatServer;
import com.guojianyong.web.annotation.Action;
import com.guojianyong.web.constant.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;

import static com.guojianyong.service.constants.ServiceMessage.CANNOT_DELETE_HYC;
import static com.guojianyong.service.constants.Status.ERROR;
import static com.guojianyong.utils.BeanUtils.jsonToJavaObject;
import static com.guojianyong.utils.ControllerUtils.returnJsonObject;

/**
 * 负责好友相关业务流程
 */
public class FriendServlet extends BaseServlet {
    private final FriendService friendService = (FriendService) new ServiceProxyFactory().getProxyInstance(new FriendServiceImpl());
    private final ChatService chatService = (ChatService) new ServiceProxyFactory().getProxyInstance(new ChatServiceImpl());
    private final MessageService messageService = (MessageService) new ServiceProxyFactory().getProxyInstance(new MessageServiceImpl());
    private final MomentService momentService = (MomentService)new ServiceProxyFactory().getProxyInstance(new MomentServiceImpl());

    /**
     * 提供添加好友的业务流程
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Action(method = RequestMethod.ADD_DO)
    synchronized public void addFriend(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Friend friend = (Friend) jsonToJavaObject(req.getInputStream(), Friend.class);
        ServiceResult result;
        //加好友
        result = friendService.addFriend(friend);
        if (ERROR.equals(result.getStatus())) {
            returnJsonObject(resp, result);
            return;
        }
        //添加好友后判断是否对方也将自己添加为好友，如果双向添加，则建立聊天关系，否则发送好友通知
        if (friendService.isFriend(friend)) {

            Chat chat = (Chat) chatService.createFriendChat(friend).getData();
            //发送打招呼消息
            Message message = new Message();
            message.setChatId(chat.getId());
            message.setSenderId(friend.getUserId());
            message.setContent(ServiceMessage.AGREE_FRIEND.message);
            message.setTime(new Timestamp(System.currentTimeMillis()));
            message.setType(MessageType.USER.toString());
            messageService.insertMessage(message);
            //初始化朋友圈
            momentService.initNews(friend);
        } else {
            //生成的加好友通知，发送实时通知并存到数据库
            Message message = (Message) result.getData();
            ChatServer.sendNotify(message, friend.getFriendId());
            messageService.insertMessage(message);
        }
        returnJsonObject(resp, result);
    }

    /**
     * 提供获取好友列表的服务
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Action(method = RequestMethod.LIST_DO)
    public void listFriend(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        ServiceResult result;
        result = friendService.listFriend(new BigInteger(userId));
        returnJsonObject(resp, result);
    }

    /**
     * 提供获取好友列表的服务
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Action(method = RequestMethod.UPDATE_DO)
    public void updateFriend(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Friend friend = (Friend) jsonToJavaObject(req.getInputStream(), Friend.class);
        ServiceResult result;
        result = friendService.updateFriend(friend);
        returnJsonObject(resp, result);
    }

    /**
     * 提供删除好友的服务
     * @param req
     * @param resp
     * @throws IOException
     */
    @Action(method = RequestMethod.DELETE_DO)
    synchronized public void deleteFriend(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String friendId = req.getParameter("friend_id");
        String userId = req.getParameter("user_id");

        ServiceResult result;
        //系统账号不可删除
        //删除好友之前将聊天关系移除,请求中的好友对象只含有userId和friendId，所以需要重新获取
        Friend friend = friendService.getByUidAndFriendId(new BigInteger(userId), new BigInteger(friendId));
        if (friend == null) {
            result = new ServiceResult();
            result.setMessage(ServiceMessage.FRIEND_NOT_EXIST.message);
            result.setStatus(ERROR);
            returnJsonObject(resp, result);
            return;
        }
        if (UserServiceImpl.systemId.equals(friend.getFriendId())) {
            returnJsonObject(resp, new ServiceResult(ERROR, ServiceMessage.CANNOT_DELETE_SYSTEM.message, null));
            return;
        }
        if (UserServiceImpl.hycId.equals(friend.getFriendId())) {
            returnJsonObject(resp, new ServiceResult(ERROR, CANNOT_DELETE_HYC.message, null));
            return;
        }
        //调用聊天服务将聊天关系解除
        Chat chat = new Chat();
        chat.setId(friend.getChatId());
        chatService.removeChat(chat);
        //解除好友关系
        result = friendService.removeFriend(friend);
        returnJsonObject(resp, result);
    }

}
