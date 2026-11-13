
package AdminModule;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.crm.BaseClass.HomePageBase;
import com.crm.FileUtility.ReadFromExcel;
import com.crm.javaUtility.GenerateDynamicData;
import com.crm.listeners.WithScreenshot;
import com.crm.pom.AdminPom;
import com.crm.pom.HomePagePom;

@Listeners(WithScreenshot.class)
public class Admin extends HomePageBase {

    String createdUsername;
    String createdEmployeeName;

    // TC_0020 - Create Admin
    @Test
    public void tc_0020() throws EncryptedDocumentException, IOException, InterruptedException {

        HomePagePom home = new HomePagePom(driver);
        home.admin().click();

        AdminPom admin = new AdminPom(driver);
        admin.addPlus().click();

        Actions act = new Actions(driver);

        act.click(admin.userRole())
           .keyDown(Keys.ARROW_DOWN)
           .keyDown(Keys.ENTER)
           .perform();

        act.click(admin.status())
           .keyDown(Keys.ARROW_DOWN)
           .keyDown(Keys.ENTER)
           .perform();

        String password = ReadFromExcel.single("Sheet1", 1, 2);
        admin.password().sendKeys(password);

        createdEmployeeName = ReadFromExcel.single("Sheet1", 1, 3);
        admin.employeeName().sendKeys(createdEmployeeName);

        Thread.sleep(2000);

        act.keyDown(Keys.ARROW_DOWN)
           .keyDown(Keys.ENTER)
           .perform();

        createdUsername = GenerateDynamicData.dynamicName(
            ReadFromExcel.single("Sheet1", 1, 4)
        );

        System.out.println(createdUsername);

        admin.username().sendKeys(createdUsername);
        admin.confirmPassword().sendKeys(password);
        admin.save().click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement result = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[text()='" + createdUsername + "']")
            )
        );

        Assert.assertTrue(result.isDisplayed(), "Admin is not created");

        Reporter.log("Admin is created Successfully....", true);
    }


    // TC_0021 - Search Admin by Username
    @Test(dependsOnMethods = "tc_0020")
    public void tc_0021() {

        HomePagePom home = new HomePagePom(driver);
        home.admin().click();

        AdminPom admin = new AdminPom(driver);

        admin.searchUsername().sendKeys(createdUsername);
        admin.searchButton().click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement result = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                    "//div[contains(@class,'oxd-table-row')]//div[normalize-space()='" + createdUsername + "']"
                )
            )
        );

        Assert.assertTrue(result.isDisplayed(), "Admin user not found");

        Reporter.log("Admin user searched successfully", true);
    }


    // TC_0022 - Search Admin with Invalid Username
    @Test
    public void tc_0022() {

        HomePagePom home = new HomePagePom(driver);
        home.admin().click();

        AdminPom admin = new AdminPom(driver);

        admin.searchUsername().sendKeys("WrongUser123456");
        admin.searchButton().click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement message = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[text()='No Records Found']")
            )
        );

        Assert.assertTrue(message.isDisplayed(), "No Records Found message is not displayed");

        Reporter.log("Invalid username search verified successfully", true);
    }


    // TC_0023 - Search Admin by Employee Name
    @Test(dependsOnMethods = "tc_0020")
    public void tc_0023() {

        HomePagePom home = new HomePagePom(driver);
        home.admin().click();

        AdminPom admin = new AdminPom(driver);

        admin.searchEmployeeName().sendKeys(createdEmployeeName);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement suggestion = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@role='option']")
            )
        );

        suggestion.click();

        admin.searchButton().click();

        WebElement result = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                    "//div[contains(@class,'oxd-table-row')]//div[normalize-space()='" + createdUsername + "']"
                )
            )
        );

        Assert.assertTrue(result.isDisplayed(), "Admin user not found by Employee Name");

        Reporter.log("Admin user searched successfully by Employee Name", true);
    }


    @Test
    public void tc_0024() {

        HomePagePom home = new HomePagePom(driver);
        home.admin().click();

        AdminPom admin = new AdminPom(driver);

        // Select User Role
        admin.searchUserRole().click();

        Actions act = new Actions(driver);
        act.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();

        // Click Search
        admin.searchButton().click();

        // Create wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until table rows are present
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[contains(@class,'oxd-table-row')]")
        ));

        // Get the updated table rows
        List<WebElement> rows = driver.findElements(
            By.xpath("//div[contains(@class,'oxd-table-row')]")
        );

        Assert.assertTrue(rows.size() > 0);

        System.out.println("Admin users searched successfully by User Role");
    }
    // TC_0025 - Reset Search Fields
    @Test
    public void tc_0025() {

        HomePagePom home = new HomePagePom(driver);
        home.admin().click();

        AdminPom admin = new AdminPom(driver);

        // Enter username
        admin.searchUsername().sendKeys("TestUser123");

        // Click Reset
        admin.resetButton().click();

        // Verify username field is cleared
        Assert.assertTrue(
            admin.searchUsername().getAttribute("value").isEmpty(),
            "Search field is not cleared"
        );

        Reporter.log("Search fields reset successfully", true);
    }


    // TC_0026 - Create Admin with Blank Mandatory Fields
    @Test
    public void tc_0026() {

        HomePagePom home = new HomePagePom(driver);
        home.admin().click();

        AdminPom admin = new AdminPom(driver);

        // Click Add
        admin.addPlus().click();

        // Click Save without entering details
        admin.save().click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> requiredMessages = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//span[text()='Required']")
            )
        );

        Assert.assertTrue(
            requiredMessages.size() > 0,
            "Required validation messages are not displayed"
        );

        Reporter.log("Blank mandatory field validation verified", true);
    }


    @Test(dependsOnMethods = "tc_0020")
    public void tc_0027() throws EncryptedDocumentException, IOException {

        HomePagePom hp = new HomePagePom(driver);
        hp.admin().click();

        AdminPom ap = new AdminPom(driver);

        ap.addPlus().click();

        ap.userRole().click();
        Actions act = new Actions(driver);
        act.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();

        ap.status().click();
        act.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();

        String employeeName = ReadFromExcel.single("Sheet1", 2, 3);
        ap.employeeName().sendKeys(employeeName);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@role='listbox']//span")
        )).click();

        // Reuse the username created in TC_0020
        ap.username().sendKeys(createdUsername);

        // Password is in column C (index 2)
        String password = ReadFromExcel.single("Sheet1", 1, 2);

        ap.password().sendKeys(password);
        ap.confirmPassword().sendKeys(password);

        ap.save().click();

        // Verify duplicate username message
        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[contains(@class,'oxd-input-field-error-message') and contains(.,'Already exists')]")
                )
        );

        Assert.assertTrue(errorMessage.isDisplayed());

        System.out.println("Duplicate username validation is displayed successfully.");
    }
}