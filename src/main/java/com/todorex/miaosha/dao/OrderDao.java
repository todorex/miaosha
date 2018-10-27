package com.todorex.miaosha.dao;

import com.todorex.miaosha.domain.MiaoShaOrder;
import com.todorex.miaosha.domain.OrderInfo;
import org.apache.ibatis.annotations.*;

/**
 * @Author rex
 * 2018/10/26
 */
@Mapper
public interface OrderDao {
    @Select("select * from miaosha_order where user_id=#{userId} and product_id=#{productId}")
    public MiaoShaOrder getMiaoShaOrderByUserIdAndproductId(@Param("userId")long userId, @Param("productId")long productId);

    @Insert("insert into order_info(user_id, product_id, product_name, product_count, product_price, order_channel, status, create_date)values("
            + "#{userId}, #{productId}, #{productName}, #{productCount}, #{productPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    public long insert(OrderInfo orderInfo);

    @Insert("insert into miaosha_order (user_id, product_id, order_id)values(#{userId}, #{productId}, #{orderId})")
    public int insertMiaoshaOrder(MiaoShaOrder miaoshaOrder);
}
