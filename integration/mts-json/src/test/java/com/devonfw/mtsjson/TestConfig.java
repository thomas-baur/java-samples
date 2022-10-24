package com.devonfw.mtsjson;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.jackson.datatype.money.MoneyModule;

/**
 *
 * This configuration is required for correct mapping of Money data type.
 *
 */
@Configuration
public class TestConfig {
  @Bean
  public MoneyModule moneyModule() {

    return new MoneyModule();
  }

}
