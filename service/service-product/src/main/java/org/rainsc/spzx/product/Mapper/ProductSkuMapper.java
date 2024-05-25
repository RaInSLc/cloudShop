package org.rainsc.spzx.product.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainsc.spzx.model.dto.h5.ProductSkuDto;
import org.rainsc.spzx.model.entity.product.ProductSku;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    List<ProductSku> findProductSkuBySale();


    List<ProductSku> findByPage(ProductSkuDto productSkuDto);
}
