package com.todorex.miaosha.controller;

import com.todorex.miaosha.domain.MiaoShaOrder;
import com.todorex.miaosha.domain.MiaoShaUser;
import com.todorex.miaosha.domain.OrderInfo;
import com.todorex.miaosha.result.CodeMsg;
import com.todorex.miaosha.service.MiaoShaService;
import com.todorex.miaosha.service.OrderService;
import com.todorex.miaosha.service.ProductService;
import com.todorex.miaosha.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author rex
 * 2018/10/26
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoShaController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoShaService miaoShaService;



    @PostMapping
    public String miaosha(Model model, MiaoShaUser user, @RequestParam("productId") long productId) {
        model.addAttribute("user", user);
        if(user == null) {
            return "login";
        }
        //判断库存
        ProductVo product = productService.getProductVoByProductId(productId);
        int stock = product.getStockCount();
        if(stock <= 0) {
            model.addAttribute("errMsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_failure";
        }
        //判断是否已经秒杀到了
        MiaoShaOrder order = orderService.getMiaoShaOrderByUserIdAndProductId(user.getId(), productId);
        if(order != null) {
            model.addAttribute("errMsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_failure";
        }
        //减库存 下订单 写入秒杀订单 做成一个事务
        OrderInfo orderInfo = miaoShaService.miaosha(user, product);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("product", product);
        return "order_detail";
    }
}
