package InterviewQuestion;

import lab_12.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HandleException {
    private static final By USERNAME_SEL = By.id("username");
    public static void main(String[] args) {

        WebDriver driver = DriverFactory.getWebDriver();
        List<WebElement> usernameEles = driver.findElements(USERNAME_SEL);
        if(!usernameEles.isEmpty()){
//            Assert.fail("The element ABC is on the page!");
        }

    }

}
