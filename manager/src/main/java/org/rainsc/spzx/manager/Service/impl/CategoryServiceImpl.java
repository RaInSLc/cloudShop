package org.rainsc.spzx.manager.Service.impl;


import com.alibaba.excel.EasyExcel;
import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.rainsc.spzx.exception.R_Exception;
import org.rainsc.spzx.manager.Mapper.CategoryMapper;
import org.rainsc.spzx.manager.Service.CategoryService;
import org.rainsc.spzx.manager.listener.ExcelListener;
import org.rainsc.spzx.model.entity.product.Category;
import org.rainsc.spzx.model.vo.common.ResultCodeEnum;
import org.rainsc.spzx.model.vo.product.CategoryExcelVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.util.ArrayList;
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

    // 导出excel
    @Override
    public void exportData(HttpServletResponse response) {

        try {
            //  和其他信息

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            // 设置数据编码
            String fileName = URLEncoder.encode("分类数据", "UTF-8");
            // 设置响应头
            // 表示让文件以下载的形式打开
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 调用mapper方法查询所有分类 返回list
            List<Category> categoryList = categoryMapper.findAll();
            //
            ArrayList<CategoryExcelVo> categoryExcelVos = new ArrayList<>();
            // 遍历categoryList放入CategoryExcelVo中
            for (Category category : categoryList) {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                // category放在categoryExcelVo里
//                Long id = category.getId();
//                categoryExcelVo.setId(id);
                BeanUtils.copyProperties(category, categoryExcelVo);
                categoryExcelVos.add(categoryExcelVo);

            }
            // 调用easy excel的write方法完成
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class)
                    .sheet("分类表")
                    .doWrite(categoryExcelVos);
        } catch (Exception e) {
//            throw new RuntimeException(e);
            throw new R_Exception(ResultCodeEnum.EXCEL_ERR);
        }


    }

    // 导入
    @Override
    public void importData(MultipartFile file) {
        // 将excel解析加载到数据库中
        try {
            // 监听器
            // 每次读取文件都创建新的监听器 避免并发问题
            ExcelListener<CategoryExcelVo> excelListener = new ExcelListener(categoryMapper);
            EasyExcel.read(file.getInputStream(), CategoryExcelVo.class, excelListener)
                    .sheet().doRead();
        } catch (Exception e) {
//            throw new RuntimeException(e);
            throw new R_Exception(ResultCodeEnum.EXCEL_IN_ERR);
        }
    }
}
