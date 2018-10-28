package com.todorex.miaosha.service;

import com.todorex.miaosha.domain.MiaoShaUser;
import com.todorex.miaosha.domain.OrderInfo;
import com.todorex.miaosha.vo.ProductVo;

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
}
