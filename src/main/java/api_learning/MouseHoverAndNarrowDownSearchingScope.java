package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.NoSuchElementException;

public class MouseHoverAndNarrowDownSearchingScope {
    private static final String TARGET_URL = "https://the-internet.herokuapp.com/hovers";
    private static final By FIGURE_SEL = By.cssSelector(".figure");
    private static final By PROFILE_NAME_SEL = By.cssSelector(".figcaption h5");
    private static final By PROFILE_LINK_SEL = By.cssSelector(".figcaption a");

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getWebDriver();
        driver.get(TARGET_URL);
        try {
            /**
             * Searching globally - means from html tag
             * driver.findElement/s(locator)
             *
             *
             * Narrow down searching scope with the parent scope is now the element
             * WebElement.findElement/s(locator)
             *
             * */

            // Target parent elements
            List<WebElement> figureEles = driver.findElements(FIGURE_SEL);
            if (figureEles.isEmpty()) {
                throw new NoSuchElementException("No figures on the page");
            }

            // Mouse hover action
            Actions actions = new Actions(driver);
            for (WebElement figureEle : figureEles) {
                // Finding child elements
                WebElement profileNameEle = figureEle.findElement(PROFILE_NAME_SEL);
                WebElement profileLinkEle = figureEle.findElement(PROFILE_LINK_SEL);
                boolean isProfileNameDisplayed = profileNameEle.isDisplayed();
                boolean isProfileLinkDisplayed = profileLinkEle.isDisplayed();
                System.out.println("BEFORE | isProfileNameDisplayed: " + isProfileNameDisplayed);
                System.out.println("BEFORE | isProfileLinkDisplayed: " + isProfileLinkDisplayed);

                actions.moveToElement(figureEle).perform();
                isProfileNameDisplayed = profileNameEle.isDisplayed();
                isProfileLinkDisplayed = profileLinkEle.isDisplayed();
                System.out.println("Profile name: " + profileNameEle.getText());
                System.out.println("Profile link: " + profileLinkEle.getAttribute("href"));
                System.out.println("AFTER | isProfileNameDisplayed: " + isProfileNameDisplayed);
                System.out.println("AFTER | isProfileLinkDisplayed: " + isProfileLinkDisplayed);
                System.out.println("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

    }

}