package org.rainsc.spzx.manager.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainsc.spzx.model.entity.product.ProductDetails;

@Mapper
public interface ProductDetailsMapper {
    void save(ProductDetails productDetails);

    ProductDetails selectByProductId(Long id);

    void updateById(ProductDetails productDetails);

    void deleteByProductId(Long id);
}
