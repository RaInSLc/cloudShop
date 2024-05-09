package org.rainsc.spzx.manager.Service.impl;

import cn.hutool.core.date.DateUtil;
import org.rainsc.spzx.manager.Mapper.OrderStatisticsMapper;
import org.rainsc.spzx.manager.Service.OrderInfoService;
import org.rainsc.spzx.model.dto.order.OrderStatisticsDto;
import org.rainsc.spzx.model.entity.order.OrderStatistics;
import org.rainsc.spzx.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    OrderStatisticsMapper orderStatisticsMapper;

    /**
     * @param orderStatisticsDto 统计结果
     * @return OrderStatisticsVo
     */
    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
        // 根据DTO查询到统计结果的数据
        List<OrderStatistics> orderStatisticsList = orderStatisticsMapper.selectList(orderStatisticsDto);
        // 遍历list集合 得到所有的日期 将所有的日期封装到新的list中

        List<String> dateList = orderStatisticsList.stream()
                .map(orderStatistics ->
                        DateUtil.format(orderStatistics.getOrderDate(), "yyyy-mm-dd")).toList();


        // 遍历list集合 得到所有日期对应的总金额 将总金额封装到新的list中

        List<BigDecimal> decimalList = orderStatisticsList
                .stream().map(OrderStatistics::getTotalAmount)
//                .collect(Collectors.toList());
                .toList();
        // 把上面创建的对应数轴的list封装到OrderStatisticsVo中 然后返回
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setDateList(dateList);
        orderStatisticsVo.setAmountList(decimalList);

        return orderStatisticsVo;
    }
}
