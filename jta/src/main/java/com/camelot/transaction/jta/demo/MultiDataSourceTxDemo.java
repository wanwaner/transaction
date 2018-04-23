package com.camelot.transaction.jta.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 多数据源事务demo.
 */
@Slf4j
@Service
public class MultiDataSourceTxDemo {

  @Autowired
  @Qualifier("alipayJdbcTemplate")
  private JdbcTemplate alipayJdbcTemplate; //支付宝

  @Autowired
  @Qualifier("yuebaoJdbcTemplate")
  private JdbcTemplate yuebaoJdbcTemplate; //余额宝


  /**
   * 多数据源无事务.
   */
  public void multiDataSourceNoTx() {
    alipayJdbcTemplate
        .update("UPDATE t_alipay SET amount = amount - 1 WHERE account_id = 'zhaojun'");
    yuebaoJdbcTemplate
        .update("UPDATE t_yuebao SET amount = amount + 1 WHERE account_id = 'zhaojun'");
    log.info("multiDataSourceNoTx 执行完毕");
  }

  /**
   * 多数据源事务回滚,只会回滚主数据源的事务,另一个数据源事务无法回滚！出现不一致情况.
   */
  @Transactional
  public void multiDataSourceWithTx() {
    alipayJdbcTemplate
        .update("UPDATE t_alipay SET amount = amount - 1 WHERE account_id = 'zhaojun'");
    yuebaoJdbcTemplate
        .update("UPDATE t_yuebao SET amount = amount + 1 WHERE account_id = 'zhaojun'");
    alipayJdbcTemplate
        .update("INSERT INTO t_alipay VALUES('zhaojun','1000')");
    log.info("multiDataSourceWithTx 执行完毕");
  }

}
