package org.rainsc.spzx.manager.Service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.rainsc.spzx.manager.Mapper.BrandMapper;
import org.rainsc.spzx.manager.Service.BrandService;
import org.rainsc.spzx.model.entity.product.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageInfo<Brand> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Brand> list = brandMapper.findByPage();

        PageInfo<Brand> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    // 添加
    @Override
    public void save(Brand brand) {
        brandMapper.save(brand);
    }
}
