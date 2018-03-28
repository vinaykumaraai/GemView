package com.luretechnologies.tms.backend.data.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// "User" is a reserved word in some SQL implementations
@Entity(name = "UserInfo")
public class User extends AbstractEntity {

	@NotNull
	@Size(min = 1, max = 255)
	@Column(unique = true)
	private String email;

	@NotNull
	@Size(min = 4, max = 255)
	private String password;

	@NotNull
	@Size(min = 1, max = 255)
	private String name;

	@NotNull
	@Size(min = 1, max = 255)
	private String role;
	
	@NotNull
	@Size(min = 1, max = 255)
	private String firstname;

	@NotNull
	@Size(min = 1, max = 255)
	private String lastname;
	
	private boolean active;

	private boolean locked = false;

	public User() {
		// An empty constructor is needed for all beans
	}

	public User(String email, String name, String password, String role, String firstname, String lastname, boolean active) {
		Objects.requireNonNull(email);
		Objects.requireNonNull(name);
		Objects.requireNonNull(password);
		Objects.requireNonNull(role);
		Objects.requireNonNull(firstname);
		Objects.requireNonNull(lastname);
		Objects.requireNonNull(active);

		this.email = email;
		this.name = name;
		this.password = password;
		this.role = role;
		this.firstname = firstname;
		this.lastname= lastname;
		this.active=active;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
