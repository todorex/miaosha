package com.todorex.miaosha.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Author rex
 * 2018/10/26
 */
@Data
public class OrderInfo {
    private Long id;
    private Long userId;
    private Long productId;
    private Long  deliveryAddressId;
    private String productName;
    private Integer productCount;
    private Double productPrice;
    private Integer orderChannel;
    private Integer status;
    private Date createDate;
    private Date payDate;
}
