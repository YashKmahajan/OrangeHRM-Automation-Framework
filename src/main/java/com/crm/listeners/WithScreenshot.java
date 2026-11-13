package com.crm.listeners;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.crm.BaseClass.HomePageBase;
import com.crm.BaseClass.LoginBase;
import com.crm.javaUtility.GenerateDynamicData;
import com.crm.javaUtility.ExtentReport;

public class WithScreenshot implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {

        String name = result.getMethod().getMethodName();

        Reporter.log(name + " is onTestStart", true);

        ExtentReport.test = ExtentReport.report.createTest(name);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        String name = result.getMethod().getMethodName();

        Reporter.log(name + " is onTestSuccess", true);

        ExtentReport.test.pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        String name = result.getMethod().getMethodName();

        Reporter.log(name + " is onTestFailure", true);

        ExtentReport.test.fail(result.getThrowable());

        // Select the correct driver
        String className = result.getTestClass().getName();

        WebDriver driver;

        if (className.contains("Admin")) {
            driver = HomePageBase.driver;
        } else {
            driver = LoginBase.driver;
        }

        // Generate dynamic screenshot name
        String dynamicName =
                GenerateDynamicData.nameWithDateAndTime(name);

        try {

            TakesScreenshot ts = (TakesScreenshot) driver;

            File from = ts.getScreenshotAs(OutputType.FILE);

            File to = new File(
                    ".\\src\\test\\resources\\ScreenShot\\"
                    + dynamicName + ".png"
            );

            FileHandler.copy(from, to);

            // Attach screenshot to Extent Report
            ExtentReport.test.addScreenCaptureFromPath(
                    to.getAbsolutePath()
            );

            Reporter.log("Screenshot saved: " + dynamicName, true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        String name = result.getMethod().getMethodName();

        Reporter.log(name + " is onTestSkipped", true);

        ExtentReport.test.skip("Test Skipped");
    }

    @Override
    public void onStart(ITestContext context) {

        Reporter.log("onStart", true);

        ExtentReport.setupReport();
    }

    @Override
    public void onFinish(ITestContext context) {

    	 Reporter.log("onFinish", true);

    	    ExtentReport.report.flush();

    	    System.out.println("Extent Report Generated Successfully!");
    }
}