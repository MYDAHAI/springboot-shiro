package com.yr.springbootshiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class PermissionFilter extends AccessControlFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse,
                                      Object mappedValue) throws Exception {
        System.out.println("进入PermissionFilter的is");
        Subject subject = SecurityUtils.getSubject();//得到当前用户对象
        System.out.println(subject.getPrincipal());//得到用户信息
        if (subject.getPrincipal() == null) {
            return false;
        }

        String[] permissions = (String[]) mappedValue;//用户所有角色和权限
        System.out.println("用户所有角色和权限: " + permissions);

        if (permissions != null){
            for (String permission : permissions){
                if (subject.isPermitted(permission))
                    return true;
            }
        }

        HttpServletRequest httpRequest = ((HttpServletRequest)servletRequest);


        //获取URI : /springmvc-shiro/list
        //获取basePath : /springmvc-shiro
        //替换后的URI : /list
        String uri = httpRequest.getRequestURI();//获取URI
        System.err.println("获取URI : " + uri);
        String basePath = httpRequest.getContextPath();//获取basePath
        System.err.println("获取basePath : " + basePath);

        if(null != uri && uri.startsWith(basePath)){//startsWith  测试该uri是否是以basePath作为前缀
            uri = uri.replaceFirst(basePath, "");//把uri中的basePath替换成""
        }

        System.err.println("替换后的URI : " + uri);
        if(subject.isPermitted(uri)){//判断是否拥有当前路径的权限
            System.out.println("用户拥有当前权限");
            return Boolean.TRUE;
        }


        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        System.out.println("进入PermissionFilter的on");
        Subject subject = getSubject(request, response);
        if (null == subject.getPrincipal()) {//表示没有登录，重定向到登录页面
            saveRequest(request);
            WebUtils.issueRedirect(request, response, "/loginPage");//去访问controller跳转登录页面
        } else {//没有权限跳转到未授权页面
            WebUtils.issueRedirect(request, response, "/unauthPage");//如果有未授权页面跳转过去
        }
        return false;
    }
}
