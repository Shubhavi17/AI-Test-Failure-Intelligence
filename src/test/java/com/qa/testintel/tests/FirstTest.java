package com.qa.testintel.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

@Listeners(com.qa.testintel.listeners.FailureListener.class)
public class FirstTest {

	public WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void verifyPageTitle() {
        driver.get("https://www.saucedemo.com/");
        String actualTitle = driver.getTitle();
        System.out.println("Page title is: " + actualTitle);
        Assert.assertEquals(actualTitle, "Swag Labs");
    }

    @Test
    public void verifyWrongElement() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(org.openqa.selenium.By.id("this-id-does-not-exist")).click();
    }
    @Test
    public void verifyWrongAssertion() {
        driver.get("https://www.saucedemo.com/");
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, "This Title Is Wrong On Purpose");
    }

    @Test
    public void verifyElementTimeout() {
        driver.get("https://www.saucedemo.com/");
        org.openqa.selenium.support.ui.WebDriverWait wait =
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(3));
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                .visibilityOfElementLocated(org.openqa.selenium.By.id("element-that-will-never-appear")));
    }

    @Test
    public void verifyLoginButtonExists() {
        driver.get("https://www.saucedemo.com/");
        boolean isDisplayed = driver.findElement(org.openqa.selenium.By.id("login-button")).isDisplayed();
        Assert.assertTrue(isDisplayed);
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}