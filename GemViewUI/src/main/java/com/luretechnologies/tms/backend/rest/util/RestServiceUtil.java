package com.luretechnologies.tms.backend.rest.util;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.RestClientService;
import com.luretechnologies.client.restlib.service.model.UserSession;

/**
 * 
 * @author sils
 *
 */
public class RestServiceUtil {

	private static final RestServiceUtil INSTANCE = new RestServiceUtil();


	private static RestClientService client;
	private RestServiceUtil() {
		client = new RestClientService("http://mia.lure68.net:54061/admin/api","");
	}
	
	public static RestServiceUtil getInstance() {
		return INSTANCE;
	}
	
	public RestClientService getClient() {
		return client;
	}
	
	
	public UserSession login(String username,String password) throws ApiException {
				return client.getAuthApi().login(username, password);
	}
	
}
