package com.todorex.miaosha.domain;

import lombok.Data;

/**
 * @Author rex
 * 2018/10/26
 */
@Data
public class MiaoShaOrder {
    private Long id;
    private Long userId;
    private Long  orderId;
    private Long productId;
}
