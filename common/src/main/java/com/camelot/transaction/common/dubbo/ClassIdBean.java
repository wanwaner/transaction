package com.camelot.transaction.common.dubbo;

import java.io.Serializable;
import lombok.Data;


/**
 * 引用服务做判重唯一bean.
 */
@Data
public class ClassIdBean implements Serializable {

  private static final long serialVersionUID = -1918263464657216225L;
  private Class<?> clazz;
  private String group;
  private String version;

  /**
   * 构造函数.
   *
   * @param clazz 类
   * @param group 组
   * @param version 版本
   */
  public ClassIdBean(Class<?> clazz, String group, String version) {
    this.clazz = clazz;
    this.group = group;
    this.version = version;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof ClassIdBean)) {
      return false;
    }
    ClassIdBean classIdBean = (ClassIdBean) obj;
    if (this.clazz == null ? classIdBean.clazz != null : !this.clazz.equals(classIdBean.clazz)) {
      return false;
    }
    if (this.group == null ? classIdBean.group != null : !this.group.equals(classIdBean.group)) {
      return false;
    }
    return this.version == null ? classIdBean.version == null
        : this.version.equals(classIdBean.version);
  }

  @Override
  public int hashCode() {
    int hashCode = 17;
    hashCode = 31 * hashCode + (this.clazz == null ? 0 : this.clazz.hashCode());
    hashCode = 31 * hashCode + (this.group == null ? 0 : this.group.hashCode());
    hashCode = 31 * hashCode + (this.version == null ? 0 : this.version.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "ClassIdBean [clazz=" + this.clazz + ", group=" + this.group + ", version="
        + this.version + "]";
  }
}
