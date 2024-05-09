package org.rainsc.spzx.manager.Controller;

import org.rainsc.spzx.manager.Service.OrderInfoService;
import org.rainsc.spzx.model.dto.order.OrderStatisticsDto;
import org.rainsc.spzx.model.vo.common.Result;
import org.rainsc.spzx.model.vo.common.ResultCodeEnum;
import org.rainsc.spzx.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/admin/order/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 获取订单统计数据
     *
     * @param orderStatisticsDto 订单统计数据的传输对象
     * @return 包含订单统计数据的结果对象
     */
    @GetMapping("/getOrderStatisticsData")
    public Result<OrderStatisticsVo> getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
        OrderStatisticsVo orderStatisticsVo = orderInfoService.getOrderStatisticsData(orderStatisticsDto);
        return Result.build(orderStatisticsVo, ResultCodeEnum.SUCCESS);
    }

}