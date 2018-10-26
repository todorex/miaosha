package com.todorex.miaosha.controller;

import com.todorex.miaosha.domain.MiaoShaUser;
import com.todorex.miaosha.result.CodeMsg;
import com.todorex.miaosha.result.Result;
import com.todorex.miaosha.service.MiaoShaUserService;
import com.todorex.miaosha.util.ValidatorUtil;
import com.todorex.miaosha.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author rex
 * 2018/10/25
 */
@Controller
@Slf4j
public class LoginController {

    @Autowired
    private MiaoShaUserService miaoShaUserService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Result doLogin(HttpServletResponse response,  @Validated LoginVo loginVo) {

        log.info(loginVo.toString());
        // 登录
        miaoShaUserService.login(response, loginVo);
        return Result.success(true);

    }
}
