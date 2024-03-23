package org.rainsc.spzx.manager.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.rainsc.spzx.manager.Mapper.ProductSpecMapper;
import org.rainsc.spzx.manager.Service.ProductSpecService;
import org.rainsc.spzx.model.entity.product.ProductSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSpecServiceImpl implements ProductSpecService {
    @Autowired
    private ProductSpecMapper productSpecMapper;
    @Override
    public PageInfo<ProductSpec> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<ProductSpec> list = productSpecMapper.findByPage();
        return new PageInfo<>(list);
    }

    // 添加
    @Override
    public void save(ProductSpec productSpec) {

        productSpecMapper.save(productSpec);
    }

    // 修改
    @Override
    public void updateById(ProductSpec productSpec) {
        productSpecMapper.updateById(productSpec);
    }

    // 删除
    @Override
    public void delById(Long id) {
        productSpecMapper.delById(id);
    }
}
