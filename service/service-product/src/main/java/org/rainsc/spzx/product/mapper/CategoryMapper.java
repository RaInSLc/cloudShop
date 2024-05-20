package org.rainsc.spzx.product.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainsc.spzx.model.entity.product.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> findOneCategory();

    // 查询到所有的分类
    List<Category> findAll();
}
