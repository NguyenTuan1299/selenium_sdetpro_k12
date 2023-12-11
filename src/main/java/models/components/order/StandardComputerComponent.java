package models.components.order;

import models.components.ComponentCSSSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import support.ui.SelectEx;

import java.util.List;

@ComponentCSSSelector(".product-essential")
public class StandardComputerComponent extends ComputerEssentialComponent {

    private static final By productAttrSel = By.cssSelector("select[name^='product_attribute']");

    public StandardComputerComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    @Override
    public String selectProcessorType(String type) {
        final int PROCESSOR_DROPDOWN_INDEX = 0;
        WebElement processorDropDownEle = component.findElements(productAttrSel).get(PROCESSOR_DROPDOWN_INDEX);
        return selectOption(processorDropDownEle, type);
    }

    @Override
    public String selectRAMType(String type) {
        final int RAM_DROPDOWN_INDEX = 1;
        WebElement ramDropDownEle = component.findElements(productAttrSel).get(RAM_DROPDOWN_INDEX);
        return selectOption(ramDropDownEle, type);
    }

    private String selectOption(WebElement dropdownEle, String type){
        SelectEx select = new SelectEx(dropdownEle);
        List<WebElement> allOptionEls = select.getOptions();
        String fullStrOption = null;

        for (WebElement optionEle : allOptionEls) {
            String currentOptionText = optionEle.getText();
            String optionTextWithoutSpaces = currentOptionText.trim().replace(" ", "");
            if(optionTextWithoutSpaces.startsWith(type)){
                fullStrOption = currentOptionText;
                break;
            }
        }

        if(fullStrOption == null){
            throw new RuntimeException("[ERR] The option " + type + " is not existing to select");
        }
        select.selectByVisibleText(fullStrOption);
        return fullStrOption;
    }
}