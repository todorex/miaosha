package com.todorex.miaosha.redis;

/**
 * @Author rex
 * 2018/10/25
 */
public interface KeyPrefix {
    public int expireSeconds();

    public String getPrefix();
}
