package tests.testng;

import org.testng.annotations.Test;

public class TestOrder {

    @Test(dependsOnMethods = {"test1"})
    public void test2() {
        System.out.println("Test B");
    }

    @Test(priority = 1)
    public void test1() {
        System.out.println("Test A");
    }

}