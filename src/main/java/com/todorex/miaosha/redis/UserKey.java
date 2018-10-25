package com.todorex.miaosha.redis;

import com.todorex.miaosha.domain.User;

/**
 * @Author rex
 * 2018/10/25
 */
public class UserKey extends BasePrefix {

    private UserKey(String prefix) {
        super(prefix);
    }
    // 规定几种key
    /**
     * ID KEY
     */
    public static UserKey getById = new UserKey("id");

    /**
     * NAME KEY
     */
    public static UserKey getByName = new UserKey("name");



}
