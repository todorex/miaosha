package com.todorex.miaosha.vo;

import com.todorex.miaosha.domain.Product;
import lombok.Data;

import java.util.Date;

/**
 * @Author rex
 * 2018/10/26
 */
@Data
public class ProductVo extends Product{
    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
