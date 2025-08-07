package com.ggv2.goldenglobe_renewal.global.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.ggv2.goldenglobe_renewal.domain")
@ComponentScan(basePackages = "com.ggv2.goldenglobe_renewal")
public class AppConfig {
}