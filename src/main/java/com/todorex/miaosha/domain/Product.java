package com.todorex.miaosha.domain;

import lombok.Data;

/**
 * @Author rex
 * 2018/10/26
 */
@Data
public class Product {
    private Long id;
    private String productName;
    private String productTitle;
    private String productImg;
    private String productDetail;
    private Double productPrice;
    private Integer productStock;
}
