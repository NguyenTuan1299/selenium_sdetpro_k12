package models.components.order;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class ComputerEssentialComponent extends BaseItemComponent {

    private final static By allOptionsSel = By.cssSelector(".option-list input");

    public ComputerEssentialComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public abstract String selectProcessorType(String type);

    public abstract String selectRAMType(String type);

    public String selectHDD(String type){
        return selectCompOption(type);
    }

    public String selectOS(String type){
        return selectCompOption(type);
    }

    public String selectSoftware(String type){
        return selectCompOption(type);
    }

    public void unselectDefaultOptions() {
        component.findElements(allOptionsSel).forEach(option -> {
            if (option.getAttribute("checked") != null) {
                option.click();
            }
        });
    }

    protected String selectCompOption(String type) {
        String selectorStr = "//label[contains(text()," + "\"" + type + "\"" + ")]";
        By optionSelector = By.xpath(selectorStr);
        WebElement optionEle = null;
        try {
            optionEle = component.findElement(optionSelector);
        } catch (Exception ignored){}

        if(optionEle == null){
            throw new RuntimeException("[ERR] The option " + type + " is not existing to select!");
        }
        optionEle.click();
        return optionEle.getText().trim();
    }
}