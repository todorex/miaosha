package com.todorex.miaosha.service.impl;

import com.todorex.miaosha.domain.MiaoShaOrder;
import com.todorex.miaosha.domain.MiaoShaUser;
import com.todorex.miaosha.domain.OrderInfo;
import com.todorex.miaosha.redis.MiaoShaKey;
import com.todorex.miaosha.redis.RedisService;
import com.todorex.miaosha.service.MiaoShaService;
import com.todorex.miaosha.service.OrderService;
import com.todorex.miaosha.service.ProductService;
import com.todorex.miaosha.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author rex
 * 2018/10/26
 */
@Service
public class MiaoShaServiceImpl implements MiaoShaService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;

    @Override
    @Transactional
    public OrderInfo miaosha(MiaoShaUser user, ProductVo product) {
        //减库存
        boolean success = productService.reduceStock(product);
        if(success) {
            // 下订单 写入秒杀订单
            return orderService.createOrder(user, product);
        } else {
            // 商品已经被秒杀完
            setProductOver(product.getId());
            return null;
        }
    }

    @Override
    public long getMiaoShaResult(Long userId, long productId) {
        MiaoShaOrder order = orderService.getMiaoShaOrderByUserIdAndProductId(userId, productId);
        if(order != null) {
            //秒杀成功
            return order.getOrderId();
        }else {
            boolean isOver = getProductOver(productId);
            if(isOver) {
                return -1;
            }else {
                return 0;
            }
        }
    }

    private boolean getProductOver(long productId) {
        return redisService.exists(MiaoShaKey.isProductOver, "" + productId);
    }

    public void setProductOver(Long productId) {
        redisService.set(MiaoShaKey.isProductOver, "" + productId, true);
    }
}
