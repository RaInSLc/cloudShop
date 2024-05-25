package org.rainsc.spzx.product.service.impl;

import org.rainsc.spzx.model.entity.product.ProductSku;
import org.rainsc.spzx.product.mapper.ProductSkuMapper;
import org.rainsc.spzx.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductSkuMapper productSkuMapper;

    /**
     * @return
     */
    @Cacheable(value = "product", key = "'Sku'")
    @Override
    public List<ProductSku> findProductSkuBySale() {
        return productSkuMapper.findProductSkuBySale();
    }
}
