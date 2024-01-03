package models.pages;

import models.components.checkout.*;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {


    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public BillingAddressComponent billingAddressComp() {
        return findComponent(BillingAddressComponent.class);
    }

    public ShippingAddressComponent shippingAddressComp() {
        return findComponent(ShippingAddressComponent.class);
    }

    public ShippingMethodComponent shippingMethodComp() {
        return findComponent(ShippingMethodComponent.class);
    }

    public PaymentMethodComponent paymentMethodComp() {
        return findComponent(PaymentMethodComponent.class);
    }

    public PaymentInformationComponent paymentInformationComp() {
        return findComponent(PaymentInformationComponent.class);
    }

    public ConfirmOrderComponent confirmOrderComp() {
        return findComponent(ConfirmOrderComponent.class);
    }

}