package ru.akkulov.kamilowa_hand_made.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:config.properties")
@Slf4j
public class DatasourceConfig {

  @Value("${datasource.url}")
  public String DATASOURCE_URL;

  @Value("${datasource.username}")
  public String DATASOURCE_USERNAME;

  @Value("${datasource.password}")
  public String DATASOURCE_PASSWORD;

  @Bean
  DSLContext dslContext() throws SQLException {
    Connection connection = DriverManager.getConnection(DATASOURCE_URL, DATASOURCE_USERNAME, DATASOURCE_PASSWORD);

    JooqRecordsGeneratorConfigUtils.generateJooqSqlRecords(DATASOURCE_URL, DATASOURCE_USERNAME, DATASOURCE_PASSWORD);

    return DSL.using(connection);
  }

}
