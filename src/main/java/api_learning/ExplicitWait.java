package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.ui.WaitForElementEnabled;

import java.time.Duration;

public class ExplicitWait {
    private static final String TARGET_URL = "https://the-internet.herokuapp.com/login";
    private static final By USERNAME_SEL = By.id("username");

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getWebDriver();
        driver.get(TARGET_URL);
        try {
            // Implicit wait: Applied globally for the whole session when finding element(s) | Interval time 500 milliseconds
            // Explicit wait: Applied locally/for a specific element | Interval time 500 milliseconds
            WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));

            // This one return TimeoutException if condition is not matched
            // webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("taolao")));

            // This one return NoSuchElementException if condition is not matched
//            webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("taolao"))));

            // Trigger action and verify another element disappears
//            try {
//                webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("taolao")));
//            } catch (TimeoutException timeoutException){
//                // Assert.fail('.....')
//            }

            // Using customized explicit wait class
            webDriverWait.until(new WaitForElementEnabled(By.cssSelector("#sth")));

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            driver.quit();
        }

    }

}