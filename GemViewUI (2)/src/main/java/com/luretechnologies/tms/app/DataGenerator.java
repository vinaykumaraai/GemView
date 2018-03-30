package com.luretechnologies.tms.app;

import java.util.ArrayList;
import java.util.List;

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
		baker = userRepository.save(new User("carlos@gmail.com", "carlos", passwordEncoder.encode("carlos"), Role.HR, "carlos", "romero", true));
		User user = new User("serafin@gmail.com", "serafin", passwordEncoder.encode("serafin"), Role.IT, "Serafin", "Fuente", true);
		user.setLocked(true);
		barista = userRepository.save(user);
		user = new User("vinay@gmail.com", "Vinay", passwordEncoder.encode("admin"), Role.ADMIN, "Vinay", "Raai", true);
		user.setLocked(true);
		//TODO a static Global Hashmap
		userRepository.save(user);
		for(int j=1; j<=10;j++) {
			user = new User("Mock"+j+"@gmail.com", "Mock"+j, passwordEncoder.encode("admin"), Role.HR, "Vinay", "Raai", true);
			userRepository.save(user);
		}
	}
}
