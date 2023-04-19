package com.conf;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class ApplicationConfiguration
{
  @Bean("conf.mainConf")
  public com.commerce.service.Configuration getConfiguration() {
    
    return com.commerce.service.Configuration.getInstance();
  }
  
  @Bean("db.mainDatasource")
  public DataSource dataSource(@Qualifier("conf.mainConf") com.commerce.service.Configuration conf) {
    return new DriverManagerDataSource(conf.getDBUrl(), conf.getDBUser(), conf.getDBPassword());
    
  }
}
