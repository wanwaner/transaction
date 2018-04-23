package com.camelot.transaction;

import com.github.ltsopensource.spring.boot.annotation.EnableJobClient;
import com.github.ltsopensource.spring.boot.annotation.EnableTaskTracker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableJobClient
@EnableTaskTracker
@EnableTransactionManagement
@SpringBootApplication
public class MessageCoreServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(MessageCoreServiceApplication.class, args);
  }
}
