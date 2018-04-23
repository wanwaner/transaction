package com.camelot.transaction.redpackage.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MQ 生产者自动配置.
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(RocketMqConsumerProperties.class)
public class RocketMqConsumerAutoConfiguration {

  @Autowired
  private RocketMqConsumerProperties rmqProperties; //生产者配置信息.

  /**
   * 默认消费者.
   */
  @Bean
  public DefaultMQPushConsumer defaultMQPushConsumer() throws MQClientException {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rmqProperties.getConsumerGroup());
    consumer.setNamesrvAddr(rmqProperties.getNameServiceAddr());
    consumer.setInstanceName(rmqProperties.getInstanceName());
    consumer.setVipChannelEnabled(false);
    consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
    consumer.setMessageModel(MessageModel.CLUSTERING);
    return consumer;
  }

}
