package com.todorex.miaosha.rabbitmq;

import com.todorex.miaosha.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author rex
 * 2018/10/27
 */
@Service
@Slf4j
public class MQSender {
    @Autowired
    AmqpTemplate amqpTemplate ;

    public void sendMiaoshaMessage(MiaoShaMessage mm) {
        String msg = RedisService.beanToString(mm);
        log.info("send message:" + msg);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, msg);
    }
}
