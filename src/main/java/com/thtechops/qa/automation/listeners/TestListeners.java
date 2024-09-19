package com.thtechops.qa.automation.listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.thtechops.qa.automation.base.BaseClass;
import com.thtechops.qa.automation.utilities.HelperMethods;

public class TestListeners extends BaseClass implements ITestListener,ISuiteListener {

	public static ExtentTest parentTest;
	public static ExtentTest childTest;
	static HelperMethods helperMethods = new HelperMethods();
	static ExtentReports extent = ExtentReportTest.getInstance();
	
	@Override
	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		parentTest = extent.createTest(suite.getName());
		ExtentFactory.getInstance().setExtent(parentTest);
	}

	public void onTestStart(ITestResult result) {
		// before each test case
		childTest = parentTest.createNode(result.getMethod().getQualifiedName());
	}

	public void onTestSuccess(ITestResult result) {

		childTest.log(Status.PASS, "Test Case - " + result.getMethod().getMethodName() + " is Execution Complete.");
		
		try {

			Markup screenshotHtml = MarkupHelper.createLabel(screenShotEmbedHtml(getScreenshot()), ExtentColor.GREEN);
			childTest.info(screenshotHtml);
			ExtentFactory.getInstance().removeExtentObject();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void onTestFailure(ITestResult result) {
		
		childTest.log(Status.FAIL, "Test Case - " + result.getMethod().getMethodName() + " is Execution Failed.");
		childTest.fail(result.getThrowable());

		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// add screenshot for failed test.
		try {
			
			Markup screenshotHtml = MarkupHelper.createLabel(screenShotEmbedHtml(getScreenshot()), ExtentColor.RED);
			childTest.info(screenshotHtml);
			ExtentFactory.getInstance().removeExtentObject();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

}

	public void onTestSkipped(ITestResult result) {
		childTest.log(Status.SKIP,
			"Test Case: " + result.getMethod().getMethodName() + " is skipped.");
		ExtentFactory.getInstance().removeExtentObject();
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	public void onTestFailedWithTimeout(ITestResult result) {
	}

	public void onStart(ITestContext context) {

	}

	public synchronized static String screenShotEmbedHtml(String screenshot) {
		String html = null;
		screenshot = getScreenshot();
		html = "<a href=\"data:image/png;base64," + screenshot
				+ "\" data-featherlight=\"image\"><span class=\"label grey badge white-text text-white\">Screenshot-img</span></a>\n";
		return html;
	}

	private synchronized static String getScreenshot() {
		String screenshotBase64 = ((TakesScreenshot) helperMethods.getDriver()).getScreenshotAs(OutputType.BASE64);

		// TODO Auto-generated method stub
		return screenshotBase64;
	}

	public void onFinish(ITestContext context) {
		// close extent
		extent.flush();
	}
	
	@Override
	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		ISuiteListener.super.onFinish(suite);
	}
}
