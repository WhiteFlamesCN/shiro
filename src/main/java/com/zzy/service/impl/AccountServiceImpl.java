package com.zzy.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.entity.Account;
import com.zzy.mapper.AccountMapper;
import com.zzy.service.AccountService;
import com.zzy.utils.SaltUtils;
import lombok.val;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    //服务：找到账号
    @Override
    public Account findByUsername(String username) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",username);
        return accountMapper.selectOne(wrapper);
    }

    //服务：注册
    @Override
    public void register(String username, String password) {
        //生成盐
        String salt = SaltUtils.getSalt(8);
        //利用盐进行hash+md5+salt加密
        Md5Hash md5Hash = new Md5Hash(password,salt,1024);
        Account account = new Account();
        //设置账户信息（其实应该弄到构造方法里面的，但是只用一次，就偷懒了）
        account.setPassword(md5Hash.toHex());
        account.setPerms("");
        account.setRole("user");
        account.setUsername(username);
        account.setSalt(salt);
        //放进数据库
        accountMapper.insert(account);

    }


}
