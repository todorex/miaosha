package com.todorex.miaosha.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Author rex
 * 2018/10/26
 */
@Data
public class MiaoShaProduct {
    private Long id;
    private Long productId;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
