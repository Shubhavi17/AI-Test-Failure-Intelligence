package com.qa.testintel.listeners;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.testintel.analyzer.FailureAnalyzer;
import com.qa.testintel.reports.ExtentReportManager;
import com.qa.testintel.tests.FirstTest;

public class FailureListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        ExtentReportManager.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.getTest().log(Status.PASS, "Test passed");
        logResultToCsv(result.getMethod().getMethodName(), "PASS", null);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Object currentTestClass = result.getInstance();
        WebDriver driver = ((FirstTest) currentTestClass).driver;

        String testName = result.getMethod().getMethodName();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String baseFileName = testName + "_" + timestamp;

        Throwable error = result.getThrowable();
        String failureReason = FailureAnalyzer.getFailureReason(error);

        String screenshotPath = null;

        // 1. Save screenshot
        if (driver != null) {
            try {
                File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File destination = new File("screenshots/" + baseFileName + ".png");
                destination.getParentFile().mkdirs();
                Files.copy(screenshotFile.toPath(), destination.toPath());
                screenshotPath = destination.getAbsolutePath();
                System.out.println("Screenshot saved: " + screenshotPath);
            } catch (IOException e) {
                System.out.println("Failed to save screenshot: " + e.getMessage());
            }
        }

        // 2. Save error log
        try {
            File logFile = new File("logs/" + baseFileName + ".txt");
            logFile.getParentFile().mkdirs();

            StringWriter stringWriter = new StringWriter();
            error.printStackTrace(new PrintWriter(stringWriter));
            String fullStackTrace = stringWriter.toString();

            try (PrintWriter writer = new PrintWriter(new FileWriter(logFile))) {
                writer.println("Test Name: " + testName);
                writer.println("Failure Time: " + timestamp);
                writer.println("Exception Type: " + error.getClass().getName());
                writer.println("Exception Message: " + error.getMessage());
                writer.println("Failure Reason: " + failureReason);
                writer.println();
                writer.println("Full Stack Trace:");
                writer.println(fullStackTrace);
            }

            System.out.println("Error log saved: " + logFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Failed to save error log: " + e.getMessage());
        }

        // 3. Log into Extent Report
        ExtentTest extentTest = ExtentReportManager.getTest();
        extentTest.log(Status.FAIL, "Test failed: " + error.getMessage());
        extentTest.log(Status.FAIL, "Failure Reason: " + failureReason);
        if (screenshotPath != null) {
            try {
                extentTest.fail("Screenshot at failure",
                        com.aventstack.extentreports.MediaEntityBuilder
                                .createScreenCaptureFromPath(screenshotPath).build());
            } catch (Exception e) {
                System.out.println("Could not attach screenshot to report: " + e.getMessage());
            }
        }
        logResultToCsv(testName, "FAIL", failureReason);
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.getReportInstance().flush();
        System.out.println("Extent Report generated at: reports/TestReport.html");
    }
    private void logResultToCsv(String testName, String status, String failureReason) {
        try {
            File csvFile = new File("results/test_results.csv");
            boolean fileExists = csvFile.exists();
            csvFile.getParentFile().mkdirs();

            try (FileWriter csvWriter = new FileWriter(csvFile, true)) {
                if (!fileExists) {
                    csvWriter.append("TestName,Status,FailureReason,Timestamp\n");
                }
                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                csvWriter.append(testName).append(",")
                          .append(status).append(",")
                          .append(failureReason == null ? "" : failureReason).append(",")
                          .append(timestamp).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Failed to write CSV: " + e.getMessage());
        }
    }
}