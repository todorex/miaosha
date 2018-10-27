package com.todorex.miaosha.service;

import com.todorex.miaosha.domain.MiaoShaUser;
import com.todorex.miaosha.domain.OrderInfo;
import com.todorex.miaosha.vo.ProductVo;

/**
 * @Author rex
 * 2018/10/26
 */
public interface MiaoShaService {

    /**
     * 秒杀
     * @param user
     * @param product
     * @return
     */
    OrderInfo miaosha(MiaoShaUser user, ProductVo product);
}
