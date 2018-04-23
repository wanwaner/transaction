package com.camelot.transaction.jdbc.demo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 最原始的基于Connection的JDBC操作 事务测试.
 */
@Slf4j
@Service
public class ConnectionTxDemo {

  @Autowired
  private DataSource dataSource;


  /**
   * Connection 默认提交.
   */
  public void defaultCommit() throws SQLException {
    @Cleanup Connection conn = dataSource.getConnection();
    @Cleanup Statement statement = conn.createStatement();
    statement.execute("update t_alipay set amount = 999 where account_id = 'zhaojun'");
    log.info("defaultCommit 执行完毕");
  }

  /**
   * Connection 取消默认提交.
   */
  public void noAutoCommit() throws SQLException {
    @Cleanup Connection conn = dataSource.getConnection();
    conn.setAutoCommit(false);
    @Cleanup Statement statement = conn.createStatement();
    statement.execute("update t_alipay set amount = 888 where account_id = 'zhaojun'");
    log.info("noAutoCommit 执行完毕");
  }

  /**
   * Connection 手动提交.
   */
  public void handCommit() throws SQLException {
    @Cleanup Connection conn = dataSource.getConnection();
    conn.setAutoCommit(false);
    @Cleanup Statement statement = conn.createStatement();
    statement.execute("update t_alipay set amount = 777 where account_id = 'zhaojun'");
    conn.commit();
    log.info("handCommit 执行完毕");
  }

  /**
   * 出错回滚.
   */
  public void rollback() throws SQLException {
    @Cleanup Connection conn = dataSource.getConnection();
    conn.setAutoCommit(false);
    @Cleanup Statement statement = conn.createStatement();
    try {
      statement.execute("update t_alipay set amount = 6666 where account_id = 'zhaojun'");
      statement.execute("insert into t_alipay values('zhaojun','1000')");
    } catch (SQLException ex) {
      log.error("执行sql报错:{}", ex);
      conn.rollback();
    }
    log.info("rollback 执行完毕");
  }

}
