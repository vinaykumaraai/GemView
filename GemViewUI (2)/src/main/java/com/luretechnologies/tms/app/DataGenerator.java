package com.luretechnologies.tms.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.luretechnologies.tms.backend.UserRepository;
import com.luretechnologies.tms.backend.data.Role;
import com.luretechnologies.tms.backend.data.entity.User;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class DataGenerator implements HasLogger {


	private User baker;
	private User barista;

	@Bean
	public CommandLineRunner loadData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (hasData(userRepository)) {
				getLogger().info("Using existing database");
				return;
			}
			getLogger().info("Generating demo data");
			getLogger().info("... generating users");
			createUsers(userRepository, passwordEncoder);
			getLogger().info("Generated demo data");
		};
	}

	private boolean hasData(UserRepository userRepository) {
		return userRepository.count() != 0L;
	}

	
	private void createUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		baker = userRepository.save(new User("carlos@gmail.com", "carlos", passwordEncoder.encode("carlos"), Role.HR));
		User user = new User("serafin@gmail.com", "serafin", passwordEncoder.encode("serafin"), Role.IT);
		user.setLocked(true);
		barista = userRepository.save(user);
		user = new User("vinay@gmail.com", "Vinay", passwordEncoder.encode("admin"), Role.ADMIN);
		user.setLocked(true);
		userRepository.save(user);
	}
}
