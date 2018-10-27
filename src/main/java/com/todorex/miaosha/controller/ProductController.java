package com.todorex.miaosha.controller;

import com.todorex.miaosha.domain.MiaoShaUser;
import com.todorex.miaosha.redis.ProductKey;
import com.todorex.miaosha.redis.RedisService;
import com.todorex.miaosha.service.MiaoShaUserService;
import com.todorex.miaosha.service.ProductService;
import com.todorex.miaosha.vo.ProductVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author rex
 * 2018/10/26
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private MiaoShaUserService miaoShaUserService;

    @Autowired
    private ProductService productService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;



    @RequestMapping(value = "list", produces = "text/html")
    @ResponseBody
    public String productList(HttpServletRequest request, HttpServletResponse response, Model model, MiaoShaUser user) {
        model.addAttribute("user", user);

        String html = redisService.get(ProductKey.getProductList, "",  String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        // 查询商品列表
        List<ProductVo> productVoList = productService.listProductVo();
        model.addAttribute("productVoList", productVoList);

        WebContext context = new WebContext(request,response,
                request.getServletContext(),request.getLocale(), model.asMap());
        // 如果为空，手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("product_list", context);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(ProductKey.getProductList, "", html);
        }
        return html;
    }


    @RequestMapping("/detail/{productId}", produces = "text/html")
    @ResponseBody
    public String detail(HttpServletRequest request,
                         HttpServletResponse response,
                         Model model,MiaoShaUser user,
                         @PathVariable("productId")long productId) {
        model.addAttribute("user", user);

        //取缓存
        String html = redisService.get(ProductKey.getProductDetail, ""+ productId, String.class);
        if(!StringUtils.isEmpty(html)) {
            return html;
        }
        //手动渲染
        ProductVo goods = productService.getProductVoByProductId(productId);
        model.addAttribute("goods", goods);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        // 秒杀状态
        int miaoshaStatus = 0;
        // 秒杀剩余时间
        int remainSeconds = 0;
        if(now < startAt ) {
            //秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now )/1000);
        }else  if(now > endAt){
            //秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {
            //秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);


        WebContext context = new WebContext(request,response,
                request.getServletContext(),request.getLocale(), model.asMap());

        html = thymeleafViewResolver.getTemplateEngine().process("product_detail", context);
        if(!StringUtils.isEmpty(html)) {
            redisService.set(ProductKey.getProductDetail, ""+ productId, html);
        }
        return html;
    }
}
