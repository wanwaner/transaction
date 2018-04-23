package com.camelot.transaction.order.service.mapper;

import com.camelot.transaction.order.api.dto.OrderDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {

  //创建订单.
  int createOrder(OrderDto dto);

  //记录业务日志.
  int logBiz(@Param("msgId")Long msgId, @Param("bizId")Long bizId);

  //查询消息
  int queryMessageCount(Long msgId);
}