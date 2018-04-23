package com.camelot.transaction.common.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * alibaba dubbo 自动配置 by jun.zhao 只有pom依赖了dubbo 的模块才会自动配置
 */
@Slf4j
@Configuration
@ConditionalOnClass({ApplicationConfig.class, RegistryConfig.class, ProtocolConfig.class})
@EnableConfigurationProperties(DubboProperties.class)
public class DubboConfiguration {

  @Autowired
  private DubboProperties dubboProperties;

  @Bean
  @ConditionalOnMissingBean
  public ApplicationConfig applicationConfig() {
    log.debug("Common-dubbo-DubboConfiguration-applicationConfig  设置完毕");
    return dubboProperties.getApplication();
  }

  @Bean
  @ConditionalOnMissingBean
  public RegistryConfig registryConfig() {
    log.debug("Common-dubbo-DubboConfiguration-registryConfig  设置完毕");
    return dubboProperties.getRegistry();
  }

  @Bean
  @ConditionalOnMissingBean
  public ProtocolConfig protocolConfig() {
    log.debug("Common-dubbo-DubboConfiguration-protocolConfig  设置完毕");
    return dubboProperties.getProtocol();
  }

}
