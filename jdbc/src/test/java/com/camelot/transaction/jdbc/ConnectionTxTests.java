package com.camelot.transaction.jdbc;

import com.camelot.transaction.jdbc.demo.ConnectionTxDemo;
import java.sql.SQLException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectionTxTests {

  @Autowired
  private ConnectionTxDemo demo;

  /**
   * 测试默认的事务提交.
   */
  @Test
  public void testDefaultCommit() throws SQLException {
    demo.defaultCommit();
  }

  /**
   * 测试事务不自动提交.
   */
  @Test
  public void testNoAutoCommit() throws SQLException {
    demo.noAutoCommit();
  }

  /**
   * 测试事务手动提交.
   */
  @Test
  public void testHandCommit() throws SQLException {
    demo.handCommit();
  }

  /**
   * 测试事务回滚.
   */
  @Test
  public void testRollback() throws SQLException {
    demo.rollback();
  }
}
