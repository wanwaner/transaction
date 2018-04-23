package com.camelot.transaction.common.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.spring.ServiceBean;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;


/**
 * ProviderConfiguration Dubbo-Service注解配置,暴露服务 by jun.zhao
 * 主要改动:  检查注解配置,如果没有设置则按照全局配置设置providerBean
 */
@Slf4j
@Configuration
@ConditionalOnClass(Service.class)
@AutoConfigureAfter(DubboConfiguration.class)
@EnableConfigurationProperties(DubboProperties.class)
public class ProviderConfiguration {

  @Autowired
  private ApplicationContext applicationContext;
  @Autowired
  private DubboProperties properties;
  @Autowired
  private ApplicationConfig applicationConfig;
  @Autowired
  private ProtocolConfig protocolConfig;
  @Autowired
  private RegistryConfig registryConfig;

  /**
   * 初始化服务.
   *
   * @throws Exception 异常
   */
  @PostConstruct
  public void init() throws Exception {
    Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(Service.class);
    log.debug("找到dubbo-Service注解的bean.size={}", beans.size());
    for (Map.Entry<String, Object> entry : beans.entrySet()) {
      this.initProviderBean(entry.getKey(), entry.getValue());
    }
    log.debug("Common-dubbo-ProviderConfiguration-init  设置完毕");
  }

  /**
   * 初始化服务暴露bean.
   *
   * @param beanName beanName
   * @param bean 服务bean
   * @throws Exception 异常
   */
  private void initProviderBean(String beanName, Object bean) throws Exception {
    Service service = this.applicationContext.findAnnotationOnBean(beanName, Service.class);
    ServiceBean<Object> providerBean = new ServiceBean<>(service);
    //如果注解没配置interface
    if (providerBean.getInterface() == null && void.class.equals(service.interfaceClass()) && ""
        .equals(service.interfaceName())) {
      if (bean.getClass().getInterfaces().length > 0) {
        providerBean.setInterface(bean.getClass().getInterfaces()[0]);
      } else {
        throw new IllegalStateException(
            "对外暴露服务失败: " + bean.getClass().getName() + ", 失败原因: 暴露服务没有指明或实现接口 ");
      }
    }
    log.debug("getInterface:{}", providerBean.getInterface());
    //yml全局配置信息
    ServiceConfig serviceConfig = properties.getServiceConfig();
    //检查注解配置,如果没有设置则按照全局配置设置providerBean
    if ("".equals(service.version())) {
      providerBean.setVersion(serviceConfig.getVersion());
    }
    if ("".equals(service.group())) {
      providerBean.setGroup(serviceConfig.getGroup());
    }
    if (service.timeout() == 0 && serviceConfig.getTimeout() != null) {
      providerBean.setTimeout(serviceConfig.getTimeout());
    }
    if (service.retries() == 0 && serviceConfig.getRetries() != null) {
      providerBean.setRetries(serviceConfig.getRetries());
    }
    providerBean.setApplicationContext(this.applicationContext);
    providerBean.setApplication(this.applicationConfig);
    providerBean.setProtocol(this.protocolConfig);
    providerBean.setRegistry(this.registryConfig);
    providerBean.afterPropertiesSet();
    providerBean.setRef(bean);
    providerBean.export();
  }

}
