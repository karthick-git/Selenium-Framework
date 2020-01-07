package com.framework.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.WebDriver;

import com.framework.PageObjects.LoginPageObjects;
import com.framework.TestUtils.CsvUtils;
import com.framework.TestUtils.Log;
import com.relevantcodes.extentreports.ExtentReports;

public class BaseClass {
	public static DriverManager driverManager;
	public static WebDriver driver;
	public static Properties pro;
	public ExtentReports extent;
	public LoginPageObjects loginPage;
	public CsvUtils csvUtils;
	public CommonFunctions cf;
	
	public static XWPFDocument docx;
	public static FileOutputStream out;
	public static InputStream pic;
	public static XWPFRun run;	
	
	
	public BaseClass() throws IOException 
	{			
		pro = new Properties();
		FileInputStream FI = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\Env.properties");
		pro.load(FI);		
	}
	public void Initialize(String sTestCaseName) throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a dd-MMM-yyyy");
		Date date = new Date();
		System.out.println("Date time of the Test Run: "+dateFormat.format(date));
		String browser = pro.getProperty("browser");
		switch (browser.toLowerCase()) {
		case "chrome":
			driverManager = new ChromeDriverManager();
			break;
		case "firefox":
			driverManager = new FirefoxDriverManager();
			break;
		case "ie":
			driverManager = new IEDriverManager();
			break;
		default:
			driverManager = new ChromeDriverManager();
			break;
		}
		
		driver= driverManager.getWebDriver();
		extent = new ExtentReports (System.getProperty("user.dir")+"\\ExtentReports\\"+sTestCaseName +".html", true);
		extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
//		File folder = new File(System.getProperty("user.dir")+"/target/"+"/Screenshots/"+"/"+sTestCaseName+"/");
//		if (folder.exists()) 
//		{
//			System.out.println("Folder for this test scenario and instance already exists");
//		}
		csvUtils = new CsvUtils();
		csvUtils.csv(sTestCaseName);
		
		
	}
			
		public void createFolder(String sTestCaseName) throws Exception{
			DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a dd-MMM-yyyy");
			Date date = new Date();
			System.out.println("Date time of the Test Run: "+dateFormat.format(date));
			File folder = new File(System.getProperty("user.dir") + "/"+ sTestCaseName +"/"+dateFormat.format(date));
			if (folder.exists()) 
			{
				System.out.println("Folder for this test scenario and instance already exists");
			}
			
			String filePath = System.getProperty("user.dir") + "/"+sTestCaseName+"/"+dateFormat.format(date);
			System.setProperty("path", filePath);
			
			//Create word doc for screenshots
					
			// Setting the configuration file for log
			// Provide Log4j configuration settings
			DOMConfigurator.configure("log4j.xml");
		}
		
		public void endTest(String sTestCaseName) throws Exception {
			//CommonFunctions.saveFile();
			CommonFunctions.saveHTML(sTestCaseName);
			driver.quit();
			Log.info("Driver is closed successfully");
			Log.endTestCase(sTestCaseName);
			extent.flush();
			extent.close();
		}
		
	}
