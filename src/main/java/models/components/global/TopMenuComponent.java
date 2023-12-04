package models.components.global;

import models.components.Component;
import models.components.ComponentCSSSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

@ComponentCSSSelector(".top-menu")
public class TopMenuComponent extends Component {

    public TopMenuComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public List<MainCatItem> mainItemsElem(){
        return findComponents(MainCatItem.class);
    }

    @ComponentCSSSelector(".top-menu > li")
    public static class MainCatItem extends Component {

        public MainCatItem(WebDriver driver, WebElement component) {
            super(driver, component);
        }

        public WebElement catItemLinkElem(){
            return component.findElement(By.tagName("a"));
        }

        public List<SublistComponent> sublistComps(){
            Actions actions = new Actions(driver);
            actions.moveToElement(component).perform();
            return findComponents(SublistComponent.class);
        }
    }

    @ComponentCSSSelector(".sublist li a")
    public static class SublistComponent extends Component {

        public SublistComponent(WebDriver driver, WebElement component) {
            super(driver, component);
        }
    }

}