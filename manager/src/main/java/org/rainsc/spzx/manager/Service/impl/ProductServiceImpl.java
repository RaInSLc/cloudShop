package org.rainsc.spzx.manager.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.rainsc.spzx.manager.Mapper.ProductDetailsMapper;
import org.rainsc.spzx.manager.Mapper.ProductMapper;
import org.rainsc.spzx.manager.Mapper.ProductSkuMapper;
import org.rainsc.spzx.manager.Service.ProductService;
import org.rainsc.spzx.model.dto.product.ProductDto;
import org.rainsc.spzx.model.entity.product.Product;
import org.rainsc.spzx.model.entity.product.ProductDetails;
import org.rainsc.spzx.model.entity.product.ProductSku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private ProductDetailsMapper productDetailsMapper;


    // 条件分页查询
    @Override
    public PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto) {
        PageHelper.startPage(page, limit);
        List<Product> list = productMapper.findByPage(productDto);
        return new PageInfo<>(list);
    }

    // 添加
    @Override
    public void save(Product product) {
        // 保存商品基本信息 product
        // 设置初始值
        product.setStatus(0);
        product.setAuditStatus(0);
        productMapper.save(product);

        // 获取sku列表 保存sku信息 product_sku
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (int i = 0; i < productSkuList.size(); i++) {
            ProductSku productSku = productSkuList.get(i);
            // 编号  商品ID
            productSku.setSkuCode(product.getId() + "_" + i);
            productSku.setProductId(product.getId());
            // 名称 名称+规格名称
            productSku.setSkuName(product.getName() + productSku.getSkuSpec());
            // 销量
            productSku.setSaleNum(0);
            // 初始值
            productSku.setStatus(0);
            productSkuMapper.save(productSku);
        }
        //保存商品详情 到 product_details
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.save(productDetails);
    }
}
