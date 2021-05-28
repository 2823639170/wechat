package com.guojianyong.web;


import com.guojianyong.model.*;
import com.guojianyong.service.ChatService;
import com.guojianyong.service.FriendService;
import com.guojianyong.service.MomentService;
import com.guojianyong.service.UserService;
import com.guojianyong.service.constants.ServiceMessage;
import com.guojianyong.service.constants.Status;
import com.guojianyong.service.factory.ServiceProxyFactory;
import com.guojianyong.service.impl.ChatServiceImpl;
import com.guojianyong.service.impl.FriendServiceImpl;
import com.guojianyong.service.impl.MomentServiceImpl;
import com.guojianyong.service.impl.UserServiceImpl;
import com.guojianyong.utils.BeanUtils;
import com.guojianyong.web.constant.WebPage;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;

import static com.guojianyong.utils.BeanUtils.jsonToJavaObject;
import static com.guojianyong.utils.ControllerUtils.returnJsonObject;


public class UserServlet extends BaseServlet {


    private final int AUTO_LOGIN_AGE = 60 * 60 * 24 *30;
    private final String AUTO_LOGIN_PATH = "/";
    private final UserService userService = (UserService) new ServiceProxyFactory().getProxyInstance(new UserServiceImpl());
    private final ChatService chatService = (ChatService) new ServiceProxyFactory().getProxyInstance(new ChatServiceImpl());
    private final FriendService friendService = (FriendService) new ServiceProxyFactory().getProxyInstance(new FriendServiceImpl());
    private final MomentService momentService = (MomentService)new ServiceProxyFactory().getProxyInstance(new MomentServiceImpl());


    public void test(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("okk");

    }

    /**
     * 提供用户注册的业务流程
     * @param req
     * @param resp
     * @throws IOException
     * @throws ServletException
     */
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) BeanUtils.populate(req.getParameterMap(), User.class);
        ServiceResult result;
        //检查用户注册信息
        result = userService.checkRegister(user);
        if (Status.ERROR.equals(result.getStatus())) {
            req.setAttribute("message",result.getMessage());
            req.setAttribute("data",result.getData());
            req.getRequestDispatcher(WebPage.REGISTER_JSP.toString()).forward(req,resp);
            return;
        }
        //插入用户
        result = userService.insertUser(user);
        if (Status.ERROR.equals(result.getStatus())) {
            req.setAttribute("message",result.getMessage());
            req.setAttribute("data",result.getData());
            req.getRequestDispatcher(WebPage.REGISTER_JSP.toString()).forward(req,resp);
        } else {
            //注册成功后将用户添加到聊天总群中
            user = (User) result.getData();
            addToDefaultChat(user);
            //与系统账号加好友
            addToSystemChat(user);
            addToHYCChat(user);
            req.setAttribute("message",result.getMessage());
            req.setAttribute("data",result.getData());
            req.getRequestDispatcher("http://localhost:8080/wechat/pages/index.jsp").forward(req,resp);
        }
    }


    /**
     * 提供用户登陆的业务流程
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user =  BeanUtils.populate(req.getParameterMap(), User.class);

        ServiceResult result;
        if (user == null) {
            result = new ServiceResult(Status.ERROR, ServiceMessage.PARAMETER_NOT_ENOUGHT.message, null);
            req.setAttribute("message",result.getMessage());
            req.setAttribute("data",result.getData());
            req.getRequestDispatcher(WebPage.LOGIN_JSP.toString()).forward(req,resp);
            return;
        }
        HttpSession session = req.getSession(false);
        //检查用户是否已经建立会话并且已经具有登陆信息
        if (session == null || session.getAttribute("login") == null) {
            //检查是不是游客登陆，游客登陆的话先创建个游客账号然后登陆
            if ("visitor".equals(user.getEmail())) {
                result = userService.visitorLogin();
                if (Status.ERROR.equals(result.getStatus())) {
                    req.setAttribute("message",result.getMessage());
                    req.setAttribute("data",result.getData());
                    req.getRequestDispatcher(WebPage.LOGIN_JSP.toString()).forward(req,resp);
                    return;
                }
                User visitor = (User) result.getData();
                //把游客加入聊天总群
                addToDefaultChat(visitor);
                //与系统账号加好友
                addToSystemChat(visitor);
//                addToHYCChat(visitor);
            } else {
                //如果是用户登陆，校验密码是否正确
                result = userService.checkPassword(user);
                if (Status.ERROR.equals(result.getStatus())) {
                    req.setAttribute("message",result.getMessage());
                    req.setAttribute("data",result.getData());
                    req.getRequestDispatcher(WebPage.LOGIN_JSP.toString()).forward(req,resp);
                    return;
                } else {
                    //校验密码成功时，给会话中添加用户信息
                    result = userService.getUser(user.getId());
                    user = (User) result.getData();
                    //如果设置自动登陆，则添加cookie
                    if (req.getParameter("auto_login")!=null) {
                        setAutoLoginCookie(resp,req,  String.valueOf(user.getId()));
                    }

                }
            }

        } else {
            //先从session获取用户信息，再更新用户信息到会话中
            user = (User) session.getAttribute("login");
            result = userService.getUser(user.getId());
            System.out.println(result);
        }
        System.out.println("ssss");
        req.getSession(true).setAttribute("login", result.getData());
        req.getRequestDispatcher("http://localhost:8080/wechat/pages/index.jsp").forward(req,resp);
    }

    /**
     * 提供获取用户个人信息的业务流程
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) BeanUtils.populate(req.getParameterMap(), User.class);
        ServiceResult result;
        //获取用户数据
        result = userService.getUser(user.getId());
        if (Status.ERROR.equals(result.getStatus())) {
            returnJsonObject(resp, result);
        } else {
            //获取数据成功时的处理
            resp.getWriter().write(result.getMessage());
        }
    }


    /**
     * 提供获取用户个人信息的业务流程
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session != null) {
            session.invalidate();
        }
        returnJsonObject(resp, new ServiceResult(Status.SUCCESS, ServiceMessage.LOGOUT_SUCCESS.message, null));
    }


    /**
     * 提供用户更新个人信息的业务流程
     * @param req
     * @param resp
     * @throws IOException
     */
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) jsonToJavaObject(req.getInputStream(), User.class);
        ServiceResult result;
        if (user != null && user.getWechatId() != null) {
            User oldUser = (User) userService.getUser(user.getId()).getData();
            if (!oldUser.getWechatId().equals(user.getWechatId())) {
                //如果请求要求修改微信名，先检查用户名（微信号）是否合法
                result = userService.checkWechatId(user.getWechatId());
                if (Status.ERROR.equals(result.getStatus())) {
                    returnJsonObject(resp, result);
                    return;
                }
            }
        }
        //更新用户数据
        result = userService.updateUser(user);
        returnJsonObject(resp, result);
    }

    /**
     * 提供用户更新密码的业务流程
     * @param req
     * @param resp
     * @throws IOException
     */
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String oldPwd = req.getParameter("old_password");
        String newPwd = req.getParameter("new_password");
        String userId = req.getParameter("user_id");
        ServiceResult result;
        //更新用户数据
        result = userService.updatePwd(oldPwd, newPwd, new BigInteger(userId));
        returnJsonObject(resp, result);
    }


    /**
     * 提供搜索用户的服务
     * @param req
     * @param resp
     * @throws IOException
     */
    public void list(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) BeanUtils.populate(req.getParameterMap(), User.class);
        ServiceResult result;
        result = userService.listUserLikeName(user.getName());
        if (Status.ERROR.equals(result.getStatus())) {
            returnJsonObject(resp, result);
            return;
        }
        returnJsonObject(resp, result);
    }

    /**
     * 提供自动登陆的服务
     * @param req
     */
    public void autoLogin(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user_id".equalsIgnoreCase(cookie.getName())) {
                    ServiceResult result = userService.getUser(cookie.getValue());
                    if (Status.SUCCESS.equals(result.getStatus())) {
//                        addToDefaultChat((User) result.getData());
                        //如果获取用户信息成功则设置‘login’属性
                        HttpSession session = req.getSession();
                        session.setAttribute("login", result.getData());
                        return;
                    }
                }
            }
        }
    }


    /**
     * 设置用于自动登陆的cookie
     * @param resp
     * @param req
     * @param userId
     */
    private void setAutoLoginCookie(HttpServletResponse resp, HttpServletRequest req, String userId) {
        Cookie cookie = new Cookie("user_id", userId);
        cookie.setMaxAge(AUTO_LOGIN_AGE);
        cookie.setPath(req.getContextPath());
        resp.addCookie(cookie);
    }

    /**
     * 将一个用户添加到聊天总群
     * @param user
     */
    private void addToDefaultChat(User user) {
        Member member = new Member();
        member.setChatId(BigInteger.valueOf(0));
        member.setUserId(user.getId());
        chatService.joinChat(new Member[]{member});
    }

    /**
     * 将一个用户添加到与系统的会话中
     * @param user
     */
    private void addToSystemChat(User user) {
        Friend friend = new Friend();
        //系统添加用户账号为好友
        friend.setUserId(UserServiceImpl.systemId);
        friend.setFriendId(user.getId());
        friendService.addFriend(friend);
        //用户添加系统账号为好友
        friend.setAlias(null);
        friend.setUserId(user.getId());
        friend.setFriendId(UserServiceImpl.systemId);
        friendService.addFriend(friend);
        //将用户和系统账号（id=0）添加到同一个聊天中
        chatService.createFriendChat(friend);
        //插入一条消息
        Message message = new Message();
        message.setContent("欢迎使用wechat在线聊天系统，我是本系统的开发者，如果程序在使用的过程中发现一些问题，请刷新一下浏览器.");
    }
    /**
     * 将一个用户添加到与系统的会话中
     * @param user
     */
    private void addToHYCChat(User user) {
        Friend friend = new Friend();
        friend.setUserId(BigInteger.valueOf(1));
        friend.setFriendId(user.getId());
        friendService.addFriend(friend);
        friend.setAlias(null);
        friend.setUserId(user.getId());
        friend.setFriendId(BigInteger.valueOf(1));
        friendService.addFriend(friend);
        chatService.createFriendChat(friend);
        //初始朋友圈
        momentService.initNews(friend);
    }
}
