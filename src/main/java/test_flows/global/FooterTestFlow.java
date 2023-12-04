package test_flows.global;

import models.components.global.TopMenuComponent;
import static models.components.global.TopMenuComponent.MainCatItem;
import static models.components.global.TopMenuComponent.SublistComponent;
import models.components.global.footer.*;
import models.pages.BasePage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FooterTestFlow {

    private final WebDriver driver;

    public FooterTestFlow(WebDriver driver) {
        this.driver = driver;
    }

    public void verifyFooterComponent() {
        BasePage basePage = new BasePage(this.driver);
        InformationColumnComponent informationColumnComp = basePage.footerComp().informationColumnComp();
        CustomerServiceColumnComponent customerServiceColumnComp = basePage.footerComp().customerServiceColumnComp();
        MyAccountColumnComponent myAccountColumnComp = basePage.footerComp().myAccountColumnComp();
        FollowUsColumnComponent followUsColumnComp = basePage.footerComp().followUsColumnComp();

        verifyInformationColumn(informationColumnComp);
        verifyCustomerServiceColumn(customerServiceColumnComp);
        verifyMyAccountColumn(myAccountColumnComp);
        verifyFollowUsColumn(followUsColumnComp);
    }

    public void verifyProductCatFooterComponent() {
        // Randomly pickup MainItem from TopMenuComponent
        BasePage basePage = new BasePage(driver);
        TopMenuComponent topMenuComponent = basePage.topMenuComp();
        List<MainCatItem> mainCatsElem = topMenuComponent.mainItemsElem();
        Assert.assertFalse(mainCatsElem.isEmpty(), "[ERR] There is no item on top menu");
        int randomMainItemIndex = new SecureRandom().nextInt(mainCatsElem.size());
        MainCatItem randomMainItemElem = mainCatsElem.get(randomMainItemIndex);
        String randomCatHref = randomMainItemElem.catItemLinkElem().getAttribute("href");
        randomMainItemElem.catItemLinkElem().click();

        // Get sublist(if any) then click on a random sub-item / MainItem (if has no sublist)
//        List<SublistComponent> sublistComps = randomMainItemElem.sublistComps();
//        System.out.println(sublistComps.size());
//        if(sublistComps.isEmpty()){
//            randomMainItemElem.catItemLinkElem().click();
//        } else {
//            int randomIndex = new SecureRandom().nextInt(sublistComps.size());
//            SublistComponent randomCatItemComp = sublistComps.get(randomIndex);
//            randomCatHref = randomCatItemComp.getComponent().getAttribute("href");
//            randomCatItemComp.getComponent().click();
//        }

        // Make sure we are on the right page | Wait until navigation is done
        try {
            WebDriverWait wait = randomMainItemElem.componentWait();
            wait.until(ExpectedConditions.urlContains(randomCatHref));
        } catch (TimeoutException ignored){
            Assert.fail("[ERR] Target page is not matched");
        }

        // Call common verify method
        verifyFooterComponent();
    }

    private void verifyInformationColumn(FooterColumnComponent informationColumnComp) {
        List<String> expectedLinkTexts =
                Arrays.asList("Sitemap", "Shipping & Returns", "Privacy Notice", "Conditions of Use", "About us", "Contact us");
        List<String> expectedHrefs =
                Arrays.asList("/sitemap", "/shipping-returns", "/privacy-policy", "/conditions-of-use", "/about-us", "/contactus");
        expectedHrefs.replaceAll(originHref -> "https://demowebshop.tricentis.com" + originHref);
        testFooterColumn(informationColumnComp, expectedLinkTexts, expectedHrefs);
    }

    private void verifyCustomerServiceColumn(FooterColumnComponent customerServiceColumn) {
        List<String> expectedLinkTexts =
                Arrays.asList("Search", "News", "Blog", "Recently viewed products", "Compare products list", "New products");
        List<String> expectedHrefs =
                Arrays.asList("/search", "/news", "/blog", "/recentlyviewedproducts", "/compareproducts", "/newproducts");
        expectedHrefs.replaceAll(originHref -> "https://demowebshop.tricentis.com" + originHref);
        testFooterColumn(customerServiceColumn, expectedLinkTexts, expectedHrefs);
    }

    private void verifyMyAccountColumn(FooterColumnComponent myAccountColumn) {
        List<String> expectedLinkTexts =
                Arrays.asList("My account", "Orders", "Addresses", "Shopping cart", "Wishlist");
        List<String> expectedHrefs =
                Arrays.asList("/customer/info", "/customer/orders", "/customer/addresses", "/cart", "/wishlist");
        expectedHrefs.replaceAll(originHref -> "https://demowebshop.tricentis.com" + originHref);
        testFooterColumn(myAccountColumn, expectedLinkTexts, expectedHrefs);
    }

    private void verifyFollowUsColumn(FooterColumnComponent followUsColumn) {
        List<String> expectedLinkTexts =
                Arrays.asList("Facebook", "Twitter", "RSS", "YouTube", "Google+");
        List<String> expectedHrefs = Arrays.asList("http://www.facebook.com/nopCommerce",
                "https://twitter.com/nopCommerce",
                "https://demowebshop.tricentis.com/news/rss/1",
                "http://www.youtube.com/user/nopCommerce",
                "https://plus.google.com/+nopcommerce");
        testFooterColumn(followUsColumn, expectedLinkTexts, expectedHrefs);
    }

    private void testFooterColumn(FooterColumnComponent footerColumnComp, List<String> expectedLinkTexts, List<String> expectedHrefs) {
        List<String> actualLinkTexts = new ArrayList<>();
        List<String> actualHrefs = new ArrayList<>();

        footerColumnComp.linksEle().forEach(columnItem -> {
            actualLinkTexts.add(columnItem.getText());
            actualHrefs.add(columnItem.getAttribute("href"));
        });
        if (actualLinkTexts.isEmpty() || actualHrefs.isEmpty()) {
            Assert.fail("Footer column texts OR hyperlinks is empty!");
        }
        Assert.assertEquals(actualLinkTexts, expectedLinkTexts, "[ERR] Footer column link texts are different");
        Assert.assertEquals(actualHrefs, expectedHrefs, "[ERR] Footer column hrefs are different");
    }
}