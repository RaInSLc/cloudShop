package org.rainsc.spzx.manager.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainsc.spzx.model.entity.product.Category;
import org.rainsc.spzx.model.vo.product.CategoryExcelVo;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> selectCategoryByParentId(Long id);

    int selectCountByParentId(Long parentId);

    List<Category> findAll();

    // excel导入批量保存
    // cacheDataList缓存的类别列表
    void batchInsert(List<CategoryExcelVo> cacheDataList);
}
