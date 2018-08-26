package com.aws.codestar.projecttemplates.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Spring configuration for sample application.
 */
@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

	/**
	 * Required to inject properties using the 'Value' annotation.
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
