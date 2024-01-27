package test_flows.computer;

import models.components.cart.CartItemRowComponent;
import models.components.cart.TotalComponent;
import models.components.checkout.BillingAddressComponent;
import models.components.checkout.PaymentInformationComponent;
import models.components.checkout.PaymentMethodComponent;
import models.components.checkout.ShippingMethodComponent;
import models.components.order.ComputerEssentialComponent;
import models.pages.CheckoutOptionPage;
import models.pages.CheckoutPage;
import models.pages.ComputerItemDetailsPage;
import models.pages.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import test_data.CreditCardType;
import test_data.DataObjectBuilder;
import test_data.PaymentMethod;
import test_data.computer.ComputerData;
import test_data.user.UserDataObject;

import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderComputerFlow<T extends ComputerEssentialComponent> {

    private Class<T> computerEssentialCompClass;
    private WebDriver driver;
    private ComputerData computerData;
    private double itemTotalPrice;
    private int quantity;
    private UserDataObject defaultCheckoutUser;
    private PaymentMethod paymentMethod;
    private CreditCardType creditCardType;

    public OrderComputerFlow(WebDriver driver, Class<T> computerEssentialCompClass, ComputerData computerData) {
        this.computerEssentialCompClass = computerEssentialCompClass;
        this.driver = driver;
        this.computerData = computerData;
        this.quantity = 1;
    }

    public OrderComputerFlow(WebDriver driver, Class<T> computerEssentialCompClass, ComputerData computerData, int quantity) {
        this.computerEssentialCompClass = computerEssentialCompClass;
        this.driver = driver;
        this.computerData = computerData;
        this.quantity = quantity;
    }

    public void buildCompSpec() {
        ComputerEssentialComponent computerEssentialComp = new ComputerItemDetailsPage(driver).computerComp(computerEssentialCompClass);
        computerEssentialComp.unselectDefaultOptions();
        String processorFullStr = computerEssentialComp.selectProcessorType(this.computerData.getProcessor());
        double processorAdditionalPrice = extractAdditionalPrice(processorFullStr);
        String ramFullStr = computerEssentialComp.selectRAMType(this.computerData.getRam());
        double ramAdditionalPrice = extractAdditionalPrice(ramFullStr);
        String hddFullStr = computerEssentialComp.selectHDD(this.computerData.getHdd());
        double hddAdditionalPrice = extractAdditionalPrice(hddFullStr);
        String softwareFullStr = computerEssentialComp.selectSoftware(this.computerData.getSoftware());
        double softwareAdditionalPrice = extractAdditionalPrice(softwareFullStr);
        double osAdditionalPrice = 0;
        String osDataOption = this.computerData.getOs();
        if (osDataOption != null) {
            String osFullStr = computerEssentialComp.selectOS(osDataOption);
            osAdditionalPrice = extractAdditionalPrice(osFullStr);
        }
        double additionalPrice = processorAdditionalPrice + ramAdditionalPrice + hddAdditionalPrice + softwareAdditionalPrice + osAdditionalPrice;

        // Set item quantity
        computerEssentialComp.setProductQuantity(this.quantity);

        // Calculate item price = Base Price + Additional Prices;
        this.itemTotalPrice = (computerEssentialComp.productPrice() + additionalPrice) * this.quantity;
    }

    public void addItemToCart() {
        ComputerItemDetailsPage computerItemDetailsPage = new ComputerItemDetailsPage(driver);
        ComputerEssentialComponent computerEssentialComp = computerItemDetailsPage.computerComp(computerEssentialCompClass);
        computerEssentialComp.clickOnAddCartBtn();
        computerEssentialComp.waitUntilItemAddedToCart();
        computerItemDetailsPage.headerComp().clickOnShoppingCartLink();
    }

    public void verifyShoppingCartPage() {
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        List<CartItemRowComponent> cartItemRowComps = shoppingCartPage.cartItemRowComps();
        // Verify shopping cart details
        Assert.assertFalse(cartItemRowComps.isEmpty(), "[ERR] There is no items displayed in the shopping cart!");
        double currentSubTotals = 0;
        double currentTotalUnitPrices = 0;
        for (CartItemRowComponent cartItemRowComp : cartItemRowComps) {
            currentSubTotals = currentSubTotals + cartItemRowComp.subTotal();
            currentTotalUnitPrices = currentTotalUnitPrices + (cartItemRowComp.itemQuantity() * cartItemRowComp.unitPrice());
        }
        Assert.assertEquals(currentSubTotals, currentTotalUnitPrices, "[ERR] shopping cart sub-total is incorrect!");

        // Verify checkout prices
        TotalComponent totalComp = shoppingCartPage.totalComp();
        Map<String, Double> priceCategories = totalComp.priceCategories();
        Assert.assertFalse(priceCategories.keySet().isEmpty(), "[ERR] Checkout price info is empty!");
        double checkoutSubTotal = 0;
        double checkoutTotal = 0;
        double checkoutOtherFees = 0;
        for (String priceType : priceCategories.keySet()) {
            double priceValue = priceCategories.get(priceType);
            if (priceType.startsWith("Sub-Total")) {
                checkoutSubTotal = priceValue;
            } else if (priceType.startsWith("Total")) {
                checkoutTotal = priceValue;
            } else {
                checkoutOtherFees = checkoutOtherFees + priceValue;
            }
        }
        Assert.assertEquals(currentSubTotals, checkoutSubTotal, "[ERR] Checkout sub-total is incorrect");
        Assert.assertEquals(checkoutTotal, (checkoutSubTotal + checkoutOtherFees), "[ERR] Checkout Total is incorrect");
    }

    public void agreeTOSAndCheckout() {
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        TotalComponent totalComp = shoppingCartPage.totalComp();
        totalComp.agreeTOS();
        totalComp.clickOnCheckoutBtn();

        // This is exception case, please do not use one flow method for more than one page
        new CheckoutOptionPage(driver).checkoutAsGuest();
    }

    public void inputBillingAddress() {
        String defaultCheckoutUserDataLOC = "/src/main/java/test_data/user/DefaultCheckoutUser.json";
        this.defaultCheckoutUser = DataObjectBuilder.buildDataObjectFrom(defaultCheckoutUserDataLOC, UserDataObject.class);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        BillingAddressComponent billingAddressComp = checkoutPage.billingAddressComp();
        billingAddressComp.selectInputNewAddress();
        billingAddressComp.inputFirstName(defaultCheckoutUser.getFirstName());
        billingAddressComp.inputLastName(defaultCheckoutUser.getLastName());
        billingAddressComp.inputEmail(defaultCheckoutUser.getEmail());
        billingAddressComp.selectCountry(defaultCheckoutUser.getCountry());
        billingAddressComp.selectState(defaultCheckoutUser.getState());
        billingAddressComp.inputCity(defaultCheckoutUser.getCity());
        billingAddressComp.inputAdd1(defaultCheckoutUser.getAdd1());
        billingAddressComp.inputZipCode(defaultCheckoutUser.getZipcode());
        billingAddressComp.inputPhoneNo(defaultCheckoutUser.getPhoneNumber());
        billingAddressComp.clickOnContinueBtn();
    }

    public void inputShippingAddress() {
        new CheckoutPage(driver).shippingAddressComp().clickOnContinueBtn();
    }

    public void selectShippingMethod() {
        List<String> shippingMethods = Arrays.asList("Ground", "Next Day Air", "2nd Day Air");
        int randomShippingMethodIndex = new SecureRandom().nextInt(shippingMethods.size());
        String randomShippingMethod = shippingMethods.get(randomShippingMethodIndex);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        ShippingMethodComponent shippingMethodComp = checkoutPage.shippingMethodComp();
        shippingMethodComp.selectShippingMethod(randomShippingMethod);
        shippingMethodComp.clickOnContinueBtn();
    }

    public void selectPaymentMethod() {
        this.paymentMethod = PaymentMethod.COD;
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        PaymentMethodComponent paymentMethodComp = checkoutPage.paymentMethodComp();
        paymentMethodComp.selectCODMethod();
    }

    public void selectPaymentMethod(PaymentMethod paymentMethod) {
        Assert.assertNotNull(paymentMethod, "[ERR] payment method type can't be null");
        this.paymentMethod = paymentMethod;
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        PaymentMethodComponent paymentMethodComp = checkoutPage.paymentMethodComp();
        switch (paymentMethod) {
            case CHECK_MONEY_ORDER:
                paymentMethodComp.selectMoneyOrderMethod();
                break;
            case CREDIT_CARD:
                paymentMethodComp.selectCreditCardMethod();
                break;
            case PURCHASE_ORDER:
                paymentMethodComp.selectPurchaseOrderMethod();
                break;
            default:
                paymentMethodComp.selectCODMethod();
        }
        paymentMethodComp.clickOnContinueBtn();
    }

    /*
     * https://www.paypalobjects.com/en_AU/vhelp/paypalmanager_help/credit_card_numbers.htm
     * OR here(using generate feature): https://developer.paypal.com/api/rest/sandbox/card-testing/#link-creditcardgenerator
     * (Thanks Le Hoai Duc for sharing this link)
     * */
    public void inputPaymentInfo(CreditCardType creditCardType) {
        this.creditCardType = creditCardType;
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        PaymentInformationComponent paymentInformationComp = checkoutPage.paymentInformationComp();
        if (this.paymentMethod.equals(PaymentMethod.PURCHASE_ORDER)) {
            paymentInformationComp.inputPurchaseNum("123");
        } else if (this.paymentMethod.equals(PaymentMethod.CREDIT_CARD)) {
            paymentInformationComp.selectCardType(creditCardType);
            String cardHolderFirstName = this.defaultCheckoutUser.getFirstName();
            String cardHolderLastName = this.defaultCheckoutUser.getLastName();
            paymentInformationComp.inputCardHolderName(cardHolderFirstName + " " + cardHolderLastName);
            // Trigger the logic for other card types as well like Amex, Master card..
            String cardNumber = creditCardType.equals(CreditCardType.VISA) ? "4012888888881881" : "6011000990139424";
            paymentInformationComp.inputCardNumber(cardNumber);

            // Current month and next year
            Calendar calendar = new GregorianCalendar();
            int expiredMonthNum = calendar.get(Calendar.MONTH) + 1;
            String expiredMonthStr = expiredMonthNum < 10 ? "0" + expiredMonthNum : String.valueOf(expiredMonthNum);
            paymentInformationComp.inputExpiredMonth(expiredMonthStr);
            paymentInformationComp.inputExpiredYear(String.valueOf(calendar.get(Calendar.YEAR) + 1));
            paymentInformationComp.inputCardCode("123");
            paymentInformationComp.clickOnContinueBtn();
        } else if (this.paymentMethod.equals(PaymentMethod.COD)) {
            // Verify the text is [You will pay by COD]
        } else {
            // Verify the text is [Mail Personal or Business Check, Cashier's Check or money order to:....]
        }
    }

    // TODO: if you have time please add verification methods for this
    public void confirmOrder(){
        new CheckoutPage(driver).confirmOrderComp().clickOnConfirmBtn();
    }

    private double extractAdditionalPrice(String optionStr) {
        double price = 0;
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(optionStr);
        if (matcher.find()) {
            price = Double.parseDouble(matcher.group(1).replaceAll("[+-]", ""));
        }
        return price;
    }

}