package com.camelot.transaction.jta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JtaApplication {

  public static void main(String[] args) {
    SpringApplication.run(JtaApplication.class, args);
  }
}
