# OrangeHRM Automation Framework

A Selenium WebDriver automation framework developed using Java, TestNG and Maven to automate functional testing of the OrangeHRM web application.

## Technologies Used

- Java
- Selenium WebDriver
- TestNG
- Maven
- Apache POI
- Extent Reports
- Page Object Model (POM)
- Eclipse IDE

## Modules Covered

### 1. Login Module
- Valid login
- Invalid username
- Invalid password
- Blank username
- Blank password
- Blank login fields

### 2. Admin Module
- Admin user management
- Creating admin users
- Functional validation of admin operations

## Framework Features

- Page Object Model (POM)
- Data-driven testing using Excel
- TestNG annotations and DataProvider
- Configuration management using properties file
- Screenshot capture on test failure
- Extent HTML reporting
- Parallel execution using TestNG XML suite

## Project Execution

1. Clone or download this repository.
2. Import the project into Eclipse as an existing Maven project.
3. Allow Maven to download the required dependencies.
4. Update browser and application URL in `configuration.properties` if required.
5. Run the required TestNG test class or `parallel-suite.xml`.

## Application Under Test

OrangeHRM Open Source Demo

https://opensource-demo.orangehrmlive.com/

## Execution Evidence

Execution screenshots are available in the `TestExecutionReports` folder.