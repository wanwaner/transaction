package com.camelot.transaction.redpackage.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 红包实体.
 */
@Data
public class RedPackageDto implements Serializable {

  private long id; //订单
  private BigDecimal redPacketPayAmount; // 红包支付金额

}
