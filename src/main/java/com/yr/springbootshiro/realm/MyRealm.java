package com.yr.springbootshiro.realm;

import com.yr.springbootshiro.entity.UUser;
import com.yr.springbootshiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权方法
     * 1.实际返回的是SimpleAuthorizationInfo实例
     * 2.可以调用SimpleAuthorizationInfo的addRole方法来添加当前登录的user的权限信息
     * 3.可以调用PrincipalCollection的getPrimaryPrincipal()方法 来获取用户信息
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println(4444);

        //AuthorizationInfo的子类
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        //获取用户信息
        Object principal = principals.getPrimaryPrincipal();
        System.out.println("-> principal : " + principal);



        if ("admin".equals(principal)){
            authorizationInfo.addRole("admin");//添加admin角色
            List<String> adminList = userService.getEmail("admin");
            authorizationInfo.addStringPermissions(adminList);//添加admin 权限
        }

        if ("list".equals(principal)){
            authorizationInfo.addRole("list");//添加list角色
            List<String> List = userService.getEmail("list");
            authorizationInfo.addStringPermissions(List);//添加list 权限
        }

        authorizationInfo.addRole("user");//添加基础用户角色
        List<String> list = userService.getEmail((String) principal);
//        System.out.println("权限列表: " + list);
        authorizationInfo.addStringPermissions(list);//添加权限


        return authorizationInfo;//返回AuthorizationInfo的子类SimpleAuthorizationInfo
    }

    /**
     * 认证方法
     *你form表单提交到controller后获取用户名和密码
     *Subject subject = SecurityUtils.getSubject();
     *UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
     * 执行认证操作，也就是登录
     * subject.login(usernamePasswordToken);
     *
     * 当subject调用login方法时，就会过来执行这个doGetAuthenticationInfo 方法
     * 而且会把usernamePasswordToken 对象传入，然后进行真正的认证： 和数据库进行对比
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        System.out.println("getPrincipal :  " + token.getPrincipal());
//        //getPrincipal的值和getPrincipal().toString()的值是一样的
//        System.out.println("getPrincipal.toString :  " + token.getPrincipal().toString());
//        System.out.println("getCredentials :  " + token.getCredentials());
//        System.out.println("getCredentials.toString :  " + token.getCredentials().toString());
//        System.out.println("getName : " + getName());
        System.out.println(3333);

        //流程：
        //1.从token中获取到登录的username，注意！不需要获取password
        //2.利用username到数据库中查到用户的信息
        //3.创建SimpleAuthenticationInfo对象并返回，注意该对象的凭证是从数据库中查询到的，
        //  而不是从页面输入的，实际的密码效验可以交给shiro来完成

        //4.配置加密: shiro的密码加密可以非常的复杂，但实现起来却非常的简单
        //  可以选择加密方式： 在当前的realm中编写一个public类型的空参数方法，使用@PostConstruct
        //  注解进行修饰，在其中来设置密码的加密方式以及次数



        //登录的主要信息： 可以是实体类的对象，但该实体类的对象一定是根据token 的username查询到的 ,相当于用户名
        Object principal = token.getPrincipal();
        //凭证相当于密码
        //认证的信息： 从数据库查出来的信息，密码的对比交给shiro去进行比较,想当于密码
        //
        //
        //
        // 500cb67b1d9edeaa5afaa8348e9269d5
//        String credentials = "500cb67b1d9edeaa5afaa8348e9269d5";//配置过加密的，查出来就是加密后的密码,123456



        UUser user = userService.getId((String) principal);
        System.out.println("password: " + user.getPswd());

        String credentials = user.getPswd();

//        String credentials = "500cb67b1d9edeaa5afaa8348e9269d5";

        //当前Realm 的 name
        String realmName = getName();

        //设置盐值：盐值一般是从数据库中查出来的，盐值相当于加密的秘钥
        String source = "abc";
        ByteSource credentialsSalt = new Md5Hash(source);
        System.out.println("盐值： " + credentialsSalt);

//        SimpleAuthenticationInfo authenticationInfo =
//                new SimpleAuthenticationInfo(principal, credentials, realmName);

        //用principal和credentials去和token中的用户信息对对比认证
        //principal相当于你数据库的用户名
        //credentials相当于你数据库中的用户密码
        //然后把他们和页面输入的用户名和密码做对比，也就是和token中的用户信息做对比
        SimpleAuthenticationInfo authenticationInfo =
                new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);//交给shiro去认证

        return authenticationInfo;
    }

    // @PostConstruct : 相当于 bean 节点的init-method 配置。
    @PostConstruct
    public void setCredentialMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");//设置hash算法的名称
        credentialsMatcher.setHashIterations(1024);//设置加密次数，迭代数
        setCredentialsMatcher(credentialsMatcher);//设置凭据匹配器
    }


    //加密测试
    public static void main(String[] args) {

        String hashAlgorithmName = "MD5";//加密名称
        String password = "123456";//密码，也叫凭证
        String saltSource = "abc";//盐值
        ByteSource salt = new Md5Hash(saltSource);//最终的盐值
        int hashIterations = 1024;//加密次数

        //返回值是加密之后的密码
        //加密name，密码，盐值，加密次数.
        SimpleHash simpleHash = new SimpleHash(hashAlgorithmName, password, salt, hashIterations);
        String hashPassword = simpleHash.toString();
        System.out.println(hashPassword);
    }
}
