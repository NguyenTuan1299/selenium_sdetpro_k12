package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import support.ui.SelectEx;

public class DropdownInteraction {
    private static final String TARGET_URL = "https://the-internet.herokuapp.com/dropdown";
    private static final By DROPDOWN_SEL = By.id("dropdown");

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getWebDriver();
        driver.get(TARGET_URL);
        try {
            // Locate and select the dropdown element
            WebElement dropdownEle = driver.findElement(DROPDOWN_SEL);
            SelectEx select = new SelectEx(dropdownEle);

            // by visible Text
            select.selectOption1();
            Thread.sleep(1500);

            // by index
            select.selectByIndex(2);
            Thread.sleep(1500);

            // by value
            select.selectByValue("1");
            Thread.sleep(1500);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

    }

}