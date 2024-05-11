package org.rainsc.spzx.manager.Controller;

import com.github.pagehelper.PageInfo;
import org.rainsc.spzx.common.log.annotation.Log;
import org.rainsc.spzx.common.log.enums.OperatorType;
import org.rainsc.spzx.manager.Service.BrandService;
import org.rainsc.spzx.model.entity.product.Brand;
import org.rainsc.spzx.model.vo.common.Result;
import org.rainsc.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    // 列表
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Integer page,
                       @PathVariable Integer limit) {
        PageInfo<Brand> pageInfo = brandService.findByPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    // 添加
    @PostMapping("/save")
    public Result save(@RequestBody Brand brand) {
        brandService.save(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    // TODO 修改& 删除
    @PutMapping("/update")
    public Result update(@RequestBody Brand brand) {
//        brandService.update(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Log(title = "品牌管理:列表", businessType = 0,operatorType = OperatorType.MANAGE)
    @DeleteMapping(value = "/deleteById/{id}")
    public Result deleteById(@PathVariable(value = "id") Long id) {
//        brandService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    // 查询所有品牌
    @GetMapping("/findAll")
    public Result findAll() {
        List<Brand> list = brandService.findAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

}
