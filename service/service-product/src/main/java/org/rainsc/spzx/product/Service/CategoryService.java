package org.rainsc.spzx.product.Service;

import org.rainsc.spzx.model.entity.product.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findOneCategory();

    List<Category> findCategoryTree();
}
