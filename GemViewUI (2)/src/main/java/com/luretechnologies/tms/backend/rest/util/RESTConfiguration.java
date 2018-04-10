/**
 * 
 */
package com.luretechnologies.tms.backend.rest.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author sils
 *
 */
@Configuration
@ComponentScan(basePackageClasses = {RestClient.class})
@PropertySource("classpath:application.properties")
public class RESTConfiguration {
	 @Bean
	    public RESTServer restServer(Environment env)
	    {
	        return new RESTServer(env.getProperty("rest.user"),
	                env.getProperty("rest.password"),
	                env.getProperty("rest.host"));
	    }
}
