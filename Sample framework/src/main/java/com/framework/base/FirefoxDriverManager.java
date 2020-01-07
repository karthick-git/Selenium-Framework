package com.framework.base;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.framework.TestUtils.Log;

public class FirefoxDriverManager extends DriverManager{
	
	public FirefoxDriverManager() throws IOException {
		super();
	}

	@Override
	public void createWebDriver() {
		System.setProperty(Pro.getProperty("FF_Key"),Pro.getProperty("FF_Path"));
		FirefoxOptions options = new FirefoxOptions();
		options.setCapability("marionette", true);
		options.setAcceptInsecureCerts(true);
		driver = new FirefoxDriver(options);
		Log.info("FireFox Browser is opened successfully");
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(Pro.getProperty("AppUrl"));
		driver.manage().timeouts().pageLoadTimeout(Long.parseLong(Pro.getProperty("PageLoadTime")), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Pro.getProperty("ImplicityTime")), TimeUnit.SECONDS);		
		Log.info("Application URL is launched successfully");
	}

}
