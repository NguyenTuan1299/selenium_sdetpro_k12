package tests;

import driver.DriverFactory;
import models.pages.BasePage;
import models.pages.HomePage;
import org.openqa.selenium.WebDriver;

public class POMModel04Test {

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getWebDriver();

        HomePage homePage = new HomePage(driver);
        //homePage.footerComp().doSomething();
    }
}