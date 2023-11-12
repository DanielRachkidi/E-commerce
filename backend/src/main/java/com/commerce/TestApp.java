package com.commerce;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApp
{
  private static final Logger logger = (Logger) LoggerFactory.getLogger(TestApp.class);
  
  public static void main(String[] args)
  {
    logger.info("Starting TestApp...");
    SpringApplication.run(TestApp.class, args);
    logger.info("TestApp started.");
  }
}