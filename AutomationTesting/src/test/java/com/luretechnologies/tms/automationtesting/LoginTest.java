package com.luretechnologies.tms.automationtesting;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.luretechnologies.utils.AutomationUtil;

public class LoginTest {

	private final static Logger loginLog = Logger.getLogger(LoginTest.class);
	private final static String LOGIN_URL="http://localhost:8080/gemview/login.html";
	private static WebDriver chromeDriver = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		loginLog.info("Tests For Standard Login is Started");
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
			loginLog.info("Step 1: Application launched Successfully "+LocalDateTime.now().toString());
		}catch(Exception  e) {
			loginLog.error("Application is Failed to Load",e);
			chromeDriver.quit();
		}
	
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		 chromeDriver.quit();
	}

	@Test
	public void loginTest(WebDriver chromeDriver) throws InterruptedException {


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
	
		if(AutomationUtil.validateCredentials(chromeDriver)>0) {
			loginLog.error("Step 4: Entered Credentails are Wrong " + LocalDateTime.now().toString());
		}else {
			loginLog.info("Step 4: The Login is Sucessful at " + LocalDateTime.now().toString());
		}
		
		AutomationUtil.waitTillElementLoads(chromeDriver, "//*[@id=\"logout\"]/span/span[2]");
	}

}
