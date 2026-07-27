package com.qa.testintel.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ExtentTest test;

    // Creates the report file and sets it up (called once, at the very start)
    public static ExtentReports getReportInstance() {
        if (extent == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("reports/TestReport.html");
            sparkReporter.config().setDocumentTitle("AI Test Failure Intelligence Report");
            sparkReporter.config().setReportName("Automation Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
        }
        return extent;
    }

    // Creates a new entry in the report for one specific test
    public static ExtentTest createTest(String testName) {
        test = getReportInstance().createTest(testName);
        return test;
    }

    public static ExtentTest getTest() {
        return test;
    }
}