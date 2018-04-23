package com.camelot.transaction.order;

import com.camelot.transaction.common.idworker.IdWorker;
import com.camelot.transaction.message.api.service.OrderService;
import com.camelot.transaction.order.api.dto.OrderDto;
import com.camelot.transaction.order.service.mapper.OrderMapper;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderTest {

  @Autowired
  private OrderMapper orderMapper;

  @Autowired
  private OrderService orderService;

  @Test
  public void testMapper() {
    Long orderId = IdWorker.getId();
    Long msgId = IdWorker.getId();
    OrderDto dto = new OrderDto();
    dto.setId(orderId);
    dto.setCapitalPayAmount(BigDecimal.TEN);
    dto.setRedPacketPayAmount(BigDecimal.TEN);
    Assert.assertEquals(1, orderMapper.createOrder(dto));
    Assert.assertEquals(1, orderMapper.logBiz(msgId, orderId));
    Assert.assertEquals(1, orderMapper.queryMessageCount(msgId));
  }


  @Test
  public void testService() {
    OrderDto order = new OrderDto();
    order.setId(IdWorker.getId());
    order.setRedPacketPayAmount(BigDecimal.ONE);
    order.setCapitalPayAmount(BigDecimal.ONE);
    orderService.createOrder(order);
  }

}
