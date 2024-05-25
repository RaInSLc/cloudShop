package org.rainsc.spzx.product.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainsc.spzx.model.entity.product.Product;


@Mapper
public interface ProductMapper {


    Product getById(Long productId);
}
