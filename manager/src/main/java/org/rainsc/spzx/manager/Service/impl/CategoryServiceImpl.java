package org.rainsc.spzx.manager.Service.impl;

import org.rainsc.spzx.manager.Mapper.CategoryMapper;
import org.rainsc.spzx.manager.Service.CategoryService;
import org.rainsc.spzx.model.entity.product.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    // 找分类列表
    @Override
    public List<Category> findCategoryList(Long id) {
        // 根据id返回list集合
        List<Category> categoryList = categoryMapper.selectCategoryByParentId(id);
        // 遍历返回list集合
        // 判断分类是否存在子 级 分类 如果存在子级菜单 设置hasChildren=true 否则false
        if (!CollectionUtils.isEmpty(categoryList)) {
            categoryList.forEach(item -> {
                // 判断分类是否存在子 级 分类
                int count = categoryMapper.selectCountByParentId(item.getParentId());
//                if (count>0){
//                    item.setHasChildren(true);
//                }else {
//                    item.setHasChildren(false);
//                }
                item.setHasChildren(count > 0);
            });
        }
        return categoryList;
    }
}
