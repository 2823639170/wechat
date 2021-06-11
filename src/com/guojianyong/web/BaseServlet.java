package com.guojianyong.web;

import com.guojianyong.exception.ServiceException;
import com.guojianyong.utils.ControllerUtils;
import com.guojianyong.web.annotation.Action;
import com.guojianyong.web.constant.RequestMethod;
import com.guojianyong.web.constant.WebPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



/**
 * 所有servlet的父类，负责调用相应的方法
 */
public class BaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 解决post请求中文乱码问题
        // 一定要在获取请求参数之前调用才有效
//        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        doAction(req , resp);

    }

    /**负责将请求分发到对应的Action方法
     *
     * @param req
     * @param resp
     */
    public void doAction(HttpServletRequest req, HttpServletResponse resp) {
        //获取请求中的method参数，转换成对应的枚举类型
        try {
            String name = req.getParameter("method");
            RequestMethod requestMethod = ControllerUtils.valueOf(name);
            if (RequestMethod.INVALID_REQUEST.equals(requestMethod)) {
                toErrorPage("无效的访问链接，系统无法识别您的请求指向的服务内容：" + req.getRequestURI(), req, resp);
                return;
            } else {
                boolean isMacth =false;
                //根据方法上的注解找到对应的Action方法并执行
                Method[] methods = this.getClass().getMethods();
                for (Method m : methods) {
                    Action action = m.getAnnotation(Action.class);
                    if (action != null && action.method().equals(requestMethod)) {
                        try {
                            m.invoke(this, req, resp);
                            isMacth=true;
                            break;
                        } catch (ServiceException e) {
                            //服务层抛出的异常信息与用户的操作有关，不包含底层细节，可以向用户输出
                            e.printStackTrace();
                            toErrorPage(e.getMessage(), req, resp);
                            return;
                        }
                    }
                }
                if(!isMacth){
                    toErrorPage("无效的访问链接，系统无法识别您的请求指向的服务内容"+ req.getRequestURI(), req, resp);
                }
            }
        } catch (ExceptionInInitializerError e) {
            e.printStackTrace();
            //此处的异常包含细节信息，对客户端隐藏
//            toErrorPage(SYSTEM_EXECEPTION.message, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            //此处的异常包含细节信息，对客户端隐藏
//            toErrorPage(REQUEST_INVALID.message, req, resp);
        }
    }

    /**
     * 转发到错误界面，向客户端输出错误信息
     * @param message
     * @param req
     * @param resp
     */
    public static void toErrorPage(String message, HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("message", message);
        try {
            req.getRequestDispatcher(WebPage.ERROR_JSP.toString()).forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            try {
                resp.getWriter().write("服务器异常");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}