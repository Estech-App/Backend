package com.estech.EstechAppBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class EstechAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstechAppBackendApplication.class, args);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Estech App API")
				.description("Spring REST API reference for Estech App")
				.version("1.0")
				.build();
	}

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("estech-api")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.estech.EstechAppBackend.controller"))
				.paths(PathSelectors.any())
				.build();
	}

}
