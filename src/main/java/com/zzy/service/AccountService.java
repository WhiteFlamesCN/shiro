package com.zzy.service;

import com.zzy.entity.Account;
//服务层的接口
public interface AccountService {
    public Account findByUsername(String username);
    public void register(String username,String password);
}
