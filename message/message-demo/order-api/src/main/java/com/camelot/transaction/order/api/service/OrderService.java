package com.camelot.transaction.message.api.service;

import com.camelot.transaction.order.api.dto.OrderDto;

public interface OrderService {

  /**
   * 创建订单.
   */
  void createOrder(OrderDto orderDto);

  /**
   * 判断业务是否成功.
   */
  boolean isBizSuccess(Long messageId);
}
