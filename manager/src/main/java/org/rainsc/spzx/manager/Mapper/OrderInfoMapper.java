package org.rainsc.spzx.manager.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainsc.spzx.model.entity.order.OrderStatistics;

@Mapper
public interface OrderInfoMapper {

    OrderStatistics selectOrderStatistics(String createTime);
}
