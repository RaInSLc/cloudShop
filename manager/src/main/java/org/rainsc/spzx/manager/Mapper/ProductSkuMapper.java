package org.rainsc.spzx.manager.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainsc.spzx.model.entity.product.ProductSku;

@Mapper
public interface ProductSkuMapper {
    void save(ProductSku productSku);
}
