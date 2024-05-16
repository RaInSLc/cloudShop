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
}
