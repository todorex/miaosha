package com.todorex.miaosha.redis;

/**
 * @Author rex
 * 2018/10/26
 */
public class MiaoshaUserKey extends BasePrefix {
    /**
     * 过期时间
     * 1天
     */
    public static final int TOKEN_EXPIRE = 3600 * 24;

    private MiaoshaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE, "token");

    /**
     * 对象缓存不设置有效期
     * 希望它是永久有效的（只要没有发生变化）
     */
    public static MiaoshaUserKey getById = new MiaoshaUserKey(0, "id");
}
