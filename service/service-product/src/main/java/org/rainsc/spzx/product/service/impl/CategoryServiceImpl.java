package org.rainsc.spzx.product.service.impl;

import org.rainsc.spzx.model.entity.product.Category;
import org.rainsc.spzx.product.mapper.CategoryMapper;
import org.rainsc.spzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * @return
     */
    @Override
    public List<Category> findOneCategory() {
        return categoryMapper.findOneCategory();
    }

    /**
     * @return 树形结构 构造分类的层级 按层级返回分类
     */
    @Override
    public List<Category> findCategoryTree() {
        // 查询到所有的分类
        List<Category> allCategoryList = categoryMapper.findAll();
        // 遍历分类list  parentId=0 得到所有的一级分类

        // 遍历一级分类 id = parentId, 得到二级分类

        // 遍历二级分类 id = parentId, 得到三级分类

        return null;
    }
}
