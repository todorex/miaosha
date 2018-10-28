package com.todorex.miaosha.redis;

/**
 * @Author rex
 * 2018/10/28
 */
public class MiaoShaKey extends  BasePrefix{

    private MiaoShaKey(String prefix) {
        super(prefix);
    }
    public static MiaoShaKey isProductOver = new MiaoShaKey("po");
}
