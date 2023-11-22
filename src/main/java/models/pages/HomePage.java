package models.pages;

import models.components.product.ProductItemComponent;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public List<ProductItemComponent> productItemCompList(){
        return findComponents(ProductItemComponent.class);
    }

}