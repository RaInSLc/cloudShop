package org.rainsc.spzx.product.service;

import org.rainsc.spzx.model.entity.product.ProductSku;

import java.util.List;

public interface ProductService {
    List<ProductSku> findProductSkuBySale();
}
