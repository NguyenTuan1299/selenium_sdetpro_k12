package tests.order.computer;

import models.components.order.StandardComputerComponent;
import org.testng.annotations.Test;
import test_flows.computer.OrderComputerFlow;
import tests.BaseTest;

public class BuyingStandardComputerTest extends BaseTest {

    @Test
    public void testCheapComputerBuying(){
        driver.get("https://demowebshop.tricentis.com/build-your-own-computer");
        OrderComputerFlow orderComputerFlow = new OrderComputerFlow(driver, StandardComputerComponent.class);
        orderComputerFlow.buildCompSpecAndAddToCart();
    }
}