package com.guojianyong.filter;

import com.guojianyong.web.UserServlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        //尝试自动登陆
//        new UserServlet().autoLogin(httpServletRequest);

        Object user = httpServletRequest.getSession().getAttribute("login");
        System.out.println(user);
        if (user == null) {
            httpServletRequest.getRequestDispatcher("/pages/login.jsp").forward(servletRequest,servletResponse);
        } else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
