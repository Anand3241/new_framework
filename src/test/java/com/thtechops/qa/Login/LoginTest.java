package com.thtechops.qa.Login;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.thtechops.qa.automation.base.BaseClass;
import com.thtechops.qa.automation.pageObjects.LoginPage;
import com.thtechops.qa.automation.utilities.HelperMethods;
import com.thtechops.qa.automation.utilities.Logs;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class LoginTest
{
	
	
HelperMethods help = new HelperMethods();
	
	BaseClass base = new BaseClass();
	
	WebDriver driver;
	
	LoginPage login;
	

	
	@BeforeSuite
	public void suiteSetup() {
	
		base.openBrowser(); 
		//Creating Workbook
		help.makeURL();
		
		
	}
	
	
	@Test(priority = 0)
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify Help Page Title")
  
	public void _1_VerifyHelpPage() throws InterruptedException 
	{
		Reporter.log("This Method Verifies the Page title of the current page.");
		 driver = help.getDriver();
		 
			login = new LoginPage(driver);
		
		//verify user is redirected to Help Portal Home Page
			login.loginFunctionality();
		
	}
	
//	@AfterClass
//	public void endClassTest() {
//		//end Class test		
//		Logs.endTest();
//		help.exitDriver();
//	}

}
