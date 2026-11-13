package com.crm.javaUtility;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReport {

    public static ExtentReports report;
    public static ExtentTest test;

    public static void setupReport() {

        File folder = new File("./src/test/resources/TestReports");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        File reportFile = new File(folder, "OrangeHRMReport.html");

        System.out.println("Report Location: " + reportFile.getAbsolutePath());

        ExtentSparkReporter spark =
                new ExtentSparkReporter(reportFile.getAbsolutePath());

        report = new ExtentReports();

        report.attachReporter(spark);

        report.setSystemInfo("Project", "OrangeHRM");
        report.setSystemInfo("Browser", "Chrome");
    }
}