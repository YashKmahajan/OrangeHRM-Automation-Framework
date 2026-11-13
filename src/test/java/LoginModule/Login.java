
package LoginModule;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

import com.crm.BaseClass.LoginBase;
import com.crm.FileUtility.ReadFromExcel;
import com.crm.FileUtility.ReadFromProperty;
import com.crm.pom.LoginPom;
import com.crm.listeners.WithScreenshot;

@Listeners(WithScreenshot.class)
public class Login extends LoginBase {

    LoginPom login;

    @BeforeMethod
    public void openLoginPage() throws IOException {

        driver.manage().deleteAllCookies();
        driver.get(ReadFromProperty.readProperty("url"));

        login = new LoginPom(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOf(login.username()));
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws IOException {

        return ReadFromExcel.readLoginData();
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String testCase, String username,
                          String password, String expectedResult) {

        System.out.println("Executing Test Case: " + testCase);

        if (!username.isEmpty()) {
            login.username().sendKeys(username);
        }

        if (!password.isEmpty()) {
            login.password().sendKeys(password);
        }

        login.loginButton().click();

        if (expectedResult.equalsIgnoreCase("Dashboard")) {

            Assert.assertTrue(
                driver.getCurrentUrl().contains("dashboard"),
                testCase + " FAILED: Dashboard not displayed"
            );

        } else if (expectedResult.equalsIgnoreCase("Invalid Credentials")) {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement message = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//p[contains(@class,'oxd-alert-content-text')]")
                )
            );

            wait.until(
                ExpectedConditions.textToBePresentInElement(
                    message, "Invalid credentials"
                )
            );

            Assert.assertTrue(
                message.getText().contains("Invalid credentials"),
                testCase + " FAILED: Invalid credentials message not displayed"
            );

        } else if (expectedResult.equalsIgnoreCase("Required")) {

            Assert.assertTrue(
                driver.findElements(
                    By.xpath("//span[text()='Required']")
                ).size() > 0,
                testCase + " FAILED: Required message not displayed"
            );

        } else {

            Assert.fail("Invalid expected result: " + expectedResult);
        }

        System.out.println(testCase + " PASSED");
    }
}
