package com.zzy.realm;

import com.zzy.entity.Account;
import com.zzy.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class AccountRealm extends AuthorizingRealm {
    @Autowired
    private AccountService accountService;
    //授权逻辑
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Subject subject = SecurityUtils.getSubject();
        Account account = (Account) subject.getPrincipal();
        //设置角色
        Set<String> roles = new HashSet<>();
        roles.add(account.getRole());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        //设置权限
        info.addStringPermission(account.getPerms());
        return info;
    }

    //认证逻辑
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //从数据库中拿到正确账号信息
        Account account = accountService.findByUsername(token.getUsername());
        //利用salt和密码进行Hash+Md5+salt映射
        Md5Hash md5Hash = new Md5Hash(token.getPassword(),account.getSalt(),1024);
        //将password更改成加密过后的字符数组
        token.setPassword(md5Hash.toHex().toCharArray());
        if (account!=null){
            //将两个密码进行比对
            return new SimpleAuthenticationInfo(account,account.getPassword(),getName());
        }
        return null;
    }
}
