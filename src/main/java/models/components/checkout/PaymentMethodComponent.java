package models.components.checkout;

import models.components.Component;
import models.components.ComponentCSSSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@ComponentCSSSelector("#opc-payment_method")
public class PaymentMethodComponent extends Component {

    private static final By codSel = By.cssSelector("input[value='Payments.CashOnDelivery']");
    private static final By checkMoneyOrderSel = By.cssSelector("input[value='Payments.CheckMoneyOrder']");
    private static final By creditCardSel = By.cssSelector("input[value='Payments.Manual']");
    private static final By purchaseOrderSel = By.cssSelector("input[value='Payments.PurchaseOrder']");
    private static final By continueBtnSel = By.cssSelector("input[class*='payment-method-next-step-button']");

    public PaymentMethodComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public void selectCODMethod() {
        findElement(codSel).click();
    }

    public void selectMoneyOrderMethod() {
        findElement(checkMoneyOrderSel).click();
    }

    public void selectCreditCardMethod() {
        findElement(creditCardSel).click();
    }

    public void selectPurchaseOrderMethod() {
        findElement(purchaseOrderSel).click();
    }

    public void clickOnContinueBtn() {
        findElement(continueBtnSel).click();
    }

}