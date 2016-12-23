/*
package com.cs.test.swagger;

import com.wordnik.swagger.jaxrs.config.BeanConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

*/
/**
 * Created by admin on 2016/12/22.
 *//*

@Configuration
@EnableSwagger2
public class Swagger2 {

	private final static Logger log = LoggerFactory.getLogger(Swagger2.class);

	@Bean
	public Docket createRestApi() {
		log.info("Starting Swagger");
		StopWatch watch = new StopWatch();
		watch.start();
		Docket swaggerSpringMvcPlugin = new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.genericModelSubstitutes(ResponseEntity.class)
				.select()
				.paths(PathSelectors.regex("/rest/.*"))
				.build();
		watch.stop();
		log.info("Started Swagger in {} ms", watch.getTotalTimeMillis());


		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.2");
		beanConfig.setBasePath("http://localhost:8080/rest");
		beanConfig.setResourcePackage("io.swagger.resources");
		beanConfig.setScan(true);

		//ScannerFactory.setScanner(new DefaultJaxrsScanner());

		// Add the reader, which scans the resources and extracts the resource information
		//ClassReaders.setReader(new DefaultJaxrsApiReader());

		return swaggerSpringMvcPlugin;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Spring Boot中使用Swagger2构建RESTful APIs")
				.description("--------------")
				.contact("contact")
				.version("1.0")
				.build();
	}

}*/
