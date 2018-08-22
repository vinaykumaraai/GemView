/**Soap
 * COPYRIGHT @ Lure Technologies, LLC.
 * ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or
 * form other than in accordance with and subject to the terms of a written
 * license from Lure or with the prior written consent of Lure or as
 * permitted by applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure.  If you are neither the
 * intended recipient, nor an agent, employee, nor independent contractor
 * responsible for delivering this message to the intended recipient, you are
 * prohibited from copying, disclosing, distributing, disseminating, and/or
 * using the information in this email in any manner. If you have received
 * this message in error, please advise us immediately at
 * legal@luretechnologies.com by return email and then delete the message from your
 * computer and all other records (whether electronic, hard copy, or
 * otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */
package com.luretechnologies.tms.backend.rest.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

//import com.luretechnologies.client.restlib.service.RestClientService;
//import com.luretechnologies.client.restlib.service.model.UserSession;

//import com.luretechnologies.client.restlib.service.RestClientService;
//import com.luretechnologies.client.restlib.service.model.UserSession;

/**
 * @author sils
 *
 */
public class TestRestService {
	//public void test(){
//		try {
//		RestClientService client = new RestClientService("http://mia.lure68.net:54061/admin/api","");
//		System.out.println("Login Sucess");
//		UserSession session = client.getAuthApi().login("vinay_standard", "TestPassword123!");
//		System.out.println("Login Sucess");
//		client.getUserApi().getUsers();
//		}catch (Exception e) {
//			System.out.println("Error while logging in" +e);
//		}
//	}

	public static void main(String...s) {
		try {
			RestClient.setContent("Some testing", "This is for testing");
			RestClient.sendMessage();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	

	
}
