/**
 * 
 */
package com.luretechnologies.tms.backend.rest;

import java.util.List;
import java.util.TreeMap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.luretechnologies.tms.backend.data.entity.User;

/**
 * @author sils
 *
 */
public class UserRepository {

	private static UserRepository instance;
	private static TreeMap<String, User> userDataMap = new TreeMap<>();

	private UserRepository() {
	}

	public UserRepository getUserRepository() {
		if (instance == null)
			instance = new UserRepository();
		return instance;
	}

	public User findByEmail(String email) {
		if (userDataMap.containsKey(email))
			return userDataMap.get(email);
		else
			return null;
	}

	public Page<User> findByEmailLikeIgnoreCaseOrNameLikeIgnoreCaseOrRoleLikeIgnoreCase(String emailLike,
			String nameLike, String roleLike, Pageable pageable) {
		return null;
	}

	public long countByEmailLikeIgnoreCaseOrNameLikeIgnoreCase(String emailLike, String nameLike) {
		//FIXME work on logic
		return 0L;
	}

	public User save(User user) {
		if (userDataMap.containsKey(user.getEmail()))
			return null;
		else
			userDataMap.put(user.getEmail(), user);
		{
			return user;
		}
	}

	public long count() {
		return userDataMap.size();
	}

	public static TreeMap<String, User> getUserDataMap() {
		return userDataMap;
	}
	
	public List<User> findAll(){
		return (List<User>)userDataMap.values();
	}
}
