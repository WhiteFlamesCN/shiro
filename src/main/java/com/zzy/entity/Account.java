package com.zzy.entity;

import lombok.Data;

@Data
public class Account {
    //数据库的实体类
    private  int id;
    private  String username;
    private  String password;
    private  String perms;
    private  String role;
    private  String salt;
}
