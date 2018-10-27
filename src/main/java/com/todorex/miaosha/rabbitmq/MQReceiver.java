package com.todorex.miaosha.rabbitmq;

import com.todorex.miaosha.domain.MiaoShaUser;
import com.todorex.miaosha.domain.Product;
import com.todorex.miaosha.redis.RedisService;
import com.todorex.miaosha.service.MiaoShaService;
import com.todorex.miaosha.service.OrderService;
import com.todorex.miaosha.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author rex
 * 2018/10/27
 */
@Service
@Slf4j
public class MQReceiver {
    @Autowired
    RedisService redisService;

    @Autowired
    ProductService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoShaService miaoShaService;

    @RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
    public void receive(String message) {
        log.info("receive message:"+message);
        MiaoShaMessage mm  = RedisService.stringToBean(message, MiaoShaMessage.class);
        log.info(mm.toString());
//        MiaoShaUser user = mm.getUser();
//        long goodsId = mm.getGoodsId();
//
//        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//        int stock = goods.getStockCount();
//        if(stock <= 0) {
//            return;
//        }
//        //判断是否已经秒杀到了
//        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
//        if(order != null) {
//            return;
//        }
//        //减库存 下订单 写入秒杀订单
//        miaoshaService.miaosha(user, goods);
    }
}
