package org.rainsc.spzx.manager.task;


import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.rainsc.spzx.manager.Mapper.OrderInfoMapper;
import org.rainsc.spzx.manager.Mapper.OrderStatisticsMapper;
import org.rainsc.spzx.model.entity.order.OrderStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class OrderStatisticsTask {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    // 每天2点统计前一天的数据
    @Scheduled(cron = "0 0 2 * * ?")
    public void orderTotalAmountStatistics() {
        // 这是日志
        log.info("这是定时任务的日志");
        // 获取前一天的日期
        String createTime = DateUtil.offsetDay(new Date(), -1)
                .toString(new SimpleDateFormat("yyyy-MM-dd"));
        // 根据前一天的日期统计交易
        OrderStatistics orderStatistics = orderInfoMapper.selectOrderStatistics(createTime);
        // 统计后的数据添加到统计结果表中
        if (orderStatistics != null) {
            orderStatisticsMapper.insert(orderStatistics);
        }
    }
}
