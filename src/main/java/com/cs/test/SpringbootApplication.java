package com.cs.test;

import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.jersey.config.JerseyJaxrsConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootApplication {

	private final static Logger log = LoggerFactory.getLogger(SpringbootApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean jerseyServlet() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/rest/*");
		// our rest resources will be available in the path /rest/*
		registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());

		registration.addInitParameter("api.version", "1.0.0");
		registration.addInitParameter("swagger.api.basepath", "http://localhost:8080/rest");
		registration.addInitParameter(SwaggerContextService.USE_PATH_BASED_CONFIG, "true");
		registration.addInitParameter("scan.all.resources", "true");
		registration.addInitParameter("ignore.routes", "");

		return registration;
	}

	@Bean
	public ServletRegistrationBean bootstrap(){
		ServletRegistrationBean registration = new ServletRegistrationBean(new JerseyJaxrsConfig(), "/ignore");
		return registration;
	}

}
