package org.rainsc.spzx.manager.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.rainsc.spzx.manager.Service.CategoryService;
import org.rainsc.spzx.model.entity.product.Category;
import org.rainsc.spzx.model.vo.common.Result;
import org.rainsc.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // excel导出
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) {
        categoryService.exportData(response);


    }

    // 懒加载
    // 分类列表方法
    // 每次只查询一层数据
    // select * from category where parent_id = id
    @GetMapping("/findCategoryList/{id}")
    public Result findCategoryList(@PathVariable Long id) {
        List<Category> categoryList = categoryService.findCategoryList(id);
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }
}
