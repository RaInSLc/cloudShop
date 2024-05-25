package org.rainsc.spzx.product.Service.impl;

import org.rainsc.spzx.model.entity.product.Brand;
import org.rainsc.spzx.product.Mapper.BrandMapper;
import org.rainsc.spzx.product.Service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    /**
     * @return
     */
    @Override
    public List<Brand> findAll() {


        return brandMapper.findAll();
    }
}
