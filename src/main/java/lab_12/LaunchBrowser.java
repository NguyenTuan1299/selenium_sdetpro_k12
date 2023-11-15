package lab_12;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LaunchBrowser {

    public static void main(String[] args) {
        /*
         * 1. Check current browser version
         * 2. Find the compatible browser driver from central and then download
         * 3. Use it as browser driver
         * */
        WebDriver driver = DriverFactory.getWebDriver();

        // Open web page
        driver.get("https://sdetpro.com");

        // Close a window
        driver.close();

        // Quit the session
        driver.quit();
    }
}