package com.camelot.transaction.jta;

import com.camelot.transaction.jta.demo.MultiDataSourceTxDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultiDataSourceTxTests {

  @Autowired
  private MultiDataSourceTxDemo demo;

  /**
   * 测试多数据源无事务.
   */
  @Test
  public void testMultiDataSourceNoTx() {
    demo.multiDataSourceNoTx();
  }

  /**
   * 测试多数据源事务回滚.
   */
  @Test
  public void testMultiDataSourceWithTx() throws Exception {
    demo.multiDataSourceWithTx();
  }

}
