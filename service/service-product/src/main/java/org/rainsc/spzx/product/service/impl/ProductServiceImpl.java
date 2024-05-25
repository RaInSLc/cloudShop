package org.rainsc.spzx.product.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.rainsc.spzx.model.dto.h5.ProductSkuDto;
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

    /**
     * @param page
     * @param limit
     * @param productSkuDto
     * @return
     */
    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page,limit);
        List<ProductSku> productSkuList =  productSkuMapper.findByPage(productSkuDto);

        return new PageInfo<>(productSkuList);
    }
}
