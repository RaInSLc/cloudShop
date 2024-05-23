package org.rainsc.spzx.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.rainsc.spzx.model.entity.product.Category;
import org.rainsc.spzx.model.vo.common.Result;
import org.rainsc.spzx.model.vo.common.ResultCodeEnum;
import org.rainsc.spzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "分类接口管理")
@RestController
@RequestMapping(value = "/api/product/category")
//@CrossOrigin
@SuppressWarnings({"unchecked", "rawtypes"})
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "获取分类树形数据")
    @GetMapping("/findCategoryTree")
    public Result<List<Category>> findCategoryTree() {
        // 树形结构 构造分类的层级
        List<Category> list = categoryService.findCategoryTree();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

}