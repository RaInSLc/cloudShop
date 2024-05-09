package org.rainsc.spzx.manager.Service;

import org.rainsc.spzx.model.dto.order.OrderStatisticsDto;
import org.rainsc.spzx.model.vo.order.OrderStatisticsVo;

public interface OrderInfoService {
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
