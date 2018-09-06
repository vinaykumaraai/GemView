package com.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.vaadin.spring.events.annotation.EnableEventBus;

import com.discovery.view.MainUI;


@SpringBootApplication(scanBasePackageClasses = { MainUI.class, Application.class})
@ComponentScan(basePackages={"com.discovery"})
@EnableEventBus
public class Application extends SpringBootServletInitializer {
	
	 public static void main(String[] args) {
		 SpringApplication.run(Application.class, args);
	  }

		@Override
		protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
			return application.sources(Application.class);
		}

}
