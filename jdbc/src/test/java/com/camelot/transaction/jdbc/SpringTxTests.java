package com.camelot.transaction.jdbc;

import com.camelot.transaction.jdbc.demo.SpringTxDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringTxTests {

  @Autowired
  private SpringTxDemo demo;

  /**
   * 测试没有事务包围.
   */
  @Test
  public void testNoTransaction() {
    demo.noTransaction();
  }

  /**
   * 测试有事务包围.
   */
  @Test
  public void testWithTransaction() {
    demo.withTransaction();
  }

  /**
   * 事务嵌套.
   */
  @Test
  public void testNestedTransaction() {
    demo.nestedTransaction();
  }

  @Test
  public void testMultiTablesRollback() throws Exception {
    demo.multiTablesRollback();
  }
}
