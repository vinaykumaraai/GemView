package com.luretechnologies.tms.backend.rest.util;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.RestClientService;
import com.luretechnologies.client.restlib.service.model.UserSession;
import com.luretechnologies.tms.backend.service.RolesService;
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

/**
 * 
 * @author Vinay
 *
 */
public class RestServiceUtil {
	private final static Logger sessionLogger = Logger.getLogger(RestServiceUtil.class);

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
	
	public void updatePassword(String username,String currentPassword, String newPassword){
		try {
			client.getAuthApi().updatePassword(username, currentPassword, newPassword);
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			sessionLogger.error("API Error Occured while updating password",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			sessionLogger.error("Error Occured while updating password",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
	}
	
}
