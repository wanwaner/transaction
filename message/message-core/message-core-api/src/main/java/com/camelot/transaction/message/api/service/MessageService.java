package com.camelot.transaction.message.api.service;

import com.camelot.transaction.message.api.dto.MessageInfoDto;

public interface MessageService {

  /**
   * 发送prepare消息.
   */
  boolean prepareMessage(MessageInfoDto msg);

  /**
   * 发送commit消息.
   */
  boolean commitMessage(MessageInfoDto msg);

}
