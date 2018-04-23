package com.camelot.transaction.jta.config;

import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据源自动配置和绑定.
 */
@Slf4j
//@Configuration
public class NonXADataSourceConfig {

  /**
   * 支付宝数据源.
   */
  @Bean(name = "alipayDataSource")
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.alipay")
  public DataSource alipayDataSource() {
    return DataSourceBuilder.create().build();
  }

  /**
   * 余额宝数据源.
   */
  @Bean(name = "yuebaoDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.yuebao")
  public DataSource yuebaoDataSource() {
    return DataSourceBuilder.create().build();
  }

  /**
   * 支付宝jdbc模板.
   */
  @Bean(name = "alipayJdbcTemplate")
  public JdbcTemplate alipayJdbcTemplate(
      @Qualifier("alipayDataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  /**
   * 余额宝jdbc模板.
   */
  @Bean(name = "yuebaoJdbcTemplate")
  public JdbcTemplate yuebaoJdbcTemplate(
      @Qualifier("yuebaoDataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

}
