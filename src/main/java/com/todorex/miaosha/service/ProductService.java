package com.todorex.miaosha.service;

import com.todorex.miaosha.vo.ProductVo;

import java.util.List;

/**
 * @Author rex
 * 2018/10/26
 */
public interface ProductService {

    /**
     * 获取商品列表
     * @return
     */
    public List<ProductVo> listProductVo();

    /**
     * 根据商品ID查询商品
     * @param productId
     * @return
     */
    public ProductVo getProductVoByProductId(long productId);

    /**
     * 减库存
     * @param product
     */
    public void reduceStock(ProductVo product);
}
