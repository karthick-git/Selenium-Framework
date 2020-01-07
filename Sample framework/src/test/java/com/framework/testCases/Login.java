package com.framework.testCases;

import java.io.IOException;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.framework.TestUtils.CsvUtils;
import com.framework.base.BaseClass;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class Login extends BaseClass {
private String sTestCaseName;
private int iTestCaseRow;
private CsvUtils csvUtils;
private ExtentReports extentReports;
private ExtentTest logger;

 public Login() throws IOException{
	super();
	PageFactory.initElements(driver, this);
}
 
 @BeforeMethod
 public void printTestCaseName() throws Exception {
	 sTestCaseName = this.toString();
	 sTestCaseName=sTestCaseName.substring(14, sTestCaseName.indexOf("0"));
	 System.out.println(sTestCaseName);
 }
 
 @Test
 private void login() throws Exception{
	 System.out.println("In login method");
	 Initialize(sTestCaseName);
	 extent.endTest(logger);
 }
 
 @AfterMethod
 public void closeMethod() throws Exception {
	 endTest(sTestCaseName);
 }
 
}
