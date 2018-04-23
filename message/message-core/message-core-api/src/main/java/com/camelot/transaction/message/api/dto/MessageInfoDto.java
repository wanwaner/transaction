package com.camelot.transaction.message.api.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class MessageInfoDto implements Serializable {

  private long id; //消息日志id
  private String source; //触发来源
  private String topic; //mq topic
  private String tag; //mq tag
  private String content; //日志内容体
  private int retry; //重试次数
  private String status; //状态
  private Date createTime; //创建时间
  private Date updateTime; //创建时间

}
