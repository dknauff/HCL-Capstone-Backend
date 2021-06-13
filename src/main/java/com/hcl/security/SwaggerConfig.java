package com.hcl.security;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

import static com.google.common.base.Predicates.or;

@EnableSwagger2
public class SwaggerConfig {
	
	public Docket postApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("User-API")
				.apiInfo(apiInfo())
				.select()
				.paths(postPaths())
				.build();
	}
	
	@SuppressWarnings("unchecked")
	private Predicate<String> postPaths() {
		return or(regex("/users"));
	}

	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Swagger Documentation")
				.description("Swagger reference for developers")
				.termsOfServiceUrl("https://swagger.com")
				.licenseUrl("kiran.gurung@hcl.com")
				.version("0.1")
				.build();
	}
}
