package com.todorex.miaosha.service;

import com.todorex.miaosha.domain.MiaoShaOrder;
import com.todorex.miaosha.domain.MiaoShaUser;
import com.todorex.miaosha.domain.OrderInfo;
import com.todorex.miaosha.vo.ProductVo;

/**
 * @Author rex
 * 2018/10/26
 */
public interface OrderService {

    /**
     * 根据用户ID和商品ID获取秒杀订单
     * @param id
     * @param productId
     * @return
     */
    public MiaoShaOrder getMiaoShaOrderByUserIdAndProductId(Long id, long productId);

    /**
     * 创建订单
     * @param user
     * @param product
     * @return
     */
    public OrderInfo createOrder(MiaoShaUser user, ProductVo product);
}
