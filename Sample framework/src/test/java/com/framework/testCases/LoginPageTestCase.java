package com.framework.testCases;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.xml.DOMConfigurator;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.framework.PageObjects.LoginPageObjects;
import com.framework.TestUtils.CsvUtils;
import com.framework.TestUtils.ExcelUtils;
import com.framework.TestUtils.Log;
import com.framework.base.TestBase;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LoginPageTestCase extends TestBase {

	private String sTestCaseName;
	public int iTestCaseRow;
	public LoginPageObjects loginPage;
	public ExtentReports extent;
	ExtentTest logger;
	CsvUtils csv;
	public static String uname;
	public static String pass;

	@Parameters("instance")
	public LoginPageTestCase(String instance) throws IOException {
		super();
		// Setting up the Test Data Excel file
		try {
			// Excel sheet to be given//
			ExcelUtils.setExcelFile(Pro.getProperty("Excel_File"), "Sheet1");
			sTestCaseName = csv.csv_value(49, 1);
//				// From above method we get long test case name including package and class name etc. //			
			// The below method will refine your test case name, exactly the name use have
			// used //
			sTestCaseName = ExcelUtils.getTestCaseName(this.toString());
//				System.out.println("Test Scenario Name is "+sTestCaseName);
			uname = CSV_Utils.csv_value(49, 3);
			System.out.println(uname);
			pass = CSV_Utils.csv_value(49, 4);
			System.out.println(pass);
			// Date
			DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy HHmmss");
			Date date = new Date();
			System.out.println("Date time of the Test Run: " + dateFormat.format(date));
			// FilePath//
			// File folder = new File("C:/Users/uiks83/Documents/OAM Recovery/DCE/On
			// Prem/Logs" + "/"+ sTestCaseName +"/"+instance+"/"+dateFormat.format(date));
			File folder = new File("H:/_AppSense_/My Documents/OAM Recovery/DCE/Logs" + "/" + sTestCaseName + "/"
					+ instance + "/" + dateFormat.format(date));
			if (folder.exists()) {
				System.out.println("Folder for this test scenario and instance already exists");
			}
//				String filePath = "C:/Users/uiks83/Documents/OAM Recovery/DCE/On Prem/Logs"+"/"+sTestCaseName+"/"+instance+"/"+dateFormat.format(date);
			String filePath = "H:/_AppSense_/My Documents/OAM Recovery/DCE/Logs" + "/" + sTestCaseName + "/" + instance
					+ "/" + dateFormat.format(date);
			// Setting system property
			System.setProperty("path", filePath);
			// Setting the configuration file for log
			// Provide Log4j configuration settings
			DOMConfigurator.configure("log4j.xml");

			// Fetching the Test Case row number from the Test Data Sheet
			// Case name to get the TestCase row from the Test Data Excel sheet
			//
			iTestCaseRow = ExcelUtils.getRowContains(sTestCaseName, instance);

			System.out.println("Row containing test Scenario in the sheet is " + iTestCaseRow);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Parameters("browser")
	@BeforeMethod
	public void setUp(String browser) throws Exception {
		extent = new ExtentReports(
				"\\\\Corp\\dfs\\EIS_Shared\\ga016\\nas\\ETI\\ETO\\SHARED\\GR Release Testing\\GR Partner specifics\\Infosys Specific\\OLB\\STOCDIVS_Projects\\PWM Cyclops\\DCE Selenium\\DCE\\ExtentReport\\ExtentReportResults.html",
				true);
		extent.loadConfig(new File(
				"\\\\Corp\\dfs\\EIS_Shared\\ga016\\nas\\ETI\\ETO\\SHARED\\GR Release Testing\\GR Partner specifics\\Infosys Specific\\OLB\\STOCDIVS_Projects\\PWM Cyclops\\DCE Selenium\\DCE\\extent-config.xml"));
		Thread.sleep(2000);
		System.out.println(browser);
		Initialize(iTestCaseRow, browser);
		loginPage = new LoginPageObjects();
	}

	/*
	 * @Test(priority=1) public void VerifyPageTitleTest() {
	 * Log.startTestCase("VerifyPageTitleTest"); String
	 * PageTitle=loginpage.VerifyPageTitle();
	 * Assert.assertEquals(PageTitle,"SunTrust Online Banking"); }
	 */ @Test
	public void LoginTest() throws Exception {
		logger = extent.startTest("Running Hamburger Menu Options Test");
		captureScreenContent(driver, sTestCaseName);
//			CommonFunctions.app_captureScreenShot(driver, CommonFunctions.screenCount++, sTestCaseName);
		Log.startTestCase(sTestCaseName);
//			System.out.println("UserName is "+ExcelUtils.getCellData(iTestCaseRow,"UserName"));
//			System.out.println("Password is "+ExcelUtils.getCellData(iTestCaseRow,"Password"));
//			System.out.println("IPAddress is "+ExcelUtils.getCellData(iTestCaseRow,"IPAddress"));
		loginPage.login(uname, pass, "");
		// Log.info("Click on MysettingsPage");
		Thread.sleep(5000);
		// homepage.NavigatePage("MoveMoney");
		// Log.info("MoveMoney page opened");
		String PageTitle = loginPage.VerifyPageTitle();
		Assert.assertEquals(PageTitle, "SunTrust Online Banking");
		Log.info("Title of the page is " + PageTitle);
		logger.log(LogStatus.PASS, "Hamburger Close icon clicked");
		captureScreenContent(driver, sTestCaseName);
//			CommonFunctions.app_captureScreenShot(driver, CommonFunctions.screenCount++, sTestCaseName);
		extent.endTest(logger);
		// CommonFunctions.RetrieveDataFromDB(driver,"corp\\uikm22","suntrust1");
	}

	/*
	 * public void EditSecurity() throws Exception {
	 * loginpage.Login(ExcelUtils.getCellData(iTestCaseRow,"UserName"),ExcelUtils.
	 * getCellData(iTestCaseRow,"Password"),ExcelUtils.getCellData(iTestCaseRow,
	 * "IPAddress")); Log.info("Click on MysettingsPage"); Thread.sleep(5000); }
	 */
	@AfterMethod
	public void CloseMethod() throws IOException {
		saveHTML(sTestCaseName);
		driver.quit();
		Log.info("Driver is closed successfully");
		Log.endTestCase(sTestCaseName);
		extent.flush();
		extent.close();
	}
}
