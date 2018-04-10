/**
 * 
 */
package com.luretechnologies.tms.backend.rest.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luretechnologies.tms.backend.data.entity.User;

/**
 * @author sils
 *
 */
@PropertySource("classpath:application.properties")
public class RestClient {
	/**
	 *
	 * This is going to setup the REST server configuration in the
	 * applicationContext you can see that I am using the new Spring's Java
	 * Configuration style and not some OLD XML file
	 *
	 */
	public static ApplicationContext context = new AnnotationConfigApplicationContext(RESTConfiguration.class);
	/**
	 *
	 * We now get a RESTServer bean from the ApplicationContext which has all the
	 * data we need to log into the REST service with.
	 *
	 */
	public static RESTServer restServer = context.getBean(RESTServer.class);

	public static void sendMessage() { /**
										 *
										 * Setting up data to be sent to REST service
										 *
										 */
		Map<String, String> argumentsMap = new HashMap<String, String>();
		argumentsMap.put("id", "JS01");
		try {

			/*
			 * 
			 * This is code to post and return a user object
			 * 
			 */

			RestTemplate rt = new RestTemplate();
			rt.getMessageConverters().add(new StringHttpMessageConverter());

			String uri = new String("http://" + restServer.getHost() + ":8080/springmvc-resttemplate-test/api/{id}");

			User u = new User();
			u.setName("Johnathan M Smith");

			User returns = rt.postForObject(uri, u, User.class, argumentsMap);

		} catch (HttpClientErrorException e) {
			/**
			 *
			 * If we get a HTTP Exception display the error message
			 */

			System.out.println(e.getResponseBodyAsString());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
