package org.rainsc.spzx.manager.Service.impl;

import org.rainsc.spzx.manager.Mapper.ProductUnitMapper;
import org.rainsc.spzx.manager.Service.ProductUnitService;
import org.rainsc.spzx.model.entity.base.ProductUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductUnitServiceImpl implements ProductUnitService {
    @Autowired
    private ProductUnitMapper productUnitMapper;
    @Override
    public List<ProductUnit> findAll() {
        return productUnitMapper.findAll() ;
    }
}
