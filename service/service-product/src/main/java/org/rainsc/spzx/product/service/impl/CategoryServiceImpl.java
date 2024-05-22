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
        // 查询所有分类
        // 获取所有分类信息
        List<Category> allCategoryList = categoryMapper.findAll();

        // 遍历总分类List 过滤出一级分类列表
        List<Category> firstCategoryList = allCategoryList.stream()
                // 过滤出parentId为0的分类，即一级分类
                .filter(item -> item.getParentId().longValue() == 0)
                .toList();

        // 遍历一级分类，为每个一级分类添加二级分类
        firstCategoryList.forEach(firstCategory -> {
            List<Category> secondCategoryList = allCategoryList.stream()
                    // 过滤出parentId等于当前一级分类id的分类，即二级分类
                    .filter(item -> item.getParentId() == firstCategory.getId())
                    .toList();
            // 将二级分类列表封装到一级分类中
            firstCategory.setChildren(secondCategoryList);

            // 遍历二级分类，为每个二级分类添加三级分类
            secondCategoryList.forEach(secondCategory -> {
                List<Category> thirdCategoryList = allCategoryList.stream()
                        // 过滤出parentId等于当前二级分类id的分类，即三级分类
                        .filter(item -> item.getParentId() == secondCategory.getId())
                        .toList();
                // 将三级分类列表封装到二级分类中
                secondCategory.setChildren(thirdCategoryList);
            });
        });
        // 返回包含完整分类树的一级分类列表
        return firstCategoryList;
    }
}
