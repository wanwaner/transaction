package com.camelot.transaction.common.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


/**
 * ConsumerConfiguration Dubbo-Reference注解配置,暴露服务 by jun.zhao
 * 主要改动:  检查注解配置,如果没有设置则按照全局配置设置consumerBean
 */
@Slf4j
@Configuration
@ConditionalOnClass(Reference.class)
@AutoConfigureAfter(DubboConfiguration.class)
@EnableConfigurationProperties(DubboProperties.class)
public class ConsumerConfiguration {

  @Autowired
  private ApplicationContext applicationContext;
  @Autowired
  private DubboProperties properties;
  @Autowired
  private ApplicationConfig applicationConfig;
  @Autowired
  private RegistryConfig registryConfig;

  public static final Map<ClassIdBean, Object> DUBBO_REFERENCES_MAP =
      new ConcurrentHashMap<>();

  /**
   * spring BeanPostProcessor扩展接口.
   *
   * @return BeanPostProcessor实现
   */
  @Bean
  public BeanPostProcessor beanPostProcessor() {
    return new BeanPostProcessor() {
      @Override
      public Object postProcessBeforeInitialization(Object bean, String beanName)
          throws BeansException {
        Class<?> objClz = bean.getClass();
        if (org.springframework.aop.support.AopUtils.isAopProxy(bean)) {
          objClz = org.springframework.aop.support.AopUtils.getTargetClass(bean);
        }
        try {
          for (Field field : objClz.getDeclaredFields()) {
            //获取Reference注解的bean
            Reference reference = field.getAnnotation(Reference.class);
            if (reference != null) {
              Class<?> type = field.getType();
              //根据注解和接口类型获取引用bean
              ReferenceBean<?> referenceBean = initReferenceBean(type, reference);
              ClassIdBean classIdBean = new ClassIdBean(type, referenceBean.getGroup(),
                  referenceBean.getVersion());
              Object dubboReference = DUBBO_REFERENCES_MAP.get(classIdBean);
              if (dubboReference == null) {
                synchronized (this) {
                  // double check
                  dubboReference = DUBBO_REFERENCES_MAP.get(classIdBean);
                  if (dubboReference == null) {
                    referenceBean.setApplicationContext(applicationContext);
                    referenceBean.setApplication(applicationConfig);
                    RegistryConfig registry = referenceBean.getRegistry();
                    if (registry == null) {
                      referenceBean.setRegistry(registryConfig);
                    }
                    referenceBean.setApplication(applicationConfig);
                    referenceBean.afterPropertiesSet();
                    dubboReference = referenceBean.getObject();
                    DUBBO_REFERENCES_MAP.put(classIdBean, dubboReference);
                  }
                }
              }
              field.setAccessible(true);
              field.set(bean, dubboReference);
              field.setAccessible(false);
            }
          }
        } catch (Exception ex) {
          throw new BeanCreationException(beanName, ex);
        }
        return bean;
      }

      @Override
      public Object postProcessAfterInitialization(Object bean, String beanName)
          throws BeansException {
        return bean;
      }
    };
  }

  /**
   * 初始化引用bean.
   *
   * @param interfaceClazz 接口类型
   * @param reference 注解
   * @param <T> 泛型
   * @return 引用bean
   */
  private <T> ReferenceBean<T> initReferenceBean(Class<T> interfaceClazz, Reference reference)
      throws BeansException {
    ReferenceBean<T> referenceBean = new ReferenceBean<>();
    //yml全局配置信息
    ReferenceConfig referenceConfig = properties.getReferenceConfig();
    referenceBean.setInterface(interfaceClazz);
    referenceBean.setId(interfaceClazz.getCanonicalName());
    String[] registryArr = reference.registry();
    if (registryArr.length > 0) {
      RegistryConfig registryConfig = new RegistryConfig();
      registryConfig.setAddress(StringUtils.arrayToCommaDelimitedString(registryArr));
      referenceBean.setRegistry(registryConfig);
    }
    List<String> devOwnerList = properties.getDevOwnerList();
    ServiceConfig serviceConfig = properties.getServiceConfig();
    //检查注解配置,如果没有设置则按照全局配置设置providerBean
    if ("".equals(reference.version())) {
      if (!"".equals(reference.owner()) && devOwnerList.contains(reference.owner())) {
        referenceBean.setVersion(serviceConfig.getVersion());
      } else {
        referenceBean.setVersion(referenceConfig.getVersion());
      }
    }
    if ("".equals(reference.group())) {
      if (!"".equals(reference.owner()) && devOwnerList.contains(reference.owner())) {
        referenceBean.setGroup(serviceConfig.getGroup());
      } else {
        referenceBean.setGroup(referenceConfig.getGroup());
      }
    }
    if (reference.timeout() == 0 && referenceConfig.getTimeout() != null) {
      referenceBean.setTimeout(referenceConfig.getTimeout());
    }
    if (reference.retries() == 0 && referenceConfig.getRetries() != null) {
      referenceBean.setRetries(referenceConfig.getRetries());
    }
    if (referenceConfig.isCheck() != null) {
      referenceBean.setCheck(referenceConfig.isCheck());
    }
    return referenceBean;
  }
}
