package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverFactory {

    private WebDriver driver;

    public static WebDriver getWebDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        // This is a good workaround to not enable password manger popup from chrome
        chromeOptions.addArguments("--incognito");
        WebDriver driver = new ChromeDriver(chromeOptions);

        /*
         * Global waiting strategy for the whole session when finding an element/elements
         * Interval time: 500ms
         * */
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15L));
        return driver;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--incognito");
            this.driver = new ChromeDriver(chromeOptions);
        }
        return this.driver;
    }

    public void closeBrowserSession() {
        if (driver != null) {
            driver.quit();
        }
    }
}