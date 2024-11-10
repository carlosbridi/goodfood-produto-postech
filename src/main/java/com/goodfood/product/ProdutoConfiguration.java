package com.goodfood.product;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.goodfood.product.gateways.database")
public class ProdutoConfiguration {

}
