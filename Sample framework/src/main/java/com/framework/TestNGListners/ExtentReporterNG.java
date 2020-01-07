package com.framework.TestNGListners;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;	

public class ExtentReporterNG implements IReporter
{	
	private ExtentReports extent;
	
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory)
	{	String path = System.getProperty("path");
		extent = new ExtentReports(path + File.separator + "Extent.html", true);
		for(ISuite suite : suites)
		{
			Map<String, ISuiteResult> result = suite.getResults();
			extent = new ExtentReports(path + File.separator + "Extent.html", true);
			ITestContext context;
			for(ISuiteResult r : result.values())
			{			
				context = r.getTestContext();
				buildTestNodes(context.getPassedTests(), LogStatus.PASS);
				buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
				buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
			}
		}
		
		extent.flush();
		extent.close();	
	}
	
	private void buildTestNodes(IResultMap tests,LogStatus status)
	{
		ExtentTest test;
		
		if(tests.size() > 0)
		{
			for(ITestResult result : tests.getAllResults())
			{
				test = extent.startTest(result.getMethod().getMethodName());
				StringBuffer details = new StringBuffer();	
				
				Object[] instance = result.getParameters();
				for(int i=0; i< instance.length; i++)
				{
					details.append(instance[i]);
					
					if(!(i == instance.length - 1))
					{
						details.append(",");
					}
				}
				
				if(result.getThrowable() != null)
				{
					test.log(status,  result.getThrowable());
				} 
				else
				{
					test.log(status, "Test" + status.toString().toLowerCase() + "ed");
				}
				
				extent.endTest(test);
			}
		}
	}
}
