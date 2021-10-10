package com.zzy.config;

import com.zzy.realm.AccountRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Hashtable;
import java.util.Map;


@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        //将securitymanager放入factory
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean() ;
        factoryBean.setSecurityManager(defaultWebSecurityManager);

        //权限设置:
        //将登录成功页面分角色进行拦截
        Map<String,String> map= new Hashtable<>();
        map.put("/login_fail","authc");
        map.put("/login_success_user","roles[user]");
        map.put("/login_success_super","roles[super]");
        map.put("/login_fail.html","authc");
        map.put("/login_success_user.html","roles[user]");
        map.put("/login_success_super.html","roles[super]");

        //把成功页面放进去
        factoryBean.setFilterChainDefinitionMap(map);

        //设置登录页面
        factoryBean.setLoginUrl("/login");
        return  factoryBean;
    }

    //将realm放入manager
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("accountRealm") AccountRealm accountRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(accountRealm);
        return  manager;
    }

    //生成一个accountRealm
    @Bean
    public AccountRealm accountRealm(){
            return  new AccountRealm();
    }
}
