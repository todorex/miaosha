package com.todorex.miaosha.rabbitmq;

import com.todorex.miaosha.domain.MiaoShaOrder;
import com.todorex.miaosha.domain.MiaoShaUser;
import com.todorex.miaosha.domain.Product;
import com.todorex.miaosha.redis.RedisService;
import com.todorex.miaosha.service.MiaoShaService;
import com.todorex.miaosha.service.OrderService;
import com.todorex.miaosha.service.ProductService;
import com.todorex.miaosha.vo.ProductVo;
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
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoShaService miaoShaService;

    @RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
    public void receive(String message) {
        log.info("receive message:"+message);
        // 请求出队
        MiaoShaMessage mm  = RedisService.stringToBean(message, MiaoShaMessage.class);
        log.info(mm.toString());
        MiaoShaUser user = mm.getUser();
        long productId = mm.getProductId();

        ProductVo product = productService.getProductVoByProductId(productId);
        int stock = product.getStockCount();
        if(stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        MiaoShaOrder order = orderService.getMiaoShaOrderByUserIdAndProductId(user.getId(), productId);
        if(order != null) {
            return;
        }
        // 4. 减库存 下订单 写入秒杀订单
        miaoShaService.miaosha(user, product);
    }
}
