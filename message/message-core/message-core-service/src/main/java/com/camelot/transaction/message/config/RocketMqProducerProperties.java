package com.camelot.transaction.message.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MQ 生产者配置.
 */
@Data
@ConfigurationProperties("rocketmq.producer")
public class RocketMqProducerProperties {

  private String producerGroup; //生产组
  private String nameServiceAddr; //名称服务
  private String instanceName; //实例名

}
