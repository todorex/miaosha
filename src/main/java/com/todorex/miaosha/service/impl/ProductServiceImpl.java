package com.todorex.miaosha.service.impl;

import com.todorex.miaosha.dao.ProductDao;
import com.todorex.miaosha.domain.MiaoShaProduct;
import com.todorex.miaosha.domain.Product;
import com.todorex.miaosha.service.ProductService;
import com.todorex.miaosha.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author rex
 * 2018/10/26
 */

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<ProductVo> listProductVo() {

        return productDao.listProductVo();
    }

    @Override
    public ProductVo getProductVoByProductId(long productId) {
       return productDao.getProductVoByProductVoId(productId);
    }

    @Override
    public void reduceStock(ProductVo product) {
        MiaoShaProduct p = new MiaoShaProduct();
        p.setId(product.getId());
        productDao.reduceStock(p);
    }
}
