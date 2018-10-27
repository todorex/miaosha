package com.todorex.miaosha.domain;

import lombok.Data;

/**
 * @Author rex
 * 2018/10/26
 */
@Data
public class MiaoShaOrder {
    private Long id;
    /**
     * 建一个唯一索引(userId,productId)，保证一个用户只能秒杀商品一次
     */
    private Long userId;
    private Long  orderId;
    private Long productId;
}
