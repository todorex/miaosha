package com.todorex.miaosha.controller;

import com.todorex.miaosha.domain.MiaoShaUser;
import com.todorex.miaosha.rabbitmq.MQSender;
import com.todorex.miaosha.rabbitmq.MiaoShaMessage;
import com.todorex.miaosha.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author rex
 * 2018/10/27
 */
@Controller
public class DemoController {

    @Autowired
    private MQSender mqSender;

    @RequestMapping("/mq")
    @ResponseBody
    public void mq() {
        mqSender.sendMiaoshaMessage(new MiaoShaMessage(new MiaoShaUser(), 10L));
    }
}
