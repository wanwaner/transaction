package com.camelot.transaction.common.dubbo;

import com.camelot.transaction.common.utils.SpringUtil;
import java.util.List;

/**
 * DubboHelper. 获取spring容器中无法获取的dubbo bean.
 */
public class DubboHelper {

  /**
   * 获取bean.
   */
  public static <T> T getBean(Class<T> clazz, String owner) {
    DubboProperties properties = SpringUtil.getBean(DubboProperties.class);
    List<String> devOwnerList = properties.getDevOwnerList();
    ClassIdBean classIdBean = null;
    if (!devOwnerList.isEmpty() && devOwnerList.contains(owner)) {
      classIdBean = new ClassIdBean(clazz,
          properties.getServiceConfig().getGroup(),
          properties.getServiceConfig().getVersion());
    } else {
      classIdBean = new ClassIdBean(clazz,
          properties.getReferenceConfig().getGroup(),
          properties.getReferenceConfig().getVersion());
    }
    Object dubboReference = ConsumerConfiguration.DUBBO_REFERENCES_MAP.get(classIdBean);
    if (dubboReference != null) {
      return (T) dubboReference;
    }
    return null;
  }

}
