package com.mymc.shirodemo.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

        /*
        * Subjeck 用户
        * SecurityManager 管理用户
        * Realm 连接数据
        * */

        //ShiroFilterFactoryBean
        @Bean
        public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
            ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
            //设置安全管理器
            bean.setSecurityManager(defaultWebSecurityManager);

            //添加shiro的内置过滤器
            /*
            * anon:无需认证就可以访问
            * authc:必须认证了才能访问
            * user:必须拥有记住我功能才能用
            * perms:拥有某个资源的权限才能访问
            * role:拥有某个角色权限才能访问
            * */
            //拦截
            Map<String, String> filterMap = new LinkedHashMap<>();

//            filterMap.put("/user/add","anon");
//            filterMap.put("/user/update","authc");
            //授权，正常的情况下，没有授权会跳转到未授权页面
            //在上面的优先级越大
            filterMap.put("/user/add", "perms[user:add]");
            filterMap.put("/user/add", "perms[user:update]");
            filterMap.put("/user/*","authc");

            bean.setFilterChainDefinitionMap(filterMap);

            bean.setUnauthorizedUrl("/noauth");

            //设置登录的请求
            bean.setLoginUrl("/toLogin");



            return bean;
        }

        //DefaultWebSecurityManager
        @Bean(name = "securityManager")
        public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
            DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
            //关联UserRealm
            securityManager.setRealm(userRealm);
            return securityManager;
        }


        //创建Realm对象，需要自定义类
        @Bean(name="userRealm")
        public UserRealm userRealm(){
            return new UserRealm();
        }

        //整合ShiroDialect:用来整合shirothymeleaf

        @Bean
        public ShiroDialect getShiroDialect(){
            return new ShiroDialect();
        }


}
