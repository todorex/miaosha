package com.todorex.miaosha.service;

import com.todorex.miaosha.domain.MiaoShaUser;
import com.todorex.miaosha.domain.OrderInfo;
import com.todorex.miaosha.vo.ProductVo;

import java.awt.image.BufferedImage;

/**
 * @Author rex
 * 2018/10/26
 */
public interface MiaoShaService {

    /**
     * 秒杀
     * @param user
     * @param product
     * @return
     */
    OrderInfo miaosha(MiaoShaUser user, ProductVo product);

    /**
     * 根据用户ID和商品ID
     * 查看秒杀结果
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     * @param id
     * @param productId
     * @return
     */
    long getMiaoShaResult(Long id, long productId);

    /**
     * 查看商品是否已经卖完
     * @param productId
     * @return
     */
    boolean getProductOver(long productId);


    /**
     * 设置商品已经卖完
     * @param productId
     */
    void setProductOver(Long productId);


    /**
     * 检查秒杀路径是否正确
     * @param user
     * @param productId
     * @param path
     * @return
     */
    boolean checkPath(MiaoShaUser user, long productId, String path);


    /**
     * 创建秒杀路径
     * @param user
     * @param productId
     * @return
     */
    String createMiaoshaPath(MiaoShaUser user, long productId);

    /**
     * 创建验证码图片
     * @param user
     * @param productId
     * @return
     */
    BufferedImage createVerifyCode(MiaoShaUser user, long productId);

    /**
     * 检查验证码的值
     * @param user
     * @param productId
     * @param verifyCode
     * @return
     */
    boolean checkVerifyCode(MiaoShaUser user, long productId, int verifyCode);
}
