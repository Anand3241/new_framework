package com.thtechops.qa.automation.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.thtechops.qa.automation.base.BaseClass;
import com.thtechops.qa.automation.utilities.HelperMethods;



public class LoginPage
{
	
	 WebDriver driver;
	
	@FindBy(xpath="//input[@placeholder='Username']")
	private WebElement username;
	
	@FindBy(xpath="//input[@placeholder='Password']")
	private WebElement password;
	
	@FindBy(xpath = "//button[@type='submit']")
	private WebElement login;
	
	
	
	
	
	
	
	public LoginPage(WebDriver driver)
	{
	     this.driver=driver;
		PageFactory.initElements(driver, this);
		
	}
	
	
	
	public void loginFunctionality() throws InterruptedException
	{
		try
		{
		Thread.sleep(5000);
		username.sendKeys("Admin");
		password.sendKeys("admin123");
		
		login.click();
		}
		catch (Throwable e) 
		{
			e.printStackTrace();
			// TODO: handle exception
		}
	}

}
