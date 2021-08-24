package com.diegunix.meep.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

  @Value("${spring.application.name}")
  private String appName;

  @Value("${swagger.api.title}")
  private String apiTitle;

  @Value("${swagger.api.description}")
  private String apiDescription;

  @Bean
  public OpenAPI customOpenApi() {
    return new OpenAPI().components(new Components()).info(apiInfo());
  }

  private Info apiInfo() {
    License license = new License();
    license.setUrl("https://github.com/springfox/springfox/blob/master/LICENSE");
    license.setName("Apache License Version 2.0");

    return new Info().title(apiTitle).description(apiDescription).termsOfService("http://swagger.io/terms/")
        .license(license).version("1.0.0");
  }

}