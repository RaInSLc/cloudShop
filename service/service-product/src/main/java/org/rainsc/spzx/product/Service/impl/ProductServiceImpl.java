package org.rainsc.spzx.product.Service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.rainsc.spzx.model.dto.h5.ProductSkuDto;
import org.rainsc.spzx.model.entity.product.Product;
import org.rainsc.spzx.model.entity.product.ProductDetails;
import org.rainsc.spzx.model.entity.product.ProductSku;
import org.rainsc.spzx.model.vo.h5.ProductItemVo;
import org.rainsc.spzx.product.Mapper.ProductDetailsMapper;
import org.rainsc.spzx.product.Mapper.ProductMapper;
import org.rainsc.spzx.product.Mapper.ProductSkuMapper;
import org.rainsc.spzx.product.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductSkuMapper productSkuMapper;

    /**
     * @return
     */
    // 热销商品丢进缓存
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
        PageHelper.startPage(page, limit);
        List<ProductSku> productSkuList = productSkuMapper.findByPage(productSkuDto);

        return new PageInfo<>(productSkuList);
    }

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    /**
     * @param skuId
     * @return
     */
    @Override
    public ProductItemVo item(Long skuId) {
        ProductItemVo productItemVo = new ProductItemVo();
        // 根据skuId获取 sku信息
        ProductSku productSku = productSkuMapper.getById(skuId);
        // 从上面获取到sku 根据sku获取productId 获取商品信息
        Long productId = productSku.getProductId();
        Product product = productMapper.getById(productId);
        // productId 获取详情信息
        ProductDetails productDetails = productDetailsMapper.getByProductId(productId);
        // 封装为map   商品规格对应的skuId信息
        HashMap<String, Object> skuSpecValueMap = new HashMap<>();
        // 根据商品id 获取所有sku
        List<ProductSku> productSkuList = productSkuMapper.findByProductId(productId);
        productSkuList.forEach(item -> {
            skuSpecValueMap.put(item.getSkuSpec(), item.getId());
        });


        // 需要的数据封装到productItemVo
        productItemVo.setProduct(product);
        productItemVo.setProductSku(productSku);
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);

        // 封装详情图片
        String imageUrls = productDetails.getImageUrls();
        String[] split = imageUrls.split(",");
        List<String> list = Arrays.asList(split);
        productItemVo.setDetailsImageUrlList(list);

        // 轮播图
        productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));
        // 商品规格
        productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue()));

        return productItemVo;
    }
}
