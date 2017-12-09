package com.example.swagger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);

	@Bean
	public Docket swaggerSpringfoxDocket() {

		log.debug("Initializing swagger...");

		final StopWatch watch = new StopWatch();
		watch.start();

		final Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(apiPaths())
				.build()
				.securitySchemes(Lists.newArrayList(apiKey()))
				.apiInfo(apiInfo());
				

		watch.stop();
		log.debug("Swagger started in {} ms", watch.getTotalTimeMillis());
		return docket;
	}

	@SuppressWarnings("unchecked")
	private Predicate<String> apiPaths() {
		return Predicates.or(PathSelectors.regex("/api/.*"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Swagger 2 API documentation")
				.description("The reference documentation for KTS and NWT project.").build();
	}
	
	@Bean
	SecurityConfiguration security() {
		return new SecurityConfiguration(
				"test-app-client-id",
				"test-app-client-secret",
				"test-app-realm",
				"test-app",
				"",
				ApiKeyVehicle.HEADER,
				"X-Auth-Token",
				"," /*scope separator*/);
	}

	@Bean
	SecurityScheme apiKey() {
		return new ApiKey("token", "token", "header");
	}
}
