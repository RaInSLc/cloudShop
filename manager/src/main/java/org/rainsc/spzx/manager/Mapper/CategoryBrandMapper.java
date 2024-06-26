package org.rainsc.spzx.manager.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainsc.spzx.model.dto.product.CategoryBrandDto;
import org.rainsc.spzx.model.entity.product.Brand;
import org.rainsc.spzx.model.entity.product.CategoryBrand;

import java.util.List;

@Mapper
public interface CategoryBrandMapper {
    List<CategoryBrand> findByPage(CategoryBrandDto categoryBrandDto);

    void save(CategoryBrand categoryBrand);

    List<Brand> findBrandByCategoryId(Long categoryId);
}
