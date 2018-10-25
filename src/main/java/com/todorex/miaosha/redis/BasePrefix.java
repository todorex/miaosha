package com.todorex.miaosha.redis;

/**
 * 规范前缀
 * 不同模块的key前缀不同，不同模块不同实现类
 * @Author rex
 * 2018/10/25
 */
public abstract class BasePrefix implements KeyPrefix{
    /**
     * 过期时间
     * 0代表永不过期
     */
    private int expireSeconds;

    /**
     * key的前缀
     */
    private String prefix;

    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    public BasePrefix( int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        // 拿到类名
        String className = getClass().getSimpleName();
        return className+":" + prefix;
    }
}
