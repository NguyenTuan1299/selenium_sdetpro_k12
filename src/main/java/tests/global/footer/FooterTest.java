package tests.global.footer;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import test_flows.global.FooterTestFlow;
import tests.BaseTest;

public class FooterTest extends BaseTest {

    @Test
    public void testHomePageFooter() {
        WebDriver driver = getDriver();
        driver.get("https://demowebshop.tricentis.com/");
        FooterTestFlow footerTestFlow = new FooterTestFlow(driver);
        footerTestFlow.verifyFooterComponent();
    }

    @Test
    public void testCategoryPageFooter() {
        WebDriver driver = getDriver();
        driver.get("https://demowebshop.tricentis.com/");
        FooterTestFlow footerTestFlow = new FooterTestFlow(driver);
        footerTestFlow.verifyProductCatFooterComponent();
    }

    @Test(groups = {"MicrosoftEdge"})
    public void veryImportant(){
        System.out.println("Running on safari");
        WebDriver driver = getDriver();
        driver.get("https://demowebshop.tricentis.com/");
        try{
            Thread.sleep(5000);
        } catch (Exception ignored){}
        Assert.assertTrue(true);
    }

}