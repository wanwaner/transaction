package com.camelot.transaction.redpackage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.camelot.transaction.redpackage.api.dto.RedPackageDto;
import com.camelot.transaction.redpackage.api.service.RedPackageService;
import com.camelot.transaction.redpackage.service.mapper.RedPackageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service(owner = "redpackage")
@Component
@Transactional
public class RedPackageServiceImpl implements RedPackageService {

  @Autowired
  private RedPackageMapper redPackageMapper;

  @Override
  public void record(String msgId, RedPackageDto dto) {

    if (redPackageMapper.queryLogCount(msgId) == 0) {
      log.info("记录MQ消息ID:{}", msgId);
      redPackageMapper.logMsg(msgId);
      log.info("红包记录:{}", dto);
      redPackageMapper.record(dto);
    }
    log.info("已经消费过,幂等");
  }
}
