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
package com.luretechnologies.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class AutomationUtil {
	
	private final static String LOGIN_URL="http://localhost:8080/gemview/login.html";
	//private final Logger log = Logger.getLogger(AutomationUtil.class);

	public static WebDriver loadLoginPage(Logger log) throws IOException {
		WebDriver chromeDriver=null;
		try {
			Properties prop = new Properties();
			FileInputStream input = new FileInputStream("config.properties");
			prop.load(input);
			String location = prop.getProperty("location");
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Vinay\\eclipse-workspace\\AutomationTesting\\src\\main\\resource\\chromedriver.exe");
			chromeDriver = new ChromeDriver();
			chromeDriver.manage().window().maximize();
			chromeDriver.get(LOGIN_URL);
			assertSame("Gem View", chromeDriver.getTitle());
			return chromeDriver;
		}catch(AssertionError  e) {
			log.error("Application is Failed to Load");
			log.error(e.getStackTrace());
		}
		return chromeDriver;
		
	}
	
	public static WebElement findElementById(WebDriver chromeDriver, String id) {
		WebElement element = chromeDriver.findElement(By.id(id));
		return element;
	}
	
	public static WebElement findElementByName(WebDriver chromeDriver, String name) {
		WebElement element = chromeDriver.findElement(By.name(name));
		return element;
	}
	
	public static WebElement findElementByXpath(WebDriver chromeDriver, String xpath) {
		WebElement element = chromeDriver.findElement(By.xpath(xpath));
		return element;
	}
	
	public static int validateCredentials(WebDriver chromeDriver) {
		int error = chromeDriver.getCurrentUrl().indexOf("error");
		return error;
	}
	
	public static void waitTillElementLoads(final WebDriver chromeDriver, final String xpath) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(chromeDriver)
				 .withTimeout(30, TimeUnit.SECONDS)
		            .pollingEvery(5, TimeUnit.SECONDS)
		            .ignoring(NoSuchElementException.class);

		 WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
		        public WebElement apply(WebDriver driver) {
		            return AutomationUtil.findElementByXpath(chromeDriver, xpath);
		        }
	});
	}
}
