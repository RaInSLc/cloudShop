package org.rainsc.spzx.manager.listener;

// 监听器
// spring是单例模式, 并发时如果同时读取, 则无法区分listener归属
// 所以不能由spring管理 只能通过构造方法传递

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import org.rainsc.spzx.manager.Mapper.CategoryMapper;
import org.rainsc.spzx.model.vo.product.CategoryExcelVo;

import java.util.List;

public class ExcelListener<T> implements ReadListener<T> {

    // 通过构造方法传递mapper
    private final CategoryMapper categoryMapper;

    // 构造方法
    public ExcelListener(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    // 创建缓存 防止频繁写入ＯＯＭ
    // 缓存的数据量 BATCH_COUNT
    private static final int BATCH_COUNT = 100;

    // 缓存数据列表
    private List<T> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    // 从第二行开始读取  每行封装到T中
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        // 将数据加入缓存列表
        cacheDataList.add(t);
        // 达到100 存入数据库
        if (cacheDataList.size() >= BATCH_COUNT) {
            // 批量存入数据库
            saveData();
            // 清理缓存列表
            cacheDataList.clear();
        }
    }

    // 读取完成后的操作
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 保存剩余的数据
        saveData();
    }

    // 批量保存数据到数据库
    private void saveData() {
        // 强制类型转换为 CategoryExcelVo，并调用 Mapper 的批量插入方法
        categoryMapper.batchInsert((List<CategoryExcelVo>) cacheDataList);
    }
}
