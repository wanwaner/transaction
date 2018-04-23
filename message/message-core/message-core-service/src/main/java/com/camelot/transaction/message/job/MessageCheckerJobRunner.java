package com.camelot.transaction.message.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.camelot.transaction.message.api.dto.MessageInfoDto;
import com.camelot.transaction.message.api.service.MessageService;
import com.camelot.transaction.message.api.service.OrderService;
import com.camelot.transaction.message.service.mapper.MessageCoreMapper;
import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.spring.tasktracker.JobRunnerItem;
import com.github.ltsopensource.spring.tasktracker.LTS;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.logger.BizLogger;
import com.github.ltsopensource.tasktracker.runner.LtsLoggerFactory;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 检查任务执行器.
 */
@Slf4j
@LTS
public class MessageCheckerJobRunner {

  @Autowired
  private MessageService messageService;

  @Autowired
  private MessageCoreMapper messageCoreMapper;

  @Reference(owner = "order")
  private OrderService orderService;

  /**
   * 任务执行项. shardValue对应taskId.
   */
  @JobRunnerItem(shardValue = "message_checker")
  public Result checkPrepareMessage(Job job) {
    try {
      BizLogger bizLogger = LtsLoggerFactory.getBizLogger();

      //1.查询表中留存的预备消息.
      List<MessageInfoDto> preparMsgList = messageCoreMapper.searchPrepareMessage();
      preparMsgList.forEach(messageInfoDto -> {
        //更新 retry status
        messageCoreMapper.updateMessagStatus(messageInfoDto.getId());

        //2.调用业务查询服务，查询业务发起方是否成功.
        boolean isBizSuccess = orderService.isBizSuccess(messageInfoDto.getId());
        if (isBizSuccess) {
          //业务发起服务成功则提交事务消息.
          messageService.commitMessage(messageInfoDto);
        } else {
          //业务发起失败则删除消息日志.
          messageCoreMapper.deleteMessageById(messageInfoDto.getId());
        }
      });

      // 会发送到 LTS (JobTracker上)
      bizLogger.info("预备消息检查完成.");
    } catch (Exception e) {
      log.info("Run job failed!", e);
      return new Result(Action.EXECUTE_LATER, e.getMessage());
    }
    return new Result(Action.EXECUTE_SUCCESS, "预备消息检查成功");
  }

}
