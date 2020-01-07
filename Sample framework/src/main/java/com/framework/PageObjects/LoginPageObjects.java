package com.framework.PageObjects;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.framework.CommonFunctions.CommonFunctions;
import com.framework.TestUtils.Log;
import com.framework.base.TestBase;

public class LoginPageObjects extends TestBase {
	public LoginPageObjects() throws IOException {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "")
	public WebElement username;
	@FindBy(xpath = "")
	public WebElement password;
	@FindBy(xpath = "")
	public WebElement signOnButton;
	

	public void login(String UN, String PW, String sTestCaseName) throws Exception {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		CommonFunctions.createWordDocument(sTestCaseName);
		// CommonFunctions.captureScreenContent(run, driver, "Login Page");
		CommonFunctions.captureScreenContent(driver, sTestCaseName);
		username.sendKeys(UN);
		password.sendKeys(PW);
		// CommonFunctions.captureScreenContent(run, driver, "Entering username and
		// password");
		CommonFunctions.captureScreenContent(driver, sTestCaseName);
		signOnButton.click();
		Log.info("Username and password is entered and clicked on sign on button successfully");
		String aTitle = VerifyPageTitle();
		System.out.println("The browser title is " + aTitle);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(signOnButton));
		Log.info("Logged in successfully");
		Assert.assertEquals(VerifyPageTitle(), "");

	}
}
