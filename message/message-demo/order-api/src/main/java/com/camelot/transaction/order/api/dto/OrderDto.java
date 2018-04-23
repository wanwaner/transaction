package com.camelot.transaction.order.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 订单实体.
 */
@Data
public class OrderDto implements Serializable {

  private long id; //订单
  private BigDecimal redPacketPayAmount; // 红包支付金额
  private BigDecimal capitalPayAmount; // 资金账号支付金额

}
