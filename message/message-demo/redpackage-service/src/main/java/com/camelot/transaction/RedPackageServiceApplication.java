package com.camelot.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
public class RedPackageServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(RedPackageServiceApplication.class, args);
  }
}
