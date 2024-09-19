package com.thtechops.qa.automation.utilities;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.thtechops.qa.automation.base.BaseClass;
import com.thtechops.qa.automation.listeners.TestListeners;

import io.qameta.allure.Step;

public class HelperMethods extends BaseClass{
	WebDriverWait wait;
	String parentWindow;
	static Workbook book;
	static Sheet sheet;
	static File file;
	static String testDataSheetPath = "./TestData File/TestDataFile.xlsx";
	static FormulaEvaluator objFormulaEvaluator;

	ArrayList<String> tabs;

	// Function to find an element on webpage
	public WebElement findElement(By locator) {

		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			WebElement element = driver.findElement(locator);
			// ((JavascriptExecutor)
			// driver).executeScript("arguments[0].scrollIntoView(true);", element);
			return element;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	// Function to scroll an element into view
	public void scrollElementInView(By locator) {
		WebElement element = findElement(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);

	}

	public String getScreenshot() {
		String screenshotBase64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

		// TODO Auto-generated method stub
		return screenshotBase64;
	}

	public synchronized String screenShotEmbedHtml(String screenshot) {
		HelperMethods helper = new HelperMethods();
		String html = null;
		screenshot = helper.getScreenshot();
		html = "<a href=\"data:image/png;base64," + screenshot
				+ "\" data-featherlight=\"image\"><span class=\"label grey badge white-text text-white\">Screenshot-img</span></a>\n";
		return html;
	}

	public void attachScreenShot() {
		Markup screenshotHtml = MarkupHelper.createLabel(screenShotEmbedHtml(getScreenshot()), ExtentColor.GREEN);

		TestListeners.childTest.info(screenshotHtml);

	}

	public void scrollUp() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(document.body.scrollHeight,0)");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		js.executeScript("window.scrollTo(0,0)");

	}

	// method to open URL from config properties file
	public void openURL(String urlExcelKey) {

		HashMap<String, String> extendurls = new HashMap<String, String>();

		extendurls.put("helpSite", prop.getProperty("helpSite"));
		extendurls.put("hocAdminOverview", prop.getProperty("hocAdminOverview"));
		extendurls.put("getInvolvedURL", prop.getProperty("getInvolvedURL"));
		extendurls.put("caveonURL", prop.getProperty("caveonURL"));
		extendurls.put("rangerURL", prop.getProperty("rangerURL"));
		extendurls.put("credentialProtectionURL", prop.getProperty("credentialProtectionURL"));
		extendurls.put("verificationURL", prop.getProperty("verificationURL"));
		extendurls.put("voucherRedemptionURL", prop.getProperty("voucherRedemptionURL"));
		extendurls.put("hocModule", prop.getProperty("hocModule"));
		extendurls.put("contentLibrary", prop.getProperty("contentLibrary"));
		extendurls.put("ExpensePortal", prop.getProperty("ExpensePortal"));
		try {
			String urlExcelvalue = extendurls.get(urlExcelKey);
			//makeURL(urlExcelvalue);
		} 
		catch (Exception e) 
		{
			Logs.logger.info("Extended Url Not found");
			e.printStackTrace();
		}

	}

	public void makeURL() {
		try {
			// String sheetName = prop.getProperty("environment");
			// Read sheet inside the workbook by its Environment name
			// String urlToOpen=getValueFromFileByKey(sheetName, urlExcelKey);
			
			String url = prop.getProperty("url");
			goToURL(url);
			Logs.logger.info("Navigated to '" + url + "' URL.");
		} catch (Exception e) {
			Logs.logger.info("Error in Navigating to given url");
			e.printStackTrace();
		}

	}

	// Function to find an element on webpage
	public List<WebElement> findAllElements(By locator) {

		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			List<WebElement> element = driver.findElements(locator);
			return element;
		} catch (Exception e) {
			Logs.warn("WebElement list Not found");
			return null;
		}
	}

	// Function to enter text within a text field
	public void enterText(By locator, String text) {
		WebElement element = findElement(locator);
		clickElement(locator);
		element.clear();
		element.sendKeys(text);

	}

	// Function to enter a string in one character after the other way
	public void enterTextCharacterWise(By locator, String text) {

		WebElement element = findElement(locator);
		Random r = new Random();
		for (int i = 0; i < text.length(); i++) {
			try {
				Thread.sleep((int) (r.nextGaussian() * 15 + 10));
			} catch (InterruptedException e) {
			}
			String s = new StringBuilder().append(text.charAt(i)).toString();
			element.sendKeys(s);
		}
	}

	public void assertionVerification(String expected, String actual) {

	}

	// Function to get config properties file value as per property name
	public String getConfigProperty(String propertyName) {
		return prop.getProperty(propertyName);

	}

	// Function to click on an element
	public void clickElement(By locator) {
//	        try
//	        {
		WebElement element = findElement(locator);
		for (int i = 0; i < 10; i++) {
			if ((element.isDisplayed() == true && element.isEnabled() == true) || element == null) {
				// element.click();
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
				break;
			}
			// Thread.sleep(1000);
		}
//	        }
//	        catch (Exception e)
//	        {
//	            Logs.logger.error("Element is not clickable");
//	        }
	}

	// Function to double click on an element
	public void doubleClickElement(By locator) throws InterruptedException {
		clickElement(locator);
		Thread.sleep(500);
		clickElement(locator);

	}

	// Function to get the text of an element
	public WebDriver getDriver() {
		return driver;
	}

	// Function to get the text of an element
	public String getElementText(By locator) {
		try {
			WebElement element = findElement(locator);
			return element.getText();
		} catch (Exception e) {
			Logs.logger.error("Unable to get text");
			return null;
		}
	}

	public void scrollToPageBottom() {

		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

	}

	// Function to select the value from the drop down menu
	public void selectItemFromDropDown(By dropDownLocator, String itemValue) {
		try {
			WebElement dropDownElement = findElement(dropDownLocator);
			Select selectDropDown = new Select(dropDownElement);
			itemValue = itemValue.trim();
			int index = 0;
			for (WebElement option : selectDropDown.getOptions()) {
				if (option.getText().equalsIgnoreCase(itemValue)) {
					selectDropDown.selectByIndex(index);
					break;
				}

				index++;
			}

		} catch (Exception e) {
			Logs.logger.error("Unable to select the requested value from the drop down");

		}
	}

	// Function to wait until the element is invisible
	public void waitUntilElementIsInvisible(By waitForElementLocator) {
		try {

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(waitForElementLocator));
		} catch (Exception e) {
			Logs.logger.error("Element is still visible");
		}
	}

	// Function to wait until the element is visible
	public boolean waitUntilElementIsVisible(By waitForElementLocator) {
		try {

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(waitForElementLocator));
			return true;
		} catch (Exception e) {
			Logs.logger.error("Element is not yet visible" + waitForElementLocator );
			return false;
		}
	}

	// Function to validate Presence of an element
	@Step("Verify the presence of locator {0}")
	public boolean verifyPrsenceOfElement(By locator) {
		try {
			boolean elementDisplayed = findElement(locator).isDisplayed();
			return elementDisplayed;
		} catch (Exception e) {
			Logs.logger.error("Element is not present on the page");
			return false;
		}

	}

	// Function to switch frame on the webpage
	public void switchFrame(By locator) {

		// driver.switchTo().defaultContent();
		WebElement frameElement = findElement(locator);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.switchTo().frame(frameElement);

	}

	// Function to switch frame on the webpage
	public void switchDefaultFrameContent() {

		driver.switchTo().defaultContent();

	}

	// Function to verify and return checkbox/radio button is selected status
	public boolean getIsSelectedValue(By locator) {
		WebElement element = findElement(locator);
		return (element.isSelected());

	}

	// Function to select or deselect a radio button/ checkbox
	public void toggleBtnSelectedValue(By locator) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(locator))
					.click();
		} catch (Exception e) {

			Logs.logger.error("Unable to select the requested Radio button/ Checkbox.");
		}
	}

	public static void takeScreenshot(String fileWithPath) throws IOException {
//		    try
//		    {
//				File scrFile = ((TakesScreenshot)base.).getScreenshotAs(OutputType.FILE);
//			//	FileUtils.copyFile(scrFile, new File(fileWithPath));
//		    }
//		    catch (Exception e)
//	        {
//	        //	Log.warn(e.getMessage());
//	        }

	}

	// Funtion to get the current focussed window handle
	public String getCurrentWindowHandle() {
		try {
			String currentWindowHandle = driver.getWindowHandle();
			return currentWindowHandle;

		} catch (Exception e) {
			Logs.logger.error("Can not identify current window handle");
			return null;
		}

	}

	// Funtion to get the handles of all the windows opened
	public Set<String> getAllWindowHandle() {
		try {
			Set<String> winHandles = driver.getWindowHandles();
			return winHandles;

		} catch (Exception e) {
			Logs.logger.error("Can not list all window handles");
			return null;
		}

	}

	// Function to close all the other windows except the main window.
	public boolean closeAllOtherWindows(String openWindowHandle) {
		try {
			Set<String> allWindowHandles = driver.getWindowHandles();
			for (String currentWindowHandle : allWindowHandles) {
				if (!currentWindowHandle.equals(openWindowHandle)) {
					driver.switchTo().window(currentWindowHandle);
					driver.close();
				}
			}

			driver.switchTo().window(openWindowHandle);
			if (driver.getWindowHandles().size() == 1)
				return true;
			else
				return false;

		} catch (Exception e) {
			Logs.logger.error("Can not close all window handles except main window");
			return false;
		}

	}

	public void hoverToElement(By locator) {
		WebElement element = findElement(locator);
		Actions action = new Actions(driver);

		// Performing the mouse hover action on the target element.
		action.moveToElement(element).perform();
	}

	// Funtion to close the current focussed window and switch focus to other window
	public void closeTabandSwitchtoDefault() {
		try {
			driver.close();
			/*
			 * at this point there is no focused window,we have to explicitly switch back to
			 * another desired window, which is passed as tab index
			 */
			// switching back to the original tab
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			Logs.logger.error("Issue in switching window focus");
		}

	}

	public void closeTabandSwitchtoDefault(String window) {
		try {
			driver.close();
			/*
			 * at this point there is no focused window,we have to explicitly switch back to
			 * another desired window, which is passed as tab index
			 */
			// switching back to the original tab
			driver.switchTo().window(window);
		} catch (Exception e) {
			Logs.logger.error("Issue in switching window focus");
		}

	}

	// Funtion to open new tab in current window
	public void openNewTab(int index) {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.open('about:blank','_blank');");

			tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(index));
			// driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"\t");
			// switchToTab();

		} catch (Exception e) {
			Logs.logger.error("Can not open new tab");
		}
	}

	// Funtion to switch tabs and focus on tab by index
	public void switchTabByIndex(int index) {
		try {

			tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(index));

		} catch (Exception e) {
			Logs.logger.error("Can not find tab index");
		}
	}

	// Fuction to switch to the next tab
	public void switchToTab() {
		try {
			// getting parent window
			parentWindow = driver.getWindowHandle();
			// get all the windows
			Set<String> allWindows = driver.getWindowHandles();
			// removing parent window,
			allWindows.remove(parentWindow);
			Iterator<String> ite = allWindows.iterator();
			// So now Set contains only new tab window only,so switch to it
			driver.switchTo().window((String) ite.next());
//			  //Switching between tabs using CTRL + tab keys.
//			  driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"\t");
//			  //Switch to current selected tab's content.
//			  driver.switchTo().window(tabs.get(2));

		} catch (Exception e) {
			Logs.logger.error("Cannot switch to the next tab.");
		}
	}

	// Function to create workbook on the basis of file extension (xls/xlsx)
	public void createWorkbook() {
		try {

			// Create an object of File class to open xlsx/xls file
			file = new File(testDataSheetPath);

			// Create an object of FileInputStream class to read excel file
			FileInputStream inputStream = new FileInputStream(file);
			book = null;

			// If it is xlsx file then create object of XSSFWorkbook class
			if (testDataSheetPath.contains(".xlsx")) {
				book = new XSSFWorkbook(inputStream);
				objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) book);
			}
			// If it is xls file then create object of HSSFWorkbook class
			else {
				book = new HSSFWorkbook(inputStream);
				objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) book);

			}

		} catch (Exception e) {

			Logs.logger.error("Unable to create workbook for the file at the given filepath.");

		}
	}

	// Function to read data from file on the basis of index
	public String getValueFromFileByIndex(String sheetName, int rowIndex, int colIndex) {
		try {
			// Read sheet inside the workbook by its name
			sheet = book.getSheet(sheetName);
			String val = sheet.getRow(rowIndex).getCell(colIndex).getStringCellValue();

			return val;
		} catch (Exception e) {
			Logs.logger.error("Cannot read data from the given workbook indexes");
			return null;
		}
	}

	public Map<String, List<String>> getMapData(String sheetName) {
		Map<String, List<String>> mapdata = new LinkedHashMap<>();
		List<String> newReasonsize = new ArrayList<>();
		Set<String> uniqueViews = new LinkedHashSet<>();

		List<String> setList = new ArrayList<>();
		List<String> topicList = new ArrayList<>();
		List<String> reasonList = new ArrayList<>();
		List<String> reasonSize = new ArrayList<>();
		List<String> topicSizeset = new ArrayList<>();
		// List<String> labelList = new ArrayList<>();
		DataFormatter formatter = new DataFormatter();
		Set<String> topicSize = new HashSet<>();
		List<String> viewsList = new CopyOnWriteArrayList<>();
		List<String> view1 = new ArrayList<>();
		List<String> view2 = new ArrayList<>();
		List<String> view3 = new ArrayList<>();
		List<String> view4 = new ArrayList<>();
		List<String> view5 = new ArrayList<>();
		List<String> view6 = new ArrayList<>();
		List<String> view7 = new ArrayList<>();
		List<String> view8 = new ArrayList<>();
		List<String> view9 = new ArrayList<>();
		List<String> view10 = new ArrayList<>();
		List<String> view11 = new ArrayList<>();
		List<String> flag = new ArrayList<>();

		List<List<String>> allviewList = new ArrayList<>();
		List<String> regionList = new ArrayList<>();

		// Map<String,List<String>> viewMap = new HashMap<>();
		sheet = book.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		for (int i = 1; i < rowCount + 1; i++)

		{
			Cell cell = sheet.getRow(i).getCell(0);
			if (cell != null)
			{
				
				String key = formatter.formatCellValue(cell);

				if (!(key.equals(" ") || key.isEmpty())) {
					flag.add(key);
				}

			}
			
			
			
			
			Cell cell1 = sheet.getRow(i).getCell(1);
			if (cell1 != null)
			{
				String key1 = cell1.getStringCellValue();
				

				if (!(key1.equals(" ") || key1.isEmpty())) {
					setList.add(key1);
				}

			}

			Cell cell2 = sheet.getRow(i).getCell(2);
			if (cell2 != null)
			{
				String key2 = cell2.getStringCellValue();

				if (!(key2.equals(" ") || key2.isEmpty())) {
					reasonList.add(key2);
				}

			}

			Cell cell3 = sheet.getRow(i).getCell(3);
			if (cell3 != null) {
				String key3 = formatter.formatCellValue(cell3);

				reasonSize.add(key3);

			}

			Cell cell5 = sheet.getRow(i).getCell(5);
			if (cell5 != null) {
				String key5 = formatter.formatCellValue(cell5);
				if (!(key5.equals(" ") || key5.isEmpty())) {
					viewsList.add(key5);
				}

			}
			uniqueViews.addAll(viewsList);

			Cell cell6 = sheet.getRow(i).getCell(6);
			if (cell6 != null) {
				String key6 = formatter.formatCellValue(cell6);
				if (!(key6.equals(" ") || key6.isEmpty())) {
					view1.add(key6);
				}

			}

			Cell cell7 = sheet.getRow(i).getCell(7);
			if (cell7 != null) {
				String key7 = formatter.formatCellValue(cell7);
				if (!(key7.equals(" ") || key7.isEmpty())) {
					view2.add(key7);
				}

			}

			Cell cell8 = sheet.getRow(i).getCell(8);
			if (cell8 != null) {
				String key8 = formatter.formatCellValue(cell8);
				if (!(key8.equals(" ") || key8.isEmpty())) {
					view3.add(key8);
				}

			}

			Cell cell9 = sheet.getRow(i).getCell(9);
			if (cell9 != null) {
				String key9 = formatter.formatCellValue(cell9);
				if (!(key9.equals(" ") || key9.isEmpty())) {
					view4.add(key9);
				}

			}

			Cell cell10 = sheet.getRow(i).getCell(10);
			if (cell10 != null) {
				String key10 = formatter.formatCellValue(cell10);
				if (!(key10.equals(" ") || key10.isEmpty())) {
					view5.add(key10);
				}

			}

			Cell cell11 = sheet.getRow(i).getCell(11);
			if (cell11 != null) {
				String key11 = formatter.formatCellValue(cell11);
				if (!(key11.equals(" ") || key11.isEmpty())) {
					view6.add(key11);
				}

			}

			Cell cell12 = sheet.getRow(i).getCell(12);
			if (cell12 != null) {
				String key12 = formatter.formatCellValue(cell12);
				if (!(key12.equals(" ") || key12.isEmpty())) {
					view7.add(key12);
				}

			}

			Cell cell13 = sheet.getRow(i).getCell(13);
			if (cell13 != null) {
				String key13 = formatter.formatCellValue(cell13);
				if (!(key13.equals(" ") || key13.isEmpty())) {
					view8.add(key13);
				}

			}
			Cell cell14 = sheet.getRow(i).getCell(14);
			if (cell14 != null) {
				String key14 = formatter.formatCellValue(cell14);
				if (!(key14.equals(" ") || key14.isEmpty())) {
					view9.add(key14);
				}

			}
			Cell cell15 = sheet.getRow(i).getCell(15);
			if (cell15 != null) {
				String key15 = formatter.formatCellValue(cell15);
				if (!(key15.equals(" ") || key15.isEmpty())) {
					view10.add(key15);
				}

			}

			Cell cell16 = sheet.getRow(i).getCell(16);
			if (cell16 != null) {
				String key16 = formatter.formatCellValue(cell16);

				if (!(key16.equals(" ") || key16.isEmpty())) {
					view11.add(key16);
				}

			}

			Cell cell17 = sheet.getRow(i).getCell(17);
			if (cell17 != null) {
				String key17 = formatter.formatCellValue(cell17);

				if (!(key17.equals(" ") || key17.isEmpty())) {
					regionList.add(key17);
				}

			}

			allviewList.add(view1);
			allviewList.add(view2);
			allviewList.add(view3);
			allviewList.add(view4);
			allviewList.add(view5);
			allviewList.add(view6);

		}

		Cell cell4 = sheet.getRow(1).getCell(4);
		String key4 = formatter.formatCellValue(cell4);
		topicSize.add(key4);
		topicSizeset.addAll(topicSize);
		Set<String> s = new LinkedHashSet<String>(setList);
		topicList.addAll(s);

		for (int i = 0; i < reasonSize.size(); i++) {
			if (!(reasonSize.get(i).equals(" ") || reasonSize.get(i).isEmpty())) {
				newReasonsize.add(reasonSize.get(i));
			}
		}

		mapdata.put("flag", flag);
		mapdata.put("Topics", topicList);
		mapdata.put("Reasons", reasonList);
		mapdata.put("TopicSize", topicSizeset);
		mapdata.put("ReasonSize", newReasonsize);
		mapdata.put("AllTopics", setList);
		mapdata.put("ViewList", viewsList);
		mapdata.put("Region", regionList);
		mapdata.put("View1", view1);
		mapdata.put("View2", view2);
		mapdata.put("View3", view3);
		mapdata.put("View4", view4);
		mapdata.put("View5", view5);
		mapdata.put("View6", view6);
		mapdata.put("View7", view7);
		mapdata.put("View8", view8);
		mapdata.put("View9", view9);
		mapdata.put("View10", view10);
		mapdata.put("View11", view11);
		// mapdata.put("View7", view1);

		return mapdata;

	}

	// Function to read data from file on the basis of key value
	public String getValueFromFileByKey(String sheetName, String key) {
//				        try
//				        {
		// Read sheet inside the workbook by its name
		sheet = book.getSheet(sheetName);
		DataFormatter objDefaultFormat = new DataFormatter();
		String val = "";
		key = key.trim();
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		// Create a loop over all the rows of excel file to read it
		for (int i = 0; i < rowCount + 1; i++) {
			Row row = sheet.getRow(i);
			if (row.getCell(0).getStringCellValue().trim().equals(key)) {
				// picking the next cell value in the same row
				Cell cellValue = row.getCell(1);
				// This will evaluate the cell, And any data type value of cell will return
				// string value
				objFormulaEvaluator.evaluate(cellValue);
				val = objDefaultFormat.formatCellValue(cellValue, objFormulaEvaluator);
				System.out.println(val);

				break;
			}

		}

		return val;
//				        }
//				        catch (Exception e)
//				        {
//				          Log.error("Cannot read data from the given workbook on the basis of given key");
//				        	
//				        	return null;
//				        }

	}

	// Function to close the current focussed window
	public void closeDriver() {
		driver.close();
	}

	// Function to close the program
	public void exitDriver() {
		driver.quit();
	}

	// Function to refresh current window
	public void refresh() {
		driver.navigate().refresh();
	}

	public void navigate_back() {
		driver.navigate().back();

	}

	public SearchContext getShadowRoot(WebElement shadowHost) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (SearchContext) js.executeScript("return arguments[0].shadowRoot", shadowHost);
	}

	public SearchContext getShadowElement(WebElement shadowHost, String cssOfShadowElement) {
		SearchContext shardowRoot = getShadowRoot(shadowHost);
		return shardowRoot.findElement(By.cssSelector(cssOfShadowElement));
	}

	// Function to send Report as attachment in email
	public void sendReportEmail() throws EmailException {

		String hostName = prop.getProperty("hostName");
		int smtpPort = Integer.parseInt(prop.getProperty("smtpPort"));
		String fromEmail = prop.getProperty("fromEmail");
		String fromPswd = prop.getProperty("fromPswd");
		String toEmail = prop.getProperty("toEmail");
		String reportSubject = prop.getProperty("reportSubject");
		String reportBody = prop.getProperty("reportBody");

		// Create the attachment
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(System.getProperty("user.dir") + "//target//surefire-reports//emailable-report.html");
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("");
		attachment.setName("Automation Test Report");

		// Create the email message
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(hostName);
		email.setSmtpPort(smtpPort);
		email.setAuthenticator(new DefaultAuthenticator(fromEmail, fromPswd));
		email.setSSLOnConnect(true);
		email.setFrom("automationtrailhead@gmail.com");
		email.setSubject(reportSubject);
		email.setMsg(reportBody);

		// send mail To each email mentioned in the toEmail String in config file
		ArrayList<String> toEmailList = new ArrayList<String>(Arrays.asList(toEmail.split("\\s*,\\s*")));
		for (int i = 0; i < toEmailList.size(); i++) {
			email.addTo((String) toEmailList.get(i));
			System.out.println("mail sent to -> " + toEmailList.get(i));
		}

		// add the attachment
		email.attach(attachment);

		// send the email
		email.send();

	}

	// This method returns URL of current focussed window
	public String getCurrentURL() {
		String url = driver.getCurrentUrl();
		return url;
	}

}