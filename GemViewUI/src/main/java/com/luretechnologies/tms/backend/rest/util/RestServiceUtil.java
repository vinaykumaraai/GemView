package com.luretechnologies.tms.backend.rest.util;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.RestClientService;
import com.luretechnologies.client.restlib.service.model.UserSession;

/**
 * 
 * @author Vinay
 *
 */
public class RestServiceUtil {

	private static final RestServiceUtil INSTANCE = new RestServiceUtil();


	private static UserSession SESSION;
	private static RestClientService client;
	private RestServiceUtil() {
		client = new RestClientService("http://mia.lure68.net:54071/admin/api", "http://mia.lure68.net:54071/tms/api");
	}
	
	public static UserSession getSESSION() {
		return SESSION;
	}

	public static RestServiceUtil getInstance() {
		return INSTANCE;
	}
	
	public RestClientService getClient() {
		return client;
	}
	
	
	public UserSession login(String username,String password) throws ApiException {
		SESSION = client.getAuthApi().login(username, password);
		 return SESSION;
	}
	
}
