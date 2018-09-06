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

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.luretechnologies.utils.AutomationUtil;

public class SystemTest {
	private final static Logger systemLog = Logger.getLogger(SystemTest.class);
	private final static String LOGIN_URL="http://localhost:8080/gemview/login.html";
	private static WebDriver chromeDriver = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		systemLog.info("Tests For Standard Login is Started");
		LoginTest loginTest = new LoginTest();
		try {
			
			Properties prop = new Properties();
			FileInputStream input = new FileInputStream("config.properties");
			prop.load(input);
			
			//FIXME: change the location in config.properties where you kept the driver.
			String location = prop.getProperty("location");
			System.setProperty("webdriver.chrome.driver",location);
			chromeDriver = new ChromeDriver();
			chromeDriver.manage().window().maximize();
			chromeDriver.get(LOGIN_URL);
			assertEquals("Gem View", chromeDriver.getTitle());
			systemLog.info("Step 1: Application launched Successfully "+LocalDateTime.now().toString());
		}catch(Exception  e) {
			systemLog.error("Application is Failed to Load",e);
			chromeDriver.quit();
		}
	
		loginTest.loginTest(chromeDriver);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		 chromeDriver.quit();
	}
	
	@Test
	public void systemCreateTest() throws InterruptedException{
		
		try {
			WebElement systemButton = AutomationUtil.findElementByXpath(chromeDriver, "//*[@id=\"system\"]/span/span[2]");
			systemButton.click();
			Thread.sleep(3000);
			systemLog.info("Clicked on System Screen Button");
		}catch(Exception e){
			systemLog.error("Error while clicking on System Screen Button", e);
		}
		
		try {
			List<String> data = Arrays.asList("Payment011", "This is Description for Payment001","Text",
					"Value 1 for Payment 001");
			
			WebElement create = AutomationUtil.findElementByXpath(chromeDriver, "//*[@id=\"content\"]/div/div/div/div/div/div/div[2]/div/div[3]/div/div[1]/div/div[1]");
			create.click();
			Thread.sleep(2000);
			systemLog.info("Clicked on System Screen Create Button");
			
			WebElement paramName = AutomationUtil.findElementById(chromeDriver, "systemParamName");
			paramName.click();
			paramName.sendKeys("Payment011");
			Thread.sleep(500);
			systemLog.info("Entered Data in Parameter Name");
			
			WebElement paramDescription = AutomationUtil.findElementById(chromeDriver, "systemParamDescription");
			paramDescription.click();
			paramDescription.sendKeys("This is Description for Payment001");
			Thread.sleep(500);
			systemLog.info("Entered Data in Description");
			
			WebElement paramType = AutomationUtil.findElementById(chromeDriver, "systemParamType");
			paramType.click();
			Actions action = new Actions(chromeDriver);
			action.moveToElement(paramType).click().sendKeys("Text").build().perform();
			Thread.sleep(1000);
			action.sendKeys(Keys.TAB).build().perform();
			systemLog.info("Selected value in type");
			
			WebElement paramValue = AutomationUtil.findElementById(chromeDriver, "systemParamValue");
			paramValue.click();
			paramValue.sendKeys("Value 1 for Payment 001");
			Thread.sleep(500);
			systemLog.info("Entered Data in Value");
			
			WebElement save = AutomationUtil.findElementById(chromeDriver, "systemSave");
			save.click();
			systemLog.info("Clicked on Save Button");
			Thread.sleep(500);
			
			WebElement grid = AutomationUtil.findElementById(chromeDriver, "systemGrid");
			List<WebElement> allRows = grid.findElements(By.tagName("tr"));
			int count =0;
			for (WebElement row : allRows) { 
			    List<WebElement> cells = row.findElements(By.tagName("td")); 
			    for (WebElement cell : cells) { 
			    	if(data.contains(cell.getText())) {
			    		count=count+1;
			    		
			    		if(count==4) {
			    			systemLog.info("Data is Saved into the Grid");
			    			systemLog.info("--All Tests Passed for saving Param into the Grid");
			    		}
			    	}
			    }
			}
			
		}catch(Exception e) {
			systemLog.error("Exception while creating a System param");
			systemLog.error("--Tests Failed");
		}
	}
	
}
