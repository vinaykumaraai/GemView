/**
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
package com.luretechnologies.tms.automationtesting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.luretechnologies.utils.AutomationUtil;

public class LoginTest {
	
	private final Logger loginLog = Logger.getLogger(LoginTest.class);
	private final static String LOGIN_URL="http://localhost:8080/gemview/login.html";
	private WebDriver chromeDriver = null;
	
	
	@BeforeTest
	public void startApplication() throws IOException {
		PropertyConfigurator.configure("log4j.properties");
		loginLog.info("Tests For Standard Login is Started");
		try {
			Properties prop = new Properties();
			FileInputStream input = new FileInputStream("config.properties");
			prop.load(input);
			String location = prop.getProperty("location");
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Vinay\\eclipse-workspace\\AutomationTesting\\src\\main\\resource\\chromedriver.exe");
			chromeDriver = new ChromeDriver();
			chromeDriver.manage().window().maximize();
			chromeDriver.get(LOGIN_URL);
			assertEquals("Gem View", chromeDriver.getTitle());
			loginLog.info("Step 1: Application launched Successfully "+LocalDateTime.now().toString());
		}catch(AssertionError  e) {
			loginLog.error("Application is Failed to Load");
			loginLog.error(e.getStackTrace());
			chromeDriver.quit();
		}
	}

	@Test
	public void Login_standard() throws InterruptedException, IOException {

		WebElement username = AutomationUtil.findElementByName(chromeDriver, "username");
		
		WebElement pasword = AutomationUtil.findElementByName(chromeDriver, "password");
		
		WebElement loginButton = AutomationUtil.findElementByXpath(chromeDriver, "//*[@id=\"button-submit\"]");
		
		username.sendKeys("vinay_standard");
		Thread.sleep(1000);
		loginLog.info("Step 2: Entered Username "+LocalDateTime.now().toString());
		
		pasword.sendKeys("TestPassword123!");
		Thread.sleep(1000);
		loginLog.info("Step 3:Entered Password "+LocalDateTime.now().toString());
		
		loginButton.click();
		Thread.sleep(5000);
	
		if(AutomationUtil.validateCredentials(chromeDriver)>0) {
			loginLog.info("Step 4: Entered Credentails are Wrong " + LocalDateTime.now().toString());
		}else {
			loginLog.error("Step 4: The Login is Sucessful at " + LocalDateTime.now().toString());
		}
		
		WebElement gemView = AutomationUtil.findElementByXpath(chromeDriver, "//*[@id=\"system\"]/span/span[2]");
		gemView.click();
		Thread.sleep(5000);
	}
	
	 @AfterTest
	    public void shutDownApplication() {
		 chromeDriver.quit();
	    }
}
