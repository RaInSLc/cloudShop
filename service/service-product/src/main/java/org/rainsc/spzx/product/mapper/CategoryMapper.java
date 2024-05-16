package org.rainsc.spzx.product.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainsc.spzx.model.entity.product.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> findOneCategory();
}
