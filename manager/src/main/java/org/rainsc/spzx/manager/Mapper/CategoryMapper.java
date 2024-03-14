package org.rainsc.spzx.manager.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainsc.spzx.model.entity.product.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> selectCategoryByParentId(Long id);

    int selectCountByParentId(Long parentId);

    List<Category> findAll();
}
