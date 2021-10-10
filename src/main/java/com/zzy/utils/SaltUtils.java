package com.zzy.utils;

import java.util.Random;
import java.util.UUID;

//基于UUID的随机盐
public class SaltUtils {
    public static  String getSalt(int n){
        //基于uuid
        UUID uuid = UUID.randomUUID();
        char[] chars = uuid.toString().toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        //每次随机一个幸运id加入字符数组
        for (int i = 0; i < n; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            stringBuilder.append(aChar);
        }
        //返回构造结束的盐
        return stringBuilder.toString();
    }
}
