package org.rainsc.spzx.manager.Controller;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Parameter;
import org.rainsc.spzx.manager.Service.ProductService;
import org.rainsc.spzx.model.dto.product.ProductDto;
import org.rainsc.spzx.model.entity.product.Product;
import org.rainsc.spzx.model.vo.common.Result;
import org.rainsc.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    @PostMapping("/save")
    public Result save(@RequestBody Product product) {
        productService.save(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    // 根据id查询商品信息
    @GetMapping("/getById/{id}")
    public Result<Product> getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return Result.build(product, ResultCodeEnum.SUCCESS);
    }

    //保存修改数据接口
    @PutMapping("/updateById")
    public Result updateById(@Parameter(name = "product", description = "请求参数实体类", required = true) @RequestBody Product product) {
        productService.updateById(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    // 删除
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@Parameter(name = "id", description = "商品id", required = true) @PathVariable Long id) {
        productService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    // 商品审核
    @GetMapping("/updateAuditStatus/{id}/{auditStatus}")
    public Result updateAuditStatus(@PathVariable Long id, @PathVariable Integer auditStatus) {
        productService.updateAuditStatus(id, auditStatus);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}