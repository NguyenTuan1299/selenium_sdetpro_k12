package test_flows.global;

import models.components.global.footer.*;
import models.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

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