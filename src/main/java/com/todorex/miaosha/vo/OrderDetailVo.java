package com.todorex.miaosha.vo;

import com.todorex.miaosha.domain.OrderInfo;
import com.todorex.miaosha.domain.Product;
import lombok.Data;

/**
 * @Author rex
 * 2018/10/27
 */
@Data
public class OrderDetailVo {
    private ProductVo productVo;
    private OrderInfo order;
}
