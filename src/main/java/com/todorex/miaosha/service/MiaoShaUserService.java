package com.todorex.miaosha.service;

import com.todorex.miaosha.domain.MiaoShaUser;
import com.todorex.miaosha.result.CodeMsg;
import com.todorex.miaosha.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author rex
 * 2018/10/25
 */
public interface MiaoShaUserService {

    public MiaoShaUser getById(long id);

    public boolean login(HttpServletResponse response, LoginVo loginVo);

    public MiaoShaUser getByToken(HttpServletResponse response, String token);
}
