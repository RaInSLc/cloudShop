package org.rainsc.spzx.manager.Service;

import jakarta.servlet.http.HttpServletResponse;
import org.rainsc.spzx.model.entity.product.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    List<Category> findCategoryList(Long id);

    // 导出
    void exportData(HttpServletResponse response);

    // 导入
    void importData(MultipartFile file);
}
