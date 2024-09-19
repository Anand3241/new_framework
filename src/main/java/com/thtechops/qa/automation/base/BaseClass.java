package com.thtechops.qa.automation.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseClass 
{
	
	public static WebDriver driver;
	public static Properties prop;
	public static WebDriverWait wait;
	public static String headless;
	

	public BaseClass() {

		// class constructor which maps a property variable to the config property file
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream("./src/main/resources/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Method to launch browser and redirect to the base URL.
	public void openBrowser() {

		// getting browser type from config file
		String browserName = prop.getProperty("browserName");
		// getting headless value from config file
		headless = prop.getProperty("headless");

		// launching browser based on browser type
		switch (browserName) {
		case "chrome":
			launchChrome();
			break;
		case "firefox":
			launchFirefox();
			break;
		case "edge":
			launchEdge();
			break;
		case "safari":
			launchSafari();
			break;
		}

		driver.manage().window().maximize();
		// to delete all cookies
		// driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

	}

	// method to Go To given URL
	public void goToURL(String urlToOpen) {
		driver.get(urlToOpen);
	
		

	}

	private void launchEdge() {
		// TODO Auto-generated method stub
		// This method is to open Edge browser for running automation scripts

		//WebDriverManager.edgedriver().setup();
		EdgeOptions option = new EdgeOptions();
		option.setCapability("ignore-certificate-errors", true);
		option.setCapability("disable-extension", true);
		driver = new EdgeDriver(option);
	

	}

	private void launchFirefox() {
		// TODO Auto-generated method stub
		// This method is to open Firefox browser for running automation scripts

		//WebDriverManager.firefoxdriver().setup();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		FirefoxOptions options = new FirefoxOptions();
		
		options.addArguments("--disable-notifications");
		capabilities.setCapability("ignore-certificate-errors", true);
		capabilities.setCapability("disable-extension", true);
		capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
		options.merge(capabilities);
		driver = new FirefoxDriver(options);
		

	}

	private void launchChrome() {
		// TODO Auto-generated method stub
		// This method is to open Chrome browser for running automation scripts
//		HashMap<String, Object> prefs = new HashMap<String, Object>();
//		prefs.put("safebrowsing.enabled", "false");
//		prefs.put("profile.default_content_settings.cookies", 2);
//
//		//WebDriverManager.chromedriver().setup();
//		ChromeOptions options = new ChromeOptions();
//		//options.setHeadless(Boolean.parseBoolean(headless));
//		//options.addArguments("--headless");
//		options.setExperimentalOption("prefs", prefs);
//		options.addArguments(
//				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36");
//		// this is to open chrome using chrome profile
//		// options.addArguments("user-data-dir=/Users/sakshi.jain/Library/Application
//		// Support/Google/Chrome/Profile 1");
//		options.addArguments("--incognito");
//		DesiredCapabilities capabilities = new DesiredCapabilities();
//		capabilities.setCapability("ignore-certificate-errors", true);
//		capabilities.setCapability("disable-extension", true);
//		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
	//	options.merge(capabilities);
		driver = new ChromeDriver();
	

	}

	private void launchSafari() {
		// TODO Auto-generated method stub
		// This method is to open Safari browser for running automation scripts

		SafariOptions options = new SafariOptions();
		options.setCapability("ignore-certificate-errors", true);
		options.setCapability("disable-extension", true);
		driver = new SafariDriver(options);
		
	}

}
