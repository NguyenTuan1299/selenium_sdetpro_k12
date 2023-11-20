package tests;

import driver.DriverFactory;
import models.pages.LoginPageModel03;
import org.openqa.selenium.WebDriver;

public class POMModel03Tests {

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getWebDriver();
        driver.get("https://");
        LoginPageModel03 loginPage = new LoginPageModel03(driver);
        loginPage
                .inputUsername("Teo@sth.com")
                .inputPassword("12345678")
                .clickOnLoginBtn();
    }
}