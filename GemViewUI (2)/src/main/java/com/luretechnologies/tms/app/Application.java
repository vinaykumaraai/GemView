package com.luretechnologies.tms.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.vaadin.spring.events.annotation.EnableEventBus;

import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.ui.AppUI;

@SpringBootApplication(scanBasePackageClasses = { AppUI.class, Application.class})
//@EnableJpaRepositories(basePackageClasses = { UserRepository.class })
//@EntityScan(basePackageClasses = { User.class, LocalDateJpaConverter.class })
@ComponentScan(basePackages={"com.luretechnologies"})
@EnableEventBus
public class Application extends SpringBootServletInitializer {

	public static final String APP_URL = "/";
	public static final String LOGIN_URL = "/login.html";
	public static final String LOGOUT_URL = "/login.html?logout";
	public static final String LOGIN_FAILURE_URL = "/login.html?error";
	public static final String LOGIN_PROCESSING_URL = "/login";
	public static final String OTP_CODE_URL = "code.html";

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
}
