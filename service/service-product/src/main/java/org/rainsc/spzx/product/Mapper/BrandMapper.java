package org.rainsc.spzx.product.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainsc.spzx.model.entity.product.Brand;

import java.util.List;

@Mapper
public interface BrandMapper {
    List<Brand> findAll();
}
