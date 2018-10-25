package com.todorex.miaosha.redis;

/**
 * @Author rex
 * 2018/10/25
 */
public class OrderKey extends BasePrefix {

    public OrderKey(String prefix) {
        super(prefix);
    }

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

}
