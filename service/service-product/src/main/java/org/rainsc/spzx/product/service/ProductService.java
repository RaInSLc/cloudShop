package org.rainsc.spzx.product.service;

import com.github.pagehelper.PageInfo;
import org.rainsc.spzx.model.dto.h5.ProductSkuDto;
import org.rainsc.spzx.model.entity.product.ProductSku;

import java.util.List;

public interface ProductService {
    List<ProductSku> findProductSkuBySale();

    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);
}
