package tests;

import driver.DriverFactory;
import models.pages.LoginPageModel01;
import org.openqa.selenium.WebDriver;

public class POMModel01Tests {

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getWebDriver();
        driver.get("https://sdetpro.com");
        LoginPageModel01 loginPage = new LoginPageModel01(driver);
        loginPage.username().sendKeys("email@sth.com");
        loginPage.password().sendKeys("12345678");
        loginPage.loginBtn().click();
    }
}