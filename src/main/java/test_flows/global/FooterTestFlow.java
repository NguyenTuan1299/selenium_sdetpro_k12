package test_flows.global;

import models.components.global.footer.*;
import models.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FooterTestFlow {

    private final WebDriver driver;

    public FooterTestFlow(WebDriver driver) {
        this.driver = driver;
    }

    public void verifyFooterComponent(){
        BasePage basePage = new BasePage(this.driver);
        InformationColumnComponent informationColumnComp = basePage.footerComp().informationColumnComp();
        CustomerServiceColumnComponent customerServiceColumnComponent = basePage.footerComp().customerServiceColumnComp();
        MyAccountColumnComponent myAccountColumnComponent = basePage.footerComp().myAccountColumnComp();
        FollowUsColumnComponent followUsColumnComponent = basePage.footerComp().followUsColumnComp();
        verifyInformationColumn(informationColumnComp);
        verifyCustomerServiceColumn(customerServiceColumnComponent);
        verifyMyAccountColumn(myAccountColumnComponent);
        verifyFollowUsColumn(followUsColumnComponent);
    }

    private void verifyInformationColumn(FooterColumnComponent informationColumnComp) {
        testFooterColumn(informationColumnComp);
    }

    private void verifyCustomerServiceColumn(FooterColumnComponent customerServiceColumnComp) {
        testFooterColumn(customerServiceColumnComp);
    }

    private void verifyMyAccountColumn(FooterColumnComponent myAccountColumnComponent) {
        testFooterColumn(myAccountColumnComponent);
    }

    private void verifyFollowUsColumn(FooterColumnComponent followUsColumnComponent) {
        testFooterColumn(followUsColumnComponent);
    }

    private void testFooterColumn(FooterColumnComponent footerColumnComponent){
        System.out.println(footerColumnComponent.headerEle().getText());
        for (WebElement linkEle : footerColumnComponent.linksEle()) {
            System.out.println(linkEle.getText() + ": " + linkEle.getAttribute("href"));
        }
        System.out.println("=====");
    }

}