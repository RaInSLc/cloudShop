package org.rainsc.spzx.manager.Controller;

import com.github.pagehelper.PageInfo;
import org.rainsc.spzx.manager.Service.ProductSpecService;
import org.rainsc.spzx.model.entity.product.ProductSpec;
import org.rainsc.spzx.model.vo.common.Result;
import org.rainsc.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/admin/product/productSpec")
public class ProductSpecController {

    @Autowired
    private ProductSpecService productSpecService;

    @GetMapping("/{page}/{limit}")
    public Result<PageInfo<ProductSpec>> list(@PathVariable Integer page, @PathVariable Integer limit) {
        PageInfo<ProductSpec> pageInfo = productSpecService.findByPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    // 添加
    @PostMapping("/save")
    public Result save(@RequestBody ProductSpec productSpec) {
        productSpecService.save(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);

    }

    // 修改
    @PutMapping("/updateById")
    public Result updateById(@RequestBody ProductSpec productSpec) {
        productSpecService.updateById(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);

    }

    // 删除
    @DeleteMapping("/delById/{id}")
    public Result delById(@PathVariable Long id) {
        productSpecService.delById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    // 加载商品规格数据
    @GetMapping("findAll")
    public Result findAll() {
        List<ProductSpec> list = productSpecService.findAll();
        return Result.build(list , ResultCodeEnum.SUCCESS) ;
    }

}