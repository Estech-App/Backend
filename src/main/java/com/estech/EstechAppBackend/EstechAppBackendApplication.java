package com.estech.EstechAppBackend;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EstechAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstechAppBackendApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Estech APP")
						.version("0.0.1")
						.description("--- Description ---"));
	}

}
