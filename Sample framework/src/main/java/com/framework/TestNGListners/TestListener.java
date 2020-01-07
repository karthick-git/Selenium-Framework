package com.framework.TestNGListners;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.framework.base.BaseClass;


public class TestListener extends BaseClass implements ITestListener {

	public TestListener() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}

	public void onTestFailure(ITestResult result) {
		System.out.println("Failed :" + result.getName() + " test has failed"+result.getThrowable());
		String methodName = result.getName().toString().trim();
		String path = System.getProperty("path");
		takeScreenShot(methodName,path+"/Screenshots"+"/Failed");
	}

	public void takeScreenShot(String methodName, String path) {

		File folder = new File(path);
		if (folder.exists()) {
			folder.delete();
		}

		String filePath = path+"/";
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(filePath + methodName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onStart(ITestContext context) {
		System.out.println("Test Execution Started on " + context.getStartDate());
	}

	public void onFinish(ITestContext context) {
		System.out.println("Test Execution Finished on " + context.getEndDate());
	}

	public void onTestStart(ITestResult result) {
		System.out.println(result.getName() + " test case started on" + getTime(result.getStartMillis()));
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("Passed :" + result.getName() + " test has Passed");
		String methodName = result.getName().toString().trim();
		String path = System.getProperty("path");
		takeScreenShot(methodName,path+"/Screenshots"+"/Passed");
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("Skipped :" + result.getName() + " test has Skipped "+result.getThrowable());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

}
