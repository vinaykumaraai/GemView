package com.luretechnologies.tms.app.security;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.UserSession;
import com.luretechnologies.tms.backend.data.Role;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.backend.service.MockUserService;

/**
 * 
 * @author Vinay
 *
 */
public class BackendAuthenticationProvider implements AuthenticationProvider {
	
	public static String username = null;
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException{
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
		username = String.valueOf(auth.getPrincipal());
		  String password = String.valueOf(auth.getCredentials());
			  
		  try {
			UserSession session = RestServiceUtil.getInstance().login(username, password);
			//RestServiceUtil.getInstance().getClient().getAuthApi().
			//com.luretechnologies.client.restlib.service.model.User restUser = RestServiceUtil.getInstance().getClient().getUserApi().getUserByUserName(username);
			//User user = new User(restUser.getEmail(),restUser.getUsername(),"",restUser.getRole().getName(),restUser.getFirstName(),restUser.getLastName(),restUser.getActive());
			//FIXME: Using mocked up data due to backend service not working
			
			User user = new User("test@test.com", "Admin", "Admin", Role.ADMIN, "Test", "Test", true); 
			return new UsernamePasswordAuthenticationToken(username,password, Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			if(e.getMessage().contains("INVALID LOGIN CREDENTIALS")) {
				System.out.println("Bad Credential for: "+username);
			}
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		// TODO Auto-generated method stub
		return  (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
	public String loggedInUserName() {
		return username;
	}

}
