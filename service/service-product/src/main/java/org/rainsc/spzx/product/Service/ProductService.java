package org.rainsc.spzx.product.Service;

import com.github.pagehelper.PageInfo;
import org.rainsc.spzx.model.dto.h5.ProductSkuDto;
import org.rainsc.spzx.model.entity.product.ProductSku;
import org.rainsc.spzx.model.vo.h5.ProductItemVo;

import java.util.List;

public interface ProductService {
    List<ProductSku> findProductSkuBySale();

    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    ProductItemVo item(Long skuId);
}
