package com.camelot.transaction.message.job;

import com.github.ltsopensource.core.commons.utils.CollectionUtils;
import com.github.ltsopensource.core.domain.JobResult;
import com.github.ltsopensource.jobclient.support.JobCompletedHandler;
import com.github.ltsopensource.spring.boot.annotation.JobCompletedHandler4JobClient;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * 任务执行回调handler.
 */
@Slf4j
@JobCompletedHandler4JobClient
public class MessageCheckerFeedbackHandler implements JobCompletedHandler {

  @Override
  public void onComplete(List<JobResult> jobResults) {
    if (CollectionUtils.isNotEmpty(jobResults)) {
      jobResults.forEach(jobResult -> {
        log.info(" [LTS] 任务执行反馈结果:{}", jobResult);
      });
    }
  }
}
