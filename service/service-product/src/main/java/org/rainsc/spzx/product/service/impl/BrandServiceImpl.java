package org.rainsc.spzx.product.service.impl;

import org.rainsc.spzx.model.entity.product.Brand;
import org.rainsc.spzx.product.mapper.BrandMapper;
import org.rainsc.spzx.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
