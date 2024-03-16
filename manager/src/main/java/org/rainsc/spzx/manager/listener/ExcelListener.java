package org.rainsc.spzx.manager.listener;


// 监听器
// spring是单例模式, 并发时如果同时读取, 则无法区分listener归属
// 所以不能由spring管理 只能通过构造方法传递

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import org.apache.poi.ss.formula.functions.T;
import org.rainsc.spzx.manager.Mapper.CategoryMapper;

import java.util.List;

public class ExcelListener implements ReadListener<T> {

    // 通过构造方法传递mapper
    private CategoryMapper categoryMapper;

    public ExcelListener(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    // 创建缓存 防止频繁写入ＯＯＭ
    // 缓存的数据量BATCH_COUNT

    private static final int BATCH_COUNT = 100;

    private List<T> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    // 从第二行开始读取  每行封装到T中
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {

        cacheDataList.add(t);
        // 达到100 存入数据库
        if (cacheDataList.size() >= BATCH_COUNT) {
            // 批量存入数据库
            saveData();
            // 清理list
            cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 保存数据
        saveData();
    }

    // 保存方法
    private void saveData() {
        categoryMapper.batchInsert(cacheDataList);
    }
}
