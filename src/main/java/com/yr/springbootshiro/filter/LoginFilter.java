package com.yr.springbootshiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class LoginFilter extends AccessControlFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse,
                                      Object mappedValue) throws Exception {
        System.out.println("进入LoginFilter的is");
        Subject subject = SecurityUtils.getSubject();//得到当前用户对象
        System.out.println(subject.getPrincipal());//得到用户信息
        if (subject.getPrincipal() != null) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = ((HttpServletRequest)request);
        System.out.println("进入LoginFilter的on");
        Subject subject = getSubject(request, response);
        if (null == subject.getPrincipal()) {//表示没有登录，重定向到登录页面
            System.out.println("用户没有登录");
            System.out.println("uri : " + httpRequest.getRequestURI());
            System.out.println("url : " + httpRequest.getRequestURL());
            saveRequest(request);
            WebUtils.issueRedirect(request, response, "/loginPage");//去访问controller跳转登录页面
        } else {//没有权限跳转到未授权页面
            WebUtils.issueRedirect(request, response, "/unauthPage");//如果有未授权页面跳转过去
        }
        return false;
    }
}
