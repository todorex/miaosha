package com.todorex.miaosha.service.impl;

import com.todorex.miaosha.domain.MiaoShaUser;
import com.todorex.miaosha.domain.OrderInfo;
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

    @Override
    @Transactional
    public OrderInfo miaosha(MiaoShaUser user, ProductVo product) {
        //减库存 下订单 写入秒杀订单
        productService.reduceStock(product);
        //order_info maiosha_order
        return orderService.createOrder(user,product);
    }
}
