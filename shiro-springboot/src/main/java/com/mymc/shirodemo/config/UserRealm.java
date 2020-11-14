package com.mymc.shirodemo.config;

import com.mymc.shirodemo.pojo.User;
import com.mymc.shirodemo.service.UserService;
import com.sun.javaws.IconUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

//自定义的UserRealm
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>授权");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("user:add");

        //拿到当前登录的这个对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser  = (User) subject.getSession(); //拿到User对象

        //设置当前用户的权限
        info.addStringPermission(currentUser.getSalt()); //currentUser.getSalt()拿到数据库中用户记录的权限

        return info;
    }


    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证");

//        String name = "root";
//        String password = "123456";
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        User user = userService.queryUserByName(userToken.getUsername());

//        if (!userToken.getUsername().equals(name)) {
//            return null; //抛出异常 UnknownAccountException
//        }

        //用于在前端判断是否登录
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("loginUser",user);

        if (user == null) { //没有这个人
            return null; //UnknownAccountException
        }


        //密码认证
//        return new SimpleAuthenticationInfo("",user.getPassword(),"");
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
