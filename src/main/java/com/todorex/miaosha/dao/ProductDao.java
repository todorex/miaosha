package com.todorex.miaosha.dao;

import com.todorex.miaosha.domain.MiaoShaProduct;
import com.todorex.miaosha.vo.ProductVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author rex
 * 2018/10/26
 */
@Mapper
public interface ProductDao {
    @Select("select p.*, mp.stock_count, mp.start_date, mp.end_date, mp.miaosha_price from miaosha_product mp left join product p on mp.product_id = p.id")
    public List<ProductVo> listProductVo();

    @Select("select p.*, mp.stock_count, mp.start_date, mp.end_date, mp.miaosha_price from miaosha_product mp left join product p on mp.product_id = p.id where p.id = #{productId}")
    public ProductVo getProductVoByProductVoId(@Param("productId")long productId);

    @Update("update miaosha_product set stock_count = stock_count - 1 where product_id = #{productId}")
    public int reduceStock(MiaoShaProduct product);

}
