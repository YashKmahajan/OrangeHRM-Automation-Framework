package com.crm.BaseClass;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.crm.FileUtility.ReadFromProperty;

public class LoginBase {

	public static WebDriver driver;

	@BeforeClass
	public void precondition() throws IOException {

	    String browser = ReadFromProperty.readProperty("browser");
	    String url = ReadFromProperty.readProperty("url");

	    if (browser.equalsIgnoreCase("chrome")) {
	        driver = new ChromeDriver();
	    }
	    else if (browser.equalsIgnoreCase("edge")) {
	        driver = new EdgeDriver();
	    }
	    else if (browser.equalsIgnoreCase("firefox")) {
	        driver = new FirefoxDriver();
	    }
	    else if (browser.equalsIgnoreCase("safari")) {
	        driver = new SafariDriver();
	    }
	    else {
	        driver = new ChromeDriver();
	    }

	    driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

	    driver.get(url);
	}

	@AfterClass
	public void postCondition() {

	    if (driver != null) {
	        driver.quit();
         	}
	}
}
