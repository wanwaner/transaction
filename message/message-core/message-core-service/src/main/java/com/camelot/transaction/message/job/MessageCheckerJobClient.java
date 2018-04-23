package com.camelot.transaction.message.job;

import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;
import com.github.ltsopensource.jobclient.domain.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 检查任务提交.
 */
@Slf4j
@Component
public class MessageCheckerJobClient implements InitializingBean {

  @Autowired
  private JobClient jobClient; //jobClient已被自动配置.

  /**
   * 提交预备消息检查任务.
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    Job job = new Job();

    //任务ID 需要和执行者shardValue相同.
    job.setTaskId("message_checker");
    //任务消费节点.
    job.setTaskTrackerNodeGroup("message_TaskTracker");
    //是否结果回调.
    job.setNeedFeedback(true);
    //是否覆盖相同任务.
    job.setReplaceOnExist(true);
    //设置定时执行 每10s执行.
    job.setCronExpression("0/10 * * * * ?");
    //提交任务.
    Response response = jobClient.submitJob(job);

    log.info(" [LTS] 提交消息检查任务返回结果:{}", response);
  }
}
