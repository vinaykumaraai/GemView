package com.luretechnologies.tms.app.security;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.tms.backend.data.Role;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.backend.service.MockUserService;

/**
 * 
 * @author sils
 *
 */
public class BackendAuthenticationProvider implements AuthenticationProvider {
	

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
		  String username = String.valueOf(auth.getPrincipal());
		  String password = String.valueOf(auth.getCredentials());
			  
		  try {
			RestServiceUtil.getInstance().login(username, password);
			//com.luretechnologies.client.restlib.service.model.User restUser = RestServiceUtil.getInstance().getClient().getUserApi().getUserByUserName(username);
			//User user = new User(restUser.getEmail(),restUser.getUsername(),"",restUser.getRole().getName(),restUser.getFirstName(),restUser.getLastName(),restUser.getActive());
			//FIXME: Using mocked up data due to backend service not working
			User user = new User("test@test.com", "Admin", "", Role.ADMIN, "Test", "Test", true); 
			return new UsernamePasswordAuthenticationToken(user,null, Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}
