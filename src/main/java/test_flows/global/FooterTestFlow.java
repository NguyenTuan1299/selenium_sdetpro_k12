package test_flows.global;

import models.components.global.footer.FooterColumnComponent;
import models.components.global.footer.InformationColumnComponent;
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
        verifyInformationColumn(informationColumnComp);
        verifyCustomerServiceColumn();
        verifyMyAccountColumn();
        verifyFollowUsColumn();
    }

    private void verifyInformationColumn(FooterColumnComponent informationColumnComp) {
        testFooterColumn(informationColumnComp);
    }

    private void verifyCustomerServiceColumn() {
    }

    private void verifyMyAccountColumn() {
    }

    private void verifyFollowUsColumn() {
    }

    private void testFooterColumn(FooterColumnComponent footerColumnComponent){
        System.out.println(footerColumnComponent.headerEle().getText());
        for (WebElement linkEle : footerColumnComponent.linksEle()) {
            System.out.println(linkEle.getText() + ": " + linkEle.getAttribute("href"));
        }
        System.out.println("=====");
    }

}