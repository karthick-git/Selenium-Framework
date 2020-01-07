package com.framework.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {
	protected WebDriver driver;
	public static Properties Pro;
	
	public DriverManager() throws IOException {
		Pro = new Properties();
		FileInputStream FI = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\Env.properties");
		Pro.load(FI);
	}
	protected abstract void createWebDriver();
	
	public void quitWebDriver(){
		if(null!=driver){
			driver.quit();
			driver = null;
		}
	}
	
	public WebDriver getWebDriver() throws IOException {
		if(null == driver){
			createWebDriver();
		}
		return driver;
		
	}
	

}
