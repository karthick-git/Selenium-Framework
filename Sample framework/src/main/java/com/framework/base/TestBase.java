package com.framework.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.framework.TestUtils.Log;

public class TestBase {
	public static WebDriver driver;	
	public static Properties Pro;	
	public TestBase() throws IOException	{	
		Pro = new Properties();	
		FileInputStream FI = new FileInputStream("\\src\\main\\resources\\Env.properties");
		Pro.load(FI);
	}
		
		public static void Initialize(int iTestCaseRow, String Browsername) throws Exception	{
		Log.info("Creating a webdriver instance");	
		System.out.println(Browsername);	
		if(Browsername.equalsIgnoreCase("firefox"))		{	
			//Gecko driver has to be updated here 			
			System.setProperty(Pro.getProperty("FF_Key"),Pro.getProperty("FF_Path"));		
			FirefoxOptions options = new FirefoxOptions();		
			options.setCapability("marionette", true);		
			options.setAcceptInsecureCerts(true);		
			driver = new FirefoxDriver(options);		
			Log.info("FireFox Browser is opened successfully");	
			}		
		else if(Browsername.equalsIgnoreCase("Chrome"))		{	
			//Create instance for Chrome		
			System.setProperty(Pro.getProperty("Chrome_Key"),Pro.getProperty("Chrome_Path"));	
			ChromeOptions options = new ChromeOptions();	
			options.addArguments("start-maximized");		
			options.addArguments("disable-infobars");		
			options.addArguments("disable-extensions");		
			options.setExperimentalOption("useAutomationExtension", false);	
			driver = new ChromeDriver(options);	
			Log.info("Chrome Browser is opened successfully");		
			}					else if(Browsername.equalsIgnoreCase("IE"))		{	
				//IEDriver and IEOptions has to be updated			
				System.setProperty(Pro.getProperty("IE_Key"),Pro.getProperty("IEDriver_Path"));		
				DesiredCapabilities capabilitiesIE = DesiredCapabilities.internetExplorer();
				capabilitiesIE.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);	
				InternetExplorerOptions options = new InternetExplorerOptions();	
				options.merge(capabilitiesIE);			
				driver = new InternetExplorerDriver(options);	
				Log.info("IE Browser is opened successfully as a default browser");	
				}		
			else if (Browsername.contains("BrowserStack")) {    
				String USERNAME = Pro.getProperty("USERNAME");       
				String AUTOMATE_KEY = Pro.getProperty("AUTOMATE_KEY");    
				String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub"; 
				DesiredCapabilities caps = new DesiredCapabilities();          
				caps.setCapability("browser", "IE");    
				caps.setCapability("browser_version", "11.0");     
				caps.setCapability("os", "Windows");         
				caps.setCapability("os_version", "7");        
				caps.setCapability("resolution", "1920x1080");
				caps.setCapability("browserstack.local", "true");   
				driver = new RemoteWebDriver(new URL(URL), caps);     
				driver.manage().window().maximize();    
				}		
			else		{		
					//Create instance for Chrome by default		
				System.out.println("inside openBrowser");		
				System.setProperty(Pro.getProperty("Chrome_Key"),Pro.getProperty("Chrome_Path"));	
				ChromeOptions options = new ChromeOptions();	
				options.addArguments("start-maximized");		
				options.addArguments("disable-infobars");		
				options.setExperimentalOption("useAutomationExtension", false);	
				driver = new ChromeDriver(options);		
				Log.info("Chrome Browser is opened successfully as a default browser");		
				}					
		driver.manage().window().maximize();	
		driver.manage().deleteAllCookies();		
		//Should retrieve the URL based on the environment value provided in the DataSheet	
		//Script is to be updated	
		driver.get(Pro.getProperty("AppUrl"));	
		driver.manage().timeouts().pageLoadTimeout(Long.parseLong(Pro.getProperty("PageLoadTime")), TimeUnit.SECONDS);	
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Pro.getProperty("ImplicityTime")), TimeUnit.SECONDS);		
		Log.info("Application URL is launched successfully");	
		}		
	static String images;
	public static void captureScreenContent(WebDriver driver,String sTestCaseName) throws IOException, InvalidFormatException {     
		TakesScreenshot scrShot = ((TakesScreenshot) driver);   
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);   
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		String screenshot_name = System.nanoTime() + ".png";     
		Date date = new Date();    
		File file = new File(Pro.getProperty("BasePath") + "/Screenshots/"+"/"+sTestCaseName+"/"+dateFormat.format(date)+"/"+ screenshot_name);  
		FileUtils.copyFile(SrcFile, file);	
		System.out.println("Date time of the Test Run: "+dateFormat.format(date));    
		images = images + "<br><img src = \"" + Pro.getProperty("BasePath") + "/Screenshots/"+"/"+sTestCaseName+"/"+dateFormat.format(date)+"/"+ screenshot_name + "\">";
			} 
		public static void saveHTML(String TCName) throws IOException {    
			String fileName = TCName + "_" + System.currentTimeMillis() + ".html"; 
			File f = new File(Pro.getProperty("BasePath") + "/ValidationResults/" + fileName);  
			BufferedWriter bw = new BufferedWriter(new FileWriter(f)); 
			bw.write("<html>");  
			bw.write("<body>");  
			bw.write("<h1> Validation Results </h1>");  
			bw.write(images);      
			bw.write("</body>");   
			bw.write("</html>");   
			bw.close(); }
		
		
	}
