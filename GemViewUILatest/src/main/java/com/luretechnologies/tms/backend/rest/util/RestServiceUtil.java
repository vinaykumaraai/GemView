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
	
	private static final String ADMIN_API_URL = System.getProperty("ADMIN_API_URL", "http://mia.lure68.net:54071/admin/api");
	private static final String TMS_API_URL = System.getProperty("TMS_API_URL", "http://mia.lure68.net:54071/tms/api");


	private static UserSession SESSION;
	private static RestClientService client;
	private RestServiceUtil() {
		client = new RestClientService("https://mia.lure68.net:54072/admin/api", "https://mia.lure68.net:54072/tms/api");
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
	
	public void updatePassword(String username,String currentPassword, String newPassword) throws Exception {
		try {
			client.getAuthApi().updatePassword(username, currentPassword, newPassword);
		}catch (Exception e) {
			throw new Exception(e);
		}
	}
	
}
