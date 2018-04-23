package com.camelot.transaction.message.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.camelot.transaction.message.api.dto.MessageInfoDto;
import com.camelot.transaction.message.api.service.MessageService;
import com.camelot.transaction.message.service.mapper.MessageCoreMapper;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service(owner = "message")
@Component
@Transactional
public class MessageServiceImpl implements MessageService {

  @Resource
  private MessageCoreMapper messageCoreMapper; //消息日志表mapper

  @Resource
  private DefaultMQProducer defaultMQProducer; //mq-producer

  /**
   * 预备消息.
   */
  @Override
  public boolean prepareMessage(MessageInfoDto dto) {
    return messageCoreMapper.insertPrepareMessage(dto) == 1;
  }

  /**
   * 提交消息.
   */
  @Override
  public boolean commitMessage(MessageInfoDto dto) {

    //转换MQ消息.
    Message msg = new Message(dto.getTopic(), dto.getTag(),
        dto.getContent().getBytes());// body

    //异步发送MQ信息,回调成功或失败方法.
    try {
      defaultMQProducer.send(msg, new SendCallback() {

        //成功回调,删除预备消息.
        @Override
        public void onSuccess(SendResult sendResult) {
          log.info("MQ 消息发送成功:{}", sendResult);
          messageCoreMapper.deleteMessageById(dto.getId());
        }

        //失败回调,
        @Override
        public void onException(Throwable e) {
          log.error("发送MQ消息时发送错误:{}", e);
        }
      });
    } catch (MQClientException | RemotingException | InterruptedException ex) {
      log.error("提交消息时发送错误:{}", ex);
      return false;
    }
    return true;
  }
}
