package com.todorex.miaosha.redis;

/**
 * @Author rex
 * 2018/10/27
 */
public class ProductKey extends BasePrefix{
    public ProductKey(String prefix) {
        super(prefix);
    }

    public ProductKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 页面缓存一般有效期比较短
     * 1分钟
     */
    public static ProductKey getProductList = new ProductKey(60, "pl");

    public static ProductKey getProductDetail = new ProductKey(60, "pd");

    public static ProductKey getMiaoShaProductStock= new ProductKey(0, "ps");
}
