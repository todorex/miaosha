package com.todorex.miaosha.redis;

/**
 * @Author rex
 * 2018/10/28
 */
public class MiaoShaKey extends  BasePrefix{



    private MiaoShaKey(String prefix) {
        super(prefix);
    }

    public MiaoShaKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 商品卖完标志前缀
     */
    public static MiaoShaKey isProductOver = new MiaoShaKey("po");

    /**
     * 秒杀路径前缀
     */

    public static MiaoShaKey getMiaoShaPath = new MiaoShaKey(60, "mp");

    /**
     * 验证码前缀
     */
    public static MiaoShaKey getMiaoShaVerifyCode = new MiaoShaKey(300, "vc");
}
