package com.zzy.controller;

import com.zzy.entity.Account;
import com.zzy.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccountController {

    //注入服务层
    @Autowired
    private AccountService accountService;

    //处理所有get请求到对应的html
    @GetMapping("/{url}")
    public String redirect(@PathVariable("url") String url){
        return url;
    }

    //注册页面把表单传过来后，执行服务层注册功能，然后重定向到登录页面
    @PostMapping("/register")
    public String register(String username,String password){
        accountService.register(username,password);
        return "redirect:login";
    }

    //处理登录表单传过来的信息，并封装到token交由shiro处理，接受返回的异常/结果
    @PostMapping("/login")
    public String login(String username, String password, Model model){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
            subject.login(token);
            //根据不同的角色跳转到不同的页面
            if (subject.hasRole("super")){
                return "redirect:login_success_super";//登录成功后跳转到成功页面
            }else{
                return "redirect:login_success_user";//登录成功后跳转到成功页面
            }
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            return "login_fail";//账号错误
        } catch (IncorrectCredentialsException e){
            e.printStackTrace();
            return  "login_fail";//密码错误
        }
    }
}
