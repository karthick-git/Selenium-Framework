package com.framework.base;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.framework.TestUtils.Log;

public class IEDriverManager extends DriverManager{

	public IEDriverManager() throws IOException {
		super();
	}

	@Override
	public void createWebDriver() {
		System.setProperty(Pro.getProperty("IE_Key"),Pro.getProperty("IEDriver_Path"));
		DesiredCapabilities capabilitiesIE = DesiredCapabilities.internetExplorer();
		capabilitiesIE.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		InternetExplorerOptions options = new InternetExplorerOptions();
		options.merge(capabilitiesIE);
		driver = new InternetExplorerDriver(options);
		Log.info("IE Browser is opened successfully as a default browser");
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(Pro.getProperty("AppUrl"));
		driver.manage().timeouts().pageLoadTimeout(Long.parseLong(Pro.getProperty("PageLoadTime")), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Pro.getProperty("ImplicityTime")), TimeUnit.SECONDS);		
		Log.info("Application URL is launched successfully");
		
	}

}
