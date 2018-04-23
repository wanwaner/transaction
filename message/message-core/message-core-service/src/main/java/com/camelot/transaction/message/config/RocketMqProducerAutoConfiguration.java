package com.camelot.transaction.message.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MQ 生产者自动配置.
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(RocketMqProducerProperties.class)
public class RocketMqProducerAutoConfiguration {

  @Autowired
  private RocketMqProducerProperties rmqProperties; //生产者配置信息.

  /**
   * 默认生产者.
   */
  @Bean
  public DefaultMQProducer defaultMQProducer() throws MQClientException {
    DefaultMQProducer producer = new DefaultMQProducer(rmqProperties.getProducerGroup());
    producer.setNamesrvAddr(rmqProperties.getNameServiceAddr());
    producer.setInstanceName(rmqProperties.getInstanceName());
    producer.setVipChannelEnabled(false);
    producer.start();
    log.info("DefaultMQProducer is Started.");
    return producer;
  }

}
