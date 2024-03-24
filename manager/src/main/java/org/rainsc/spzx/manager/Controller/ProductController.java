package org.rainsc.spzx.manager.Controller;

import com.github.pagehelper.PageInfo;
import org.rainsc.spzx.manager.Service.ProductService;
import org.rainsc.spzx.model.dto.product.ProductDto;
import org.rainsc.spzx.model.entity.product.Product;
import org.rainsc.spzx.model.vo.common.Result;
import org.rainsc.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/admin/product/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 条件分页查询
    @GetMapping(value = "/{page}/{limit}")
    public Result<PageInfo<Product>> findByPage(@PathVariable Integer page, @PathVariable Integer limit, ProductDto productDto) {
        PageInfo<Product> pageInfo = productService.findByPage(page, limit, productDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

}