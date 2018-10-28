package com.todorex.miaosha.access;

import com.todorex.miaosha.domain.MiaoShaUser;

/**
 * @Author rex
 * 2018/10/28
 */
public class UserContext {

    private static ThreadLocal<MiaoShaUser> userHolder = new ThreadLocal<MiaoShaUser>();

    public static void setUser(MiaoShaUser user) {
        userHolder.set(user);
    }

    public static MiaoShaUser getUser() {
        return userHolder.get();
    }
}
