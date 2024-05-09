package org.rainsc.spzx.manager.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainsc.spzx.model.dto.order.OrderStatisticsDto;
import org.rainsc.spzx.model.entity.order.OrderStatistics;

import java.util.List;

@Mapper
public interface OrderStatisticsMapper {
    void insert(OrderStatistics orderStatistics);

    List<OrderStatistics> selectList(OrderStatisticsDto orderStatisticsDto);
}
