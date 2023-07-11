package com.yr.springbootshiro.controller;

import com.yr.springbootshiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ShiroController {

    @Autowired
    private UserService userService;

//    @RequiresRoles("list")
    @ResponseBody
    @RequestMapping("/list")
    public String testList(){
        System.out.println("list...");
        return "success";
    }

    @ResponseBody
    @RequestMapping("/testAll")
    public String testAll(){
        userService.getId("abc");
        return "success";
    }

    //登录跳转
    @RequestMapping("/loginPage")
    public String loginPage(){
        System.out.println("进入登录跳转");
        return "shiro-login";
    }

    //未授权页面跳转
    @RequestMapping("/unauthPage")
    public String unauthPage(){
        return "shiro-unauthorized";
    }

    //成功页面跳转
    @RequestMapping("/successPage")
    public String successPage(){
        return "shiro-success";
    }

    //user页面跳转
    @RequestMapping("/user")
    public String user(){
        return "user";
    }

    //admin页面跳转
    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }

    @RequestMapping("/shiro-login")
    public String login(@RequestParam(value = "username",required = false) String username,
                        @RequestParam(value = "password",required = false) String password){
        try {
            // 获取当前执行的用户
            Subject subject = SecurityUtils.getSubject();
            //使用UsernamePasswordToken 来封装用户名和密码
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            //执行认证操作，也就是登录
            subject.login(usernamePasswordToken);
        } catch (AuthenticationException ae) {//认证异常
            System.out.println("登录失败： " + ae.getMessage());
            return "shiro-login";
        } catch (Exception e){
            return "shiro-login";
        }
        return "shiro-success";
    }
}
