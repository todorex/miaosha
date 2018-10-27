package com.todorex.miaosha.service.impl;

import com.todorex.miaosha.dao.OrderDao;
import com.todorex.miaosha.domain.MiaoShaOrder;
import com.todorex.miaosha.domain.MiaoShaUser;
import com.todorex.miaosha.domain.OrderInfo;
import com.todorex.miaosha.service.OrderService;
import com.todorex.miaosha.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author rex
 * 2018/10/26
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public MiaoShaOrder getMiaoShaOrderByUserIdAndProductId(Long userId, long productId) {

        return orderDao.getMiaoShaOrderByUserIdAndproductId(userId, productId);
    }

    @Override
    @Transactional
    public OrderInfo createOrder(MiaoShaUser user, ProductVo product) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddressId(0L);
        orderInfo.setProductCount(1);
        orderInfo.setProductId(product.getId());
        orderInfo.setProductName(product.getProductName());
        orderInfo.setProductPrice(product.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        long orderId = orderDao.insert(orderInfo);
        MiaoShaOrder miaoshaOrder = new MiaoShaOrder();
        miaoshaOrder.setProductId(product.getId());
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setUserId(user.getId());
        orderDao.insertMiaoshaOrder(miaoshaOrder);
        return orderInfo;
    }
}
