package models.components.cart;

import models.components.Component;
import models.components.ComponentCSSSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@ComponentCSSSelector(".cart-item-row")
public class CartItemRowComponent extends Component {

    private final static By unitPriceSel = By.cssSelector(".product-unit-price");
    private final static By itemQuantitySel = By.cssSelector("input[name^='itemquantity']");
    private final static By subtotalSel = By.cssSelector(".product-subtotal");

    public CartItemRowComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public double unitPrice(){
        return Double.parseDouble(findElement(unitPriceSel).getText().trim());
    }

    public double itemQuantity(){
        return Double.parseDouble(findElement(itemQuantitySel).getAttribute("value").trim());
    }

    public double subTotal(){
        return Double.parseDouble(findElement(subtotalSel).getText().trim());
    }
}