package org.rainsc.spzx.manager.Service;

import com.github.pagehelper.PageInfo;
import org.rainsc.spzx.model.dto.product.ProductDto;
import org.rainsc.spzx.model.entity.product.Product;

public interface ProductService {
    PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto);

    void save(Product product);

    Product getById(Long id);

    void updateById(Product product);

    void deleteById(Long id);
//    商品审核
    void updateAuditStatus(Long id, Integer auditStatus);
}
