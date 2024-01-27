package models.components.checkout;

import models.components.Component;
import models.components.ComponentCSSSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

@ComponentCSSSelector("#opc-billing")
public class BillingAddressComponent extends Component {

    private static final By inputAddressDropdownSel = By.id("billing-address-select");
    private static final By firstNameSel = By.id("BillingNewAddress_FirstName");
    private static final By lastNameSel = By.id("BillingNewAddress_LastName");
    private static final By emailSel = By.id("BillingNewAddress_Email");
    private static final By selectCountryDropdownSel = By.id("BillingNewAddress_CountryId");
    private static final By selectStateDropdownSel = By.id("BillingNewAddress_StateProvinceId");
    private static final By loadingStateProgressBarSel = By.id("states-loading-progress");
    private static final By citySel = By.id("BillingNewAddress_City");
    private static final By add1Sel = By.id("BillingNewAddress_Address1");
    private static final By zipCodeSel = By.id("BillingNewAddress_ZipPostalCode");
    private static final By phoneNoSel = By.id("BillingNewAddress_PhoneNumber");
    private static final By continueBtnSel = By.cssSelector("input[class*='new-address-next-step-button']");

    public BillingAddressComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public void selectInputNewAddress(){
        if(!findElements(inputAddressDropdownSel).isEmpty()){
            Select select = new Select(findElement(inputAddressDropdownSel));
            select.selectByVisibleText("New Address");
        }
    }

    public void inputFirstName(String firstName) {
        findElement(firstNameSel).sendKeys(firstName);
    }

    public void inputLastName(String lastName) {
        findElement(lastNameSel).sendKeys(lastName);
    }

    public void inputEmail(String email) {
        findElement(emailSel).sendKeys(email);
    }

    public void selectCountry(String country) {
        selectCommon(findElement(selectCountryDropdownSel), country);
        wait.until(ExpectedConditions.invisibilityOf(findElement(loadingStateProgressBarSel)));
    }

    public void selectState(String state) {
        selectCommon(findElement(selectStateDropdownSel), state);
    }

    private void selectCommon(WebElement dropdownEle, String value){
        Select select = new Select(dropdownEle);
        select.selectByVisibleText(value);
    }

    public void inputCity(String city) {
        findElement(citySel).sendKeys(city);
    }

    public void inputAdd1(String add1) {
        findElement(add1Sel).sendKeys(add1);
    }

    public void inputZipCode(String zipCode) {
        findElement(zipCodeSel).sendKeys(zipCode);
    }

    public void inputPhoneNo(String phoneNo) {
        findElement(phoneNoSel).sendKeys(phoneNo);
    }

    public void clickOnContinueBtn() {
        findElement(continueBtnSel).click();
        wait.until(ExpectedConditions.invisibilityOf(findElement(continueBtnSel)));
    }

}