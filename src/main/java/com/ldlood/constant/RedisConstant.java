package com.ldlood.constant;

/**
 * redis常量
 * Created by Ldlood on 2017/8/13.
 */
public interface RedisConstant {

    String TOKEN_PREFIX = "token_%s";

    Integer EXPIRE = 7200; //过期时间 2小时
}
