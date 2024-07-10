package fr.diginamic.hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(
				new Info()
				.title("Titre de l'API")
				.version("1.0")
				.description("The City Data Management API offers streamlined access to comprehensive demographic and administrative data for cities across France. Designed for government agencies and urban planners, this API allows users to retrieve, manage, and analyze detailed information about city populations, municipal boundaries, and departmental affiliations. By leveraging real-time data processing, the API facilitates informed decision-making and efficient urban management.")
				.contact(
						new Contact()
						.name("Lysiane D.")
						.url("https://github.com/Lysianedon")));
	}
}
