package org.rainsc.spzx.manager.Service;

import org.rainsc.spzx.model.entity.product.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findCategoryList(Long id);
}
