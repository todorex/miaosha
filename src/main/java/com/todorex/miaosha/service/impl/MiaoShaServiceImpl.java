package com.todorex.miaosha.service.impl;

import com.todorex.miaosha.domain.MiaoShaOrder;
import com.todorex.miaosha.domain.MiaoShaUser;
import com.todorex.miaosha.domain.OrderInfo;
import com.todorex.miaosha.redis.MiaoShaKey;
import com.todorex.miaosha.redis.RedisService;
import com.todorex.miaosha.service.MiaoShaService;
import com.todorex.miaosha.service.OrderService;
import com.todorex.miaosha.service.ProductService;
import com.todorex.miaosha.util.MD5Util;
import com.todorex.miaosha.util.UUIDUtil;
import com.todorex.miaosha.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

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
        if (success) {
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
        if (order != null) {
            //秒杀成功
            return order.getOrderId();
        } else {
            boolean isOver = getProductOver(productId);
            if (isOver) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public boolean getProductOver(long productId) {
        return redisService.exists(MiaoShaKey.isProductOver, "" + productId);
    }

    @Override
    public void setProductOver(Long productId) {
        redisService.set(MiaoShaKey.isProductOver, "" + productId, true);
    }

    @Override
    public boolean checkPath(MiaoShaUser user, long productId, String path) {
        if (user == null || path == null) {
            return false;
        }
        String pathOld = redisService.get(MiaoShaKey.getMiaoShaPath, "" + user.getId() + "_" + productId, String.class);
        return path.equals(pathOld);
    }

    @Override
    public String createMiaoshaPath(MiaoShaUser user, long productId) {
        if (user == null || productId <= 0) {
            return null;
        }
        String str = MD5Util.md5(UUIDUtil.uuid() + "123456");
        redisService.set(MiaoShaKey.getMiaoShaPath, "" + user.getId() + "_" + productId, str);
        return str;
    }

    @Override
    public BufferedImage createVerifyCode(MiaoShaUser user, long productId) {
        if (user == null || productId <= 0) {
            return null;
        }
        int width = 80;
        int height = 32;
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        //把验证码存到redis中
        int rnd = calc(verifyCode);
        redisService.set(MiaoShaKey.getMiaoShaVerifyCode, user.getId() + "," + productId, rnd);
        //输出图片
        return image;
    }

    @Override
    public boolean checkVerifyCode(MiaoShaUser user, long productId, int verifyCode) {
        if (user == null || productId <= 0) {
            return false;
        }
        Integer codeOld = redisService.get(MiaoShaKey.getMiaoShaVerifyCode, user.getId() + "," + productId, Integer.class);
        if (codeOld == null || codeOld - verifyCode != 0) {
            return false;
        }
        // 用完就删掉
        redisService.delete(MiaoShaKey.getMiaoShaVerifyCode, user.getId() + "," + productId);
        return true;
    }

    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            // 使用JavaScript引擎来计算表达式
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer) engine.eval(exp);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static char[] ops = new char[]{'+', '-', '*'};

    /**
     * + - *
     */
    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = "" + num1 + op1 + num2 + op2 + num3;
        return exp;
    }
}
