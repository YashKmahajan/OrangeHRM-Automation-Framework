
package com.crm.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdminPom {

    @FindBy(xpath = "//button[@class='oxd-button oxd-button--medium oxd-button--secondary']")
    private WebElement add_plus;

    @FindBy(xpath = "//div[@class='oxd-select-wrapper']")
    private WebElement user_role;

    @FindBy(xpath = "(//div[@class='oxd-select-wrapper'])[2]")
    private WebElement status;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement password;

    @FindBy(xpath = "//input[@placeholder='Type for hints...']")
    private WebElement employee_name;

    @FindBy(xpath = "(//input[@class='oxd-input oxd-input--active'])[2]")
    private WebElement username;

    @FindBy(xpath = "(//input[@type='password'])[2]")
    private WebElement confirm_password;

    @FindBy(xpath = "//button[@type='submit' and text()=' Save ']")
    private WebElement save;

    @FindBy(xpath = "//label[text()='Username']/following::input[1]")
    private WebElement searchUsername;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchButton;

    @FindBy(xpath = "//label[text()='Employee Name']/following::input[1]")
    private WebElement searchEmployeeName;

    // Search User Role
    @FindBy(xpath = "//label[text()='User Role']/following::div[contains(@class,'oxd-select-text-input')][1]")
    private WebElement searchUserRole;

    // Reset Button
    @FindBy(xpath = "//button[normalize-space()='Reset']")
    private WebElement resetButton;

    public AdminPom(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public WebElement addPlus() {
        return add_plus;
    }

    public WebElement userRole() {
        return user_role;
    }

    public WebElement status() {
        return status;
    }

    public WebElement password() {
        return password;
    }

    public WebElement employeeName() {
        return employee_name;
    }

    public WebElement username() {
        return username;
    }

    public WebElement confirmPassword() {
        return confirm_password;
    }

    public WebElement save() {
        return save;
    }

    public WebElement searchUsername() {
        return searchUsername;
    }

    public WebElement searchButton() {
        return searchButton;
    }

    public WebElement searchEmployeeName() {
        return searchEmployeeName;
    }

    public WebElement searchUserRole() {
        return searchUserRole;
    }

    public WebElement resetButton() {
        return resetButton;
    }
}
