package com.todorex.miaosha.redis;

/**
 * @Author rex
 * 2018/10/28
 */
public class AccessKey extends BasePrefix{

    private AccessKey( int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 过期时间外面定
     * @param expireSeconds 过期时间
     * @return
     */
    public static AccessKey withExpire(int expireSeconds) {
        return new AccessKey(expireSeconds, "access");
    }

}
