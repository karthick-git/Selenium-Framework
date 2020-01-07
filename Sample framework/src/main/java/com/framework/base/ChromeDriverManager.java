package com.framework.base;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.framework.TestUtils.Log;


public class ChromeDriverManager extends DriverManager {
	
		public ChromeDriverManager() throws IOException {
		super();
		}

		@Override
		public void createWebDriver() {
		System.setProperty(Pro.getProperty("Chrome_Key"),Pro.getProperty("Chrome_Path"));
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("start-maximized");
		options.addArguments("disable-infobars");
		options.addArguments("disable-extensions");
		options.setExperimentalOption("useAutomationExtension", false);
		this.driver = new ChromeDriver(options);
		Log.info("Chrome Browser is opened successfully as a default browser");
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(Pro.getProperty("AppUrl"));
		driver.manage().timeouts().pageLoadTimeout(Long.parseLong(Pro.getProperty("PageLoadTime")), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Pro.getProperty("ImplicityTime")), TimeUnit.SECONDS);		
		Log.info("Application URL is launched successfully");
		
	}

}