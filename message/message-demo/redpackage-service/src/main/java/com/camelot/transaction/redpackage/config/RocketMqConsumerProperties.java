package com.camelot.transaction.redpackage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MQ 消费者配置.
 */
@Data
@ConfigurationProperties("rocketmq.consumer")
public class RocketMqConsumerProperties {

  private String consumerGroup; //消费组
  private String nameServiceAddr; //名称服务
  private String instanceName; //实例名

}
