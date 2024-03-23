package org.rainsc.spzx.manager.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainsc.spzx.model.entity.product.ProductSpec;

import java.util.List;

@Mapper
public interface ProductSpecMapper {
    // 分页查询
    List<ProductSpec> findByPage();

    // 添加
    void save(ProductSpec productSpec);

    void updateById(ProductSpec productSpec);

    void delById(Long id);
}
