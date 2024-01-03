package models.pages;

import models.components.cart.CartItemRowComponent;
import models.components.cart.TotalComponent;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class ShoppingCartPage extends BasePage {

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public List<CartItemRowComponent> cartItemRowComps(){
        return findComponents(CartItemRowComponent.class);
    }

    public TotalComponent totalComp(){
        return findComponent(TotalComponent.class);
    }
}