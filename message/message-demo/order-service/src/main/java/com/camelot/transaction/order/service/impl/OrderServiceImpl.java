package com.camelot.transaction.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.camelot.transaction.common.idworker.IdWorker;
import com.camelot.transaction.message.api.dto.MessageInfoDto;
import com.camelot.transaction.message.api.service.MessageService;
import com.camelot.transaction.message.api.service.OrderService;
import com.camelot.transaction.order.api.dto.OrderDto;
import com.camelot.transaction.order.service.mapper.OrderMapper;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service(owner = "order")
@Component
@Transactional
public class OrderServiceImpl implements OrderService {

  @Resource
  private OrderMapper orderMapper;

  @Reference(owner = "message")
  private MessageService messageService;

  @Override
  public void createOrder(OrderDto orderDto) {
    Long msgId = IdWorker.getId();
    MessageInfoDto msg = new MessageInfoDto();
    msg.setId(msgId);
    msg.setSource("ORDER");
    msg.setTopic("order");
    msg.setTag("consumer");

    Map<String, String> map = new HashMap<>();
    map.put("redPacketPayAmount", "10");
    map.put("capitalPayAmount", "10");
    msg.setContent(JSONObject.toJSONString(map));
    if (messageService.prepareMessage(msg)) {
      //事务发起方业务
      orderMapper.createOrder(orderDto);

      //记录消息,用于反查业务是否成功.
      orderMapper.logBiz(msgId, orderDto.getId());

      //提交消息.
      messageService.commitMessage(msg);
    }
  }

  @Override
  public boolean isBizSuccess(Long messageId) {
    return orderMapper.queryMessageCount(messageId) == 1;
  }
}
