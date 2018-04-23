package com.camelot.transaction.common.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * DubboProperties 属性文件配置.
 */
@Data
@ConfigurationProperties(prefix = "dubbo")
public class DubboProperties {

  /**
   * provider 配置.
   */
  @NestedConfigurationProperty
  private ServiceConfig serviceConfig;

  /**
   * reference 配置.
   */
  @NestedConfigurationProperty
  private ReferenceConfig referenceConfig;

  /**
   * 应用配置.
   */
  @NestedConfigurationProperty
  private ApplicationConfig application;

  /**
   * 注册配置.
   */
  @NestedConfigurationProperty
  private RegistryConfig registry;

  /**
   * 协议配置.
   */
  @NestedConfigurationProperty
  private ProtocolConfig protocol;


  private List<String> devOwnerList = new ArrayList<>();
}
