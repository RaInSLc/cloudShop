package org.rainsc.spzx.product.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainsc.spzx.model.entity.product.ProductDetails;

@Mapper
public interface ProductDetailsMapper {

    ProductDetails getByProductId(Long productId);
}
