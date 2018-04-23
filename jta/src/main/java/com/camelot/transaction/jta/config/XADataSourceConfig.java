package com.camelot.transaction.jta.config;

import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据源自动配置和绑定.
 */
@Slf4j
@Configuration
public class XADataSourceConfig {

  /**
   * 支付宝数据源.
   */
  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.alipay")
  public DataSource alipayDataSource() {
    return new AtomikosDataSourceBean();
  }

  /**
   * 余额宝数据源.
   */
  @Bean
  @ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.yuebao")
  public DataSource yuebaoDataSource() {
    return new AtomikosDataSourceBean();
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
