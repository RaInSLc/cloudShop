package org.rainsc.spzx.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.rainsc.spzx.model.entity.product.Category;
import org.rainsc.spzx.model.entity.product.ProductSku;
import org.rainsc.spzx.model.vo.common.Result;
import org.rainsc.spzx.model.vo.common.ResultCodeEnum;
import org.rainsc.spzx.model.vo.h5.IndexVo;
import org.rainsc.spzx.product.service.CategoryService;
import org.rainsc.spzx.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "首页接口管理")
@RestController
@RequestMapping(value = "/api/product/index")
@CrossOrigin
@SuppressWarnings({"unchecked", "rawtypes"})
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Operation(summary = "获取首页数据")
    @GetMapping
    public Result<IndexVo> findData() {
        // 一级分类
        List<Category> categoryList = categoryService.findOneCategory();
        // 热销榜 销量排名
        List<ProductSku> productSkuList = productService.findProductSkuBySale();
        // 封装数据
        IndexVo indexVo = new IndexVo();
        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);
        return Result.build(indexVo, ResultCodeEnum.SUCCESS);
    }

}