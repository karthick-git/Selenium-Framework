package com.framework.CommonFunctions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.io.FileUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.framework.TestUtils.Log;
import com.framework.base.BaseClass;


public class CommonFunctions extends BaseClass 
{
		
	public CommonFunctions() throws IOException 
	{
		PageFactory.initElements(driver, this);
	}	
	
	public static void javasSriptClick (By by)
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;	
		js.executeScript("arguments[0].click();", by);
		Log.info("Element clicked by java script");
	}
	
	public static void ClickMethod_JScript(WebElement element,String message)
	{
	
		try
		{
			JavascriptExecutor js;
			js= (JavascriptExecutor)driver;
	        js.executeScript("arguments[0].scrollIntoView();", element);
	        captureScreenContent(run, driver, message);
	        js.executeScript("arguments[0].click();", element);
	        Log.info("Element clicked by java script");
		}catch(Exception e)
		{
			System.out.println(e.getStackTrace());
		}
	}
	
	public static List<Map<String, String>>  common_Map(ResultSet rs) throws SQLException 
	{
        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
        try {
            if (rs != null) 
            {
                ResultSetMetaData meta = rs.getMetaData();
                int numColumns = meta.getColumnCount();
                while (rs.next()) 
                {
                    Map<String, String> row = new HashMap<String, String>();
                    for (int i = 1; i <= numColumns; ++i) 
                    {
                        String name = meta.getColumnName(i);
                        String value = null;
                        Object object = rs.getObject(i);
                        if (object == null) 
                        {
                        	System.out.print("null" + "\t");
                        	value = null;
                        } else 
                        {
                         System.out.print(object.toString() + "\t");
                         value = object.toString();
                        }
                        row.put(name, value);
                        System.out.println("");
                    }
                    results.add(row);
                }
            }
        } finally 
        {
            rs.close();
        }
        return results;        
    }
	
	public static String commonMapRead(Map <String, String> results, String keyName) 
	{
		String sValue = null;
		
		Map<String, String> map = results;
		for (Map.Entry<String, String> entry : map.entrySet()) 
		{
			if (entry.getKey().toString().equals(keyName)) 
			{
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				sValue= entry.getValue();
				return sValue;
			}
		}

		return sValue;
	}
	
	public static String commonMapReadWithKey(Map <Integer, String> results, Integer key) 
	{
		String sValue = null;
		
		Map<Integer, String> map = results;
		for (Map.Entry<Integer, String> entry : map.entrySet()) 
		{
			if (entry.getKey().equals(key)) 
			{
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				sValue= entry.getValue();
				return sValue;
			}
		}

		return sValue;
	}
	
	public static void takeScreenShot(String Screenname, WebDriver driver) 
	{
		String LANpath = System.getProperty("path");
		//Date 
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
		Date date = new Date();
		System.out.println("Date time is "+dateFormat.format(date));
		File folder = new File(LANpath);
		if (folder.exists()) 
		{
			folder.delete();
		}

		String filePath = LANpath+"/"+"Screenshots/";
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try 
		{
			FileUtils.copyFile(scrFile, new File(filePath + Screenname + dateFormat.format(date)+ ".png"));
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void SelectFromDropDown(WebElement element, String visibleText)
	{
		try
		{			
			Select dselect = new Select(element);
			dselect.selectByVisibleText(visibleText);	
			Log.info(visibleText+"dropdown option is selected");
		}
		catch(Exception e)
		{
			Log.info("Exception caught");
			e.printStackTrace();
		}
		
	}
	
	public static void switchToWindow(String message,WebElement elem) throws Exception
	{	
//		String parentWindowHandler = driver.getWindowHandle(); 
//		String subWindowHandler = null;
//
//		Set<String> handles = driver.getWindowHandles();
//		Iterator<String> iterator = handles.iterator();
//		while (iterator.hasNext()){
//		    subWindowHandler = iterator.next();
//		    driver.switchTo().window(subWindowHandler);
//		    driver.manage().window().maximize();
//		    System.out.println(subWindowHandler);
////		    WebDriverWait wait = new WebDriverWait(driver, 20);
////			wait.until(ExpectedConditions.visibilityOf(elem));
//		}
//		captureScreenContent(run, driver, message);
//		driver.close();
		
//		String parentWindow= driver.getWindowHandle();
//		for(String winHandle : driver.getWindowHandles())
//		{
//			driver.switchTo().window(winHandle);
////			System.out.println(winHandle);
//			driver.manage().window().maximize();
//			WebDriverWait wait = new WebDriverWait(driver, 20);
//			wait.until(ExpectedConditions.visibilityOf(elem));
//		    
//		    
//		}
		String parentWindow = driver.getWindowHandle();
		
		
        Set<String> handles =  driver.getWindowHandles();
        for(String windowHandle  : handles)
           {
           if(!windowHandle.equals(parentWindow))
              {
              driver.switchTo().window(windowHandle);
              captureScreenContent(run, driver, message);
              driver.close();
              }
           }
	}
	
	public static void closeCurrentWindow()
	{		
		  driver.close();	
	}
	
	public static void ClickMethod(WebElement ele)
	{
		try		
		{			
			ele.click();
			System.out.println(ele+" is present and clicked");
		}
		catch(Exception e)
		{
			
		}		
	}
	
	public static String getText(WebElement webElementName) 
	{		
		String text = null;
		Log.info("Getting text from "+webElementName+"");
		
		try
		{
			text = webElementName.getText();
		}catch(Exception e)
		{
			
		}	
		Log.info("Text captured - "+text);
		return text ;
	}
	
		//Get the current time/ date
		public static String now(String format) 
		{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat fm = new SimpleDateFormat();
			return fm.format(cal.getTime());
		}
		
		public static String yesterday(String format) 
		{			
			final Calendar cal = Calendar.getInstance();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			cal.add(Calendar.DATE, -1);
			String date = simpleDateFormat.format(cal.getTime());
			System.out.println(date);
			return date ;
		}
		
		public static String todayDate(String pattern)
		{
			//String pattern = "MM/dd/yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());
			System.out.println(date);
			return date ;
		}
	
		public static String getURL() 
		{		
			String url = null;
			Log.info("Getting URL");
			
			try
			{
				url = driver.getCurrentUrl();
			}catch(Exception e)
			{
				
			}	
			Log.info("URL captured - "+url);
			return url ;
		}
		
		public static Boolean isDisplayed(WebElement ele) 
		{		
			Boolean bool = null;
			Log.info("Verfying "+ele+" exist");
			
			try
			{
				ele.isDisplayed();
				Log.info(ele +" exist");
				bool=true;
			}catch(Exception e)
			{
				Log.info(ele +" not exist");
				bool= false;
			}		
			return bool ;
		}
		
		public static void scrollToView(WebElement ele)
		{			
			try
			{
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
				Log.info("Scrolled to view "+ele);
			}catch(Exception e)
			{
				
			}			
		}
		
		public static void wait(WebElement ele)
		{
			WebDriverWait Wait = new WebDriverWait(driver, 30);
			try{
				
			}catch(Exception e)
			{
				Wait.until(ExpectedConditions.elementToBeClickable(ele));
			}				
		}
		
		public static void createWordDocument(String Testcase) throws Exception
		{
			BaseClass.docx = new XWPFDocument();
			BaseClass.run = docx.createParagraph().createRun();
			BaseClass.out = new FileOutputStream(new File(System.getProperty("user.dir")+"/target/"+"/Screenshots/"+Testcase+".docx"));
			
		}
		
		
		public static void captureScreenContent(XWPFRun run, WebDriver driver, String text) throws IOException, InvalidFormatException, org.apache.poi.openxml4j.exceptions.InvalidFormatException, InterruptedException
		{
			TakesScreenshot scrShot =((TakesScreenshot)driver);
			File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
			String screenshot_name = System.currentTimeMillis() + ".png";		
			File file = new File(System.getProperty("user.dir")+"/Temp/" + screenshot_name);
			FileUtils.copyFile(SrcFile, file);
			pic = new FileInputStream(System.getProperty("user.dir")+"/Temp/"+ screenshot_name);
	
			BaseClass.run.setText(text);		
			BaseClass.run.addPicture(pic, XWPFDocument.PICTURE_TYPE_PNG, screenshot_name, Units.toEMU(500), Units.toEMU(300));
			//run.addBreak();	
			pic.close();
			file.delete();	
			TimeUnit.SECONDS.sleep(1);
		}
		
		public static void selectByValue(WebElement element,String value){
			Select s = new Select(element);
			s.selectByValue(value);
		}
		
		public static void selectByIndex(WebElement element,int index){
			Select s = new Select(element);
			s.selectByIndex(index);
		}
		
//		public static void switchFrameToParentFrame(WebElement element,String message) throws Exception{
//			element.click();
//			captureScreenContent(run, driver, message);
//			driver.switchTo().parentFrame();
//		}
		
		public static void expWait(WebElement element,int timeInSecs){
			WebDriverWait wait = new WebDriverWait(driver, timeInSecs);
			wait.until(ExpectedConditions.visibilityOf(element));
		}
		
		public static void hover(WebElement element){
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
		}
		
		public static void saveFile() throws IOException
		{	
			BaseClass.docx.write(out);
			
			BaseClass.out.flush();		
			BaseClass.out.close();	
			BaseClass.docx.close();
		}
		
		public static String images;
		public static void captureScreenContent(WebDriver driver,String sTestCaseName) throws IOException, InvalidFormatException {
	        TakesScreenshot scrShot = ((TakesScreenshot) driver);
	        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
	        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	        String screenshot_name = System.nanoTime() + ".png";
	        Date date = new Date();
	        File file = new File(pro.getProperty("BasePath") + "/Screenshots/"+"/"+sTestCaseName+"/"+dateFormat.format(date)+"/"+ screenshot_name);
	        FileUtils.copyFile(SrcFile, file);
			System.out.println("Date time of the Test Run: "+dateFormat.format(date));
	        images = images + "<br><img src = \"" + pro.getProperty("BasePath") + "/Screenshots/"+"/"+sTestCaseName+"/"+dateFormat.format(date)+"/"+ screenshot_name + "\">";
	 //
		}

	 public static void saveHTML(String TCName) throws IOException {
	        String fileName = TCName + "_" + System.currentTimeMillis() + ".html";
	        File f = new File(pro.getProperty("BasePath") + "/ValidationResults/" + fileName);
	        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
	        bw.write("<html>");
	        bw.write("<body>");
	        bw.write("<h1> Validation Results </h1>");
	        bw.write(images);
	        bw.write("</body>");
	        bw.write("</html>");
	        bw.close();
	 }
		
}
