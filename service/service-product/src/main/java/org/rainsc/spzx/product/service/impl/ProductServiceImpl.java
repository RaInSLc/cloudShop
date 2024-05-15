package org.rainsc.spzx.product.service.impl;

import org.rainsc.spzx.model.entity.product.ProductSku;
import org.rainsc.spzx.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    /**
     * @return
     */
    @Override
    public List<ProductSku> findProductSkuBySale() {
        return null;
    }
}
