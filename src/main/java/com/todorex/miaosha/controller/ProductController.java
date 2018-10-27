package com.todorex.miaosha.controller;

import com.todorex.miaosha.domain.MiaoShaUser;
import com.todorex.miaosha.service.MiaoShaUserService;
import com.todorex.miaosha.service.ProductService;
import com.todorex.miaosha.vo.ProductVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping("list")
    public String productList(Model model, MiaoShaUser user) {
        model.addAttribute("user", user);
        // 查询商品列表
        List<ProductVo> productVoList = productService.listProductVo();
        model.addAttribute("productVoList", productVoList);
        return "product_list";
    }


    @RequestMapping("/detail/{productId}")
    public String detail(Model model,MiaoShaUser user,
                         @PathVariable("productId")long productId) {
        model.addAttribute("user", user);

        ProductVo productVo = productService.getProductVoByProductId(productId);
        model.addAttribute("product", productVo);

        long startAt = productVo.getStartDate().getTime();
        long endAt = productVo.getEndDate().getTime();
        long now = System.currentTimeMillis();

        // 秒杀状态
        int miaoshaStatus = 0;
        // 还有多少秒开始
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
        return "product_detail";
    }
}
