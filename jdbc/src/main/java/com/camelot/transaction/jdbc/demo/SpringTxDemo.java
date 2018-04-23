package com.camelot.transaction.jdbc.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring 注解事务的一个demo.
 */
@Slf4j
@Service
public class SpringTxDemo {

  @Autowired
  private JdbcTemplate template;

  /**
   * 没有事务包围时.
   */
  public void noTransaction() {
    template.update("UPDATE t_alipay SET amount = 6666 WHERE account_id = 'zhaojun'");
    template.update("INSERT INTO t_alipay VALUES('zhaojun','1000')");
    log.info("noTransaction 执行完毕");
  }

  /**
   * 事务包围时.
   */
  @Transactional
  public void withTransaction() {
    template.update("UPDATE t_alipay SET amount = 9999 WHERE account_id = 'zhaojun'");
    template.update("INSERT INTO t_alipay VALUES('zhaojun','1000')");
    log.info("noTransaction 执行完毕");
  }

  /**
   * 事务嵌套.
   */
  @Transactional
  public void nestedTransaction() {
    template.update("UPDATE t_alipay SET amount = 1234 WHERE account_id = 'zhaojun'");
    nested();
  }

  @Transactional
  void nested() {
    template.update("INSERT INTO t_alipay VALUES('zhaojun','1000')");
  }


  /**
   * 手动抛出异常回滚！注意: Exception异常不回滚
   */
  @Transactional
  public void multiTablesRollback() throws Exception {
    template
        .update("UPDATE t_alipay SET amount = amount - 1 WHERE account_id = 'zhaojun'");
    template
        .update("UPDATE t_yuebao SET amount = amount + 1 WHERE account_id = 'zhaojun'");
    log.info("multiTablesRollback 执行完毕");
    throw new RuntimeException("test");
  }
}
