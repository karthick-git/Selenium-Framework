package com.framework.base;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.framework.TestUtils.Log;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
public class CommonFunctions extends TestBase {
	public static String screenshotFilePath;
	public static int screenCount=0;
	public static WebDriver driver;
	public static Log Log;
	
	public CommonFunctions() throws IOException 	{
		PageFactory.initElements(driver, this);
	}
	public static void ClickMethod(WebDriver driver,By by)	{
		try				{
			WebDriverWait wait = new WebDriverWait(driver, 20)	;
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			wait.until(ExpectedConditions.elementToBeClickable(by));
			driver.findElement(by).click();
			System.out.println("Element is present and clicked");
		}
		catch(Exception e)		{
			System.out.println(e.getStackTrace());
		}
			}
	public static void waitMethod(WebDriver driver,By by)	{
		try		{
			WebDriverWait wait = new WebDriverWait(driver, 30)	;
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			wait.until(ExpectedConditions.elementToBeClickable(by));
			System.out.println("Element is present");
		}
catch(NoSuchElementException e)		{
			System.out.println(e.getStackTrace());
		}
	}
//	public static void javasSriptClick (By by)	{
//		JavascriptExecutor js = (JavascriptExecutor)driver;
//			js.executeScript("arguments[0].click();", by);
//	}
	public static void ClickMethod_JScript(WebDriver driver,WebElement element)	{
		try		{
			JavascriptExecutor js;
			js= (JavascriptExecutor)driver;
			js.executeScript("arguments[0].scrollIntoView();", element);
			js.executeScript("arguments[0].click();", element);
			//js.executeScript("arguments[0].click();", element);
		}
catch(Exception e)		{
			System.out.println(e.getStackTrace());
		}
	}
	public static void sendKeys_JScript(WebDriver driver,String text,WebElement element)	{
		try		{
			JavascriptExecutor js;
			js= (JavascriptExecutor)driver;
			js.executeScript("arguments[0].scrollIntoView();", element);
			js.executeScript("arguments[0].setAttribute('value', '" + text +"')", element);
			//js.executeScript("arguments[0].click();", element);
		}
catch(Exception e)		{
			System.out.println(e.getStackTrace());
		}
	}
	public static void RetrieveDataFromDB(WebDriver driver, String username, String password) throws ClassNotFoundException, SQLException	{
	List<Map<String, String>> results = null;
	DriverManager.registerDriver(new SQLServerDriver());
	String dataBaseURL = "jdbc:sqlserver://10.7.94.175:1433;DatabaseName=OLB_UI_Log";
	//Loading the required JDBC Driver class	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	//Creating connection to the DB 	try 	{
		con = DriverManager.getConnection(dataBaseURL, username, password);
		//Executing SQL query and fetching the result		st = con.createStatement();
		String query = "Select top 10 * from dbo.CDFLog where message like order by EntryTime Desc";
		rs = st.executeQuery(query);
		while (rs.next()) 		{
			System.out.println(rs.getString("Message"));
			results= common_Map(rs);
			if (results.size()> 0) 			{
				System.out.println("DB contains data for the criteria");
			}
 else			{
				System.out.println(rs.getString("Message"));
			}
			results = null;
		}
	}

	public static void dataBaseRollBack(Connection connection) 	{
		try 		{
			if (connection != null) 			{
				connection.rollback();
			}
		}
catch (SQLException e) 		{
			e.printStackTrace();
		}
	}
	public static List<Map<String, String>>  common_Map(ResultSet rs) throws SQLException 	{
		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		try {
			if (rs != null) 			{
				ResultSetMetaData meta = rs.getMetaData();
				int numColumns = meta.getColumnCount();
				while (rs.next()) 				{
					Map<String, String> row = new HashMap<String, String>();
					for (int i = 1;
 i <= numColumns;
 ++i) 					{
						String name = meta.getColumnName(i);
						String value = null;
						Object object = rs.getObject(i);
						if (object == null) 						{
							System.out.print("null" + "\t");
							value = null;
						}
 else 						{
							System.out.print(object.toString() + "\t");
							value = object.toString();
						}
						row.put(name, value);
						System.out.println("");
					}
					results.add(row);
				}
			}
		}
 finally 		{
			rs.close();
		}
		return results;
        	}
	public static String commonMapRead(Map <String, String> results, String keyName) 	{
		String sValue = null;
		Map<String, String> map = results;
		for (Map.Entry<String, String> entry : map.entrySet()) 		{
			if (entry.getKey().toString().equals(keyName)) 			{
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				sValue= entry.getValue();
				return sValue;
			}
		}
		return sValue;
	}
	public static String commonMapReadWithKey(Map <Integer, String> results, Integer key) 	{
		String sValue = null;
		Map<Integer, String> map = results;
		for (Map.Entry<Integer, String> entry : map.entrySet()) 		{
			if (entry.getKey().equals(key)) 			{
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				sValue= entry.getValue();
				return sValue;
			}
		}
		return sValue;
	}
	public static void app_captureScreenShot(WebDriver driver,int sCount,String testCaseName){
//		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
		//		screenshotFilePath=System.getProperty("user.dir").concat("\\ Output\\ScreenShot\\").concat("\\").concat(testCaseName).concat("\\");
		String screenCount= Integer.toString(sCount);
		File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try{
			FileUtils.copyFile(srcFile, new File(screenshotFilePath.concat(screenCount).concat(".png")));
		}
catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
		public static void app_captureScreenShot(WebDriver driver,int sCount){
		 //screenshotFilePath=System.getProperty("user.dir").concat("\\ Output\\ScreenShot\\Deposits\\").concat(InputData.productType).concat("\\").concat(InputData.Browser).concat("\\").concat(timeStamp).concat("\\");
		 String screenCount= Integer.toString(sCount);
		 File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		 try{
			 FileUtils.copyFile(srcFile, new File(screenshotFilePath.concat(screenCount).concat(".png")));
		 }
catch(IOException e){
			 System.out.println(e.getMessage());
		 }
		}
	public static void takeScreenShot(String Screenname, WebDriver driver) 	{
		String LANpath = System.getProperty("path");
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
		Date date = new Date();
		System.out.println("Date time is "+dateFormat.format(date));
		File folder = new File(LANpath);
		if (folder.exists()) 		{
			folder.delete();
		}
		String filePath = LANpath+"/"+"Screenshots/";
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try 		{
			FileUtils.copyFile(scrFile, new File(filePath + Screenname + dateFormat.format(date)+ ".png"));
		}
 catch (IOException e) 		{
			e.printStackTrace();
		}
	}
//	public void BOUI_ConvertOLBTODCE(int iTestCaseRow)	{
//			Log.info("Function to convert OLB to DCE using BOUI");
//		try 		{
//			JavascriptExecutor js;
//			js= (JavascriptExecutor)driver;
//			js.executeScript("window.open('"+Pro.getProperty("bouiURL")+"','_blank');
//");
//			//driver.get("https://onlinebanking-itca.suntrust.com");
//			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
//			ListIterator<String> Li = tabs.listIterator();
//			Log.info("Window Handle is "+Li.next());
//			if (Li.hasNext())			{
//				Log.info("Window Handle is "+Li.next());
//			}
//      			Log.info("Size of the Window handles : " +tabs.size());
//			Log.info("Current URL is "+driver.getCurrentUrl());
//			if(tabs.size()>1){
//				driver.switchTo().window(tabs.get(1));
//				Log.info("Current URL is "+driver.getCurrentUrl());
//}
//			Log.info("BOUI - Convert OLB Client To DCE Client");
//			//driver.manage().timeouts().implicitlyWait(Long.parseLong(Pro.getProperty("ImplicityTime")), TimeUnit.SECONDS);
//			BOUI_UserID.sendKeys(Pro.getProperty("boui-username"));
//			BOUI_Password.sendKeys(Pro.getProperty("boui-password"));
//			BOUI_LoginButton.click();
//			Log.info("Username and Password is entered and clicked on Login button successfully");
//			//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//			//Clicking on New			BOUI_New.click();
//			//Clicking on Portfolio activity			BOUI_Portfolio.click();
//			//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//			Thread.sleep(2000);
//			Log.info("Attempting to switch to a frame");
//			driver.switchTo().frame("PegaGadget0Ifr");
//			Log.info("Switching to initial frame");
//			WebElement element =driver.findElement(By.xpath("//*[@id='iframeDiv']/iframe"));
//			driver.switchTo().frame(element);
//			Log.info("switch to a frame is successful");
//			//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//			//By SelectOption = By.cssSelector("#SearchByMethod");
//			//CommonFunctions.waitMethod(driver, SelectOption);
//			SelectFromDropDown(BOUI_SearchByMethod, "Party by TIN");
////			BOUI_TIN.sendKeys(ExcelUtils.getCellData(iTestCaseRow,"AECIN"));
//			//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//			BOUI_Search.click();
//			//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//			Thread.sleep(3000);
//			BOUI_BasicData.click();
//			//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//			Log.info("Is edit button enabled "+BOUI_UpdEntlmntCheck.isEnabled());
//			BOUI_UpdEntlmntCheck.click();
////			SelectFromDropDown(BOUI_ClientSegmentation, ExcelUtils.getCellData(iTestCaseRow,"UIAtribute_Color"));
////			SelectFromDropDown(BOUI_ClientEffSegExp, ExcelUtils.getCellData(iTestCaseRow,"UIAtribute_Size"));
////			SelectFromDropDown(BOUI_ClientPortalExp, ExcelUtils.getCellData(iTestCaseRow,"UIAtribute_Style"));
////			SelectFromDropDown(BOUI_ClientPlanExp, ExcelUtils.getCellData(iTestCaseRow,"UIAtribute_Type"));
//			Thread.sleep(2000);
//			BOUI_SUBMIT.click();
//			Thread.sleep(8000);
//			BOUI_SuccessMessage.isDisplayed();
////			if(BOUI_SuccessMessage.getText().contains(ExcelUtils.getCellData(iTestCaseRow,"BOUI_Message")))//			{
////				Log.info("Successfully updated");
////			}
////			else//			{
////				Log.info("Updation is not successful");
////			}
////			driver.switchTo().defaultContent();
//			//Actions act=new Actions(driver);
//			//String key = Keys.chord(Keys.CONTROL,"t");
//			//act.sendKeys(key).build().perform();
//		}
//catch (NoSuchElementException e) 		{
//			e.printStackTrace();
//		}
//		catch (Exception e) 		{
//			e.printStackTrace();
//		}
//	}
	public void SelectFromDropDown(WebElement element, String visibleText)	{
		try		{
			Thread.sleep(2000);
			Select dselect = new Select(element);
			dselect.selectByVisibleText(visibleText);
			//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		catch(Exception e)		{
			
			e.printStackTrace();
		}
	}
			public static void switchToWindow()	{
		for(String winHandle : driver.getWindowHandles())		{
			driver.switchTo().window(winHandle);
		}
	}
	public static void closeCurrentWindow()	{
				driver.close();
		}
	public static void ClickMethod(WebElement ele)	{
		try				{
						ele.click();
			System.out.println("Element is present and clicked");
		}
		catch(Exception e)		{
			System.out.println(e.getStackTrace());
		}
			}
	public static String getText(WebElement webElementName) 	{
				String text = null;
		//Log.info("Getting text from "+webElementName+"");
		try		{
			text = webElementName.getText();
		}
catch(Exception e)		{
		}
		//	Log.info("Text captured - "+text);
		return text ;
	}
	//Get the current time/ date	
	public static String now(String format) 	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat fm = new SimpleDateFormat();
		return fm.format(cal.getTime());
	}
	public static String yesterday(String format) 	{
					final Calendar cal = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		cal.add(Calendar.DATE, -1);
		String date = simpleDateFormat.format(cal.getTime());
		System.out.println(date);
		return date ;
	}
	public static String todayDate(String pattern)	{
		//String pattern = "MM/dd/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		System.out.println(date);
		return date ;
	}
	public static String getURL() 	{
				String url = null;
		Log.info("Getting URL");
		try		{
			url = driver.getCurrentUrl();
		}
catch(Exception e)		{
		}
			Log.info("URL captured - "+url);
		return url ;
	}
	public static Boolean isDisplayed(WebElement ele) 	{
				Boolean bool = null;
		Log.info("Verfying element exist");
		try		{
			ele.isDisplayed();
			Log.info("Element exist");
			bool=true;
		}
catch(Exception e)		{
			Log.info("Element not exist");
			bool= false;
		}
				return bool ;
	}}
//		}
