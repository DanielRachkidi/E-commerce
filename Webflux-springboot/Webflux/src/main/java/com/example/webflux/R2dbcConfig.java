package com.example.webflux;

import io.r2dbc.proxy.ProxyConnectionFactory;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import io.r2dbc.spi.ValidationDepth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class R2dbcConfig {
  
  @Value("${spring.datasource.url}")
  private String url;
  
  @Value("${spring.datasource.user}")
  private String username;
  
  @Value("${spring.datasource.password}")
  private String password;
  
  @Bean
  public ConnectionFactory connectionFactory() {
    ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
//      .option(Option.DRIVER, "postgresql")
//      .option(Option.HOST, "localhost")
//      .option(Option.PORT, 5432)
//      .option(Option.DATABASE, "test")
//      .option(Option.USER, "postgres")
//      .option(Option.PASSWORD, "password")
//      .option(Option.PROTOCOL, "proxy")
//      .option(Option.PROXY_VALIDATION_DEPTH, ValidationDepth.LOCAL)
      .build();
    
    ConnectionFactory originalFactory = ConnectionFactories.get(options);
    ConnectionFactory proxyFactory = ProxyConnectionFactory.builder(originalFactory).build();
    
    return proxyFactory;
  }
}