package com.yr.springbootshiro.config;

import com.yr.springbootshiro.filter.InitFilter;
import com.yr.springbootshiro.filter.LoginFilter;
import com.yr.springbootshiro.filter.PermissionFilter;
import com.yr.springbootshiro.realm.MyRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import javax.servlet.Filter;

@Configuration
public class ShiroConfig {

    /**
     * 配置shiro拦截器
     *
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager());

        //权限map
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("/unauthPage", "anon");
        hashMap.put("/successPage", "anon");
        hashMap.put("/loginPage", "anon");
        hashMap.put("/testAll", "anon");
        hashMap.put("/logout", "logout");//登出
        hashMap.put("/shiro-*", "anon");//匿名
        hashMap.put("/admin", "roles[admin]");//指定角色访问权限
        hashMap.put("/**", "authc");//所有url需认证登录后才能访问


        //添加ini中的权限
        shiroFilterFactoryBean.setFilterChainDefinitions(initFilter().loadFilterChainDefinitions());

        //自定义权限map
        HashMap<String, Filter> filterHashMap = new HashMap<>();
//        filterHashMap.put("login", loginFilter());
        //login放在/**中出现循环重定向问题解决办法
        //把login过滤器脱离ioc容器，直接new即可解决问题
        filterHashMap.put("login", new LoginFilter());
        filterHashMap.put("permission",permissionFilter());





        //freemarker版本
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthPage");//指定授权失败页面
        shiroFilterFactoryBean.setSuccessUrl("/successPage");//指定认证成功页面
        shiroFilterFactoryBean.setLoginUrl("/loginPage");//指定登录页面

        //jsp版本
//         shiroFilterFactoryBean.setUnauthorizedUrl("/shiro-unauthorized.jsp");//指定授权失败页面
//         shiroFilterFactoryBean.setSuccessUrl("/shiro-success.jsp");//指定认证成功页面
//         shiroFilterFactoryBean.setLoginUrl("/shiro-login.jsp");//指定登录页面

        //map版本
//         shiroFilterFactoryBean.setFilterChainDefinitionMap(hashMap);//添加要拦截的url

        //自定义版本
        shiroFilterFactoryBean.setFilters(filterHashMap);//添加自定义过滤器

        return shiroFilterFactoryBean;
    }

    /**
     * 自定义 Realm
     *
     * @return
     */
    @Bean
    public MyRealm myRealm() {
        MyRealm myRealm = new MyRealm();
        return myRealm;
    }

    /**
     * 配置安全管理器
     *
     * @return
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm());
        return securityManager;
    }

    /**
     * ini权限
     *
     * @return
     */
    @Bean
    public InitFilter initFilter() {
        InitFilter initFilter = new InitFilter();
        return initFilter;
    }

    /**
     * login过滤器
     * @return
     */
//    @Bean
//    public LoginFilter loginFilter(){
//        LoginFilter loginFilter = new LoginFilter();
//        return loginFilter;
//    }

    /**
     * 权限过滤器
     * @return
     */
    @Bean
    public PermissionFilter permissionFilter(){
        PermissionFilter permissionFilter = new PermissionFilter();
        return permissionFilter;
    }




    /**
     * 配置shiro注解生效
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 配置shiro注解生效
     *
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager());
        return authorizationAttributeSourceAdvisor;
    }


}
