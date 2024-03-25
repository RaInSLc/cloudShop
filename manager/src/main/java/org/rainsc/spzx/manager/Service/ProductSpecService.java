package org.rainsc.spzx.manager.Service;

import com.github.pagehelper.PageInfo;
import org.rainsc.spzx.model.entity.product.ProductSpec;

import java.util.List;

public interface ProductSpecService {
    PageInfo<ProductSpec> findByPage(Integer page, Integer limit);

    void save(ProductSpec productSpec);

    void updateById(ProductSpec productSpec);

    void delById(Long id);

    List<ProductSpec> findAll();
}
