package com.thtechops.qa.automation.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportTest {
	public static ExtentReports extent;
	public static ExtentSparkReporter sparkReport;

	public static ExtentReports getInstance() {
		if (extent == null)
			setupExtentReport();
		return extent;
	}

	public static ExtentReports setupExtentReport() {
		//SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy HH-mm-ss");
		//Date date = new Date();

		//String actualDate = format.format(date);

		//String reportPath = System.getProperty("user.dir") + "/Reports/QAExecutionReport_" + actualDate + ".html";

		String reportPath = System.getProperty("user.dir") + "/Reports/Help&SupportAutomationReport.html";
		
		ExtentSparkReporter sparkReport = new ExtentSparkReporter(reportPath);

		extent = new ExtentReports();
		extent.attachReporter(sparkReport);

		sparkReport.config().setDocumentTitle("Help & Support Test Automation Report");
		sparkReport.config().setTheme(Theme.DARK);
		sparkReport.config().setReportName("Automation");
		sparkReport.config().setReportName("Help & Support Test Automation Report");

		// extent.setSystemInfo("Executed on Environment: ",
		// PropertiesOperations.getPropertyValueByKey("url"));
		// extent.setSystemInfo("Executed on Browser: ",
		// PropertiesOperations.getPropertyValueByKey("browser"));
		extent.setSystemInfo("Executed on OS: ", System.getProperty("os.name"));
		extent.setSystemInfo("Executed by User: ", System.getProperty("user.name"));
		extent.setSystemInfo("Java Version : ", System.getProperty("java.version"));

		return extent;
	}

}
