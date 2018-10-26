package com.todorex.miaosha.controller;

import com.todorex.miaosha.domain.MiaoShaUser;
import com.todorex.miaosha.service.MiaoShaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author rex
 * 2018/10/26
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private MiaoShaUserService miaoShaUserService;

    @RequestMapping("list")
    public String productList(Model model, MiaoShaUser user) {
        model.addAttribute("user", user);
        return "product_list";
    }

}
