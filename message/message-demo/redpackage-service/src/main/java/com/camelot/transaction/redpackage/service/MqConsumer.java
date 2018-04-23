package com.camelot.transaction.redpackage.service;

import com.alibaba.fastjson.JSONObject;
import com.camelot.transaction.common.idworker.IdWorker;
import com.camelot.transaction.redpackage.api.dto.RedPackageDto;
import com.camelot.transaction.redpackage.api.service.RedPackageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqConsumer implements InitializingBean {

  @Autowired
  private DefaultMQPushConsumer defaultMQPushConsumer;

  @Autowired
  private RedPackageService redPackageService;

  @Override
  public void afterPropertiesSet() throws Exception {
    defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
      MessageExt msg = msgs.get(0);
      log.info("普通消费:{},内容:{}", msg, new String(msg.getBody()));
      try {
        String msgId = msg.getMsgId();

        RedPackageDto dto = JSONObject.parseObject(new String(msg.getBody()), RedPackageDto.class);
        dto.setId(IdWorker.getId());
        log.info("RedPackageDto:{}", dto);
        redPackageService.record(msgId,dto);
      } catch (Exception ex) {
        log.error("error:{}", ex);
        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
      }
      return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    });

    defaultMQPushConsumer.subscribe("order", "consumer");
    defaultMQPushConsumer.start();

  }
}
