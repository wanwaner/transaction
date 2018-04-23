package com.camelot.transaction.redpackage;

import com.camelot.transaction.common.idworker.IdWorker;
import com.camelot.transaction.redpackage.api.dto.RedPackageDto;
import com.camelot.transaction.redpackage.service.mapper.RedPackageMapper;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedPackageTest {

  @Autowired
  private RedPackageMapper redPackageMapper;

  @Test
  public void testMapper() {
    RedPackageDto dto = new RedPackageDto();
    dto.setId(IdWorker.getId());
    dto.setRedPacketPayAmount(BigDecimal.TEN);
    Assert.assertEquals(1, redPackageMapper.record(dto));
  }


}
