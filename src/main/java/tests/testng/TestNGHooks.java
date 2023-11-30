package tests.testng;

import org.testng.annotations.*;

public class TestNGHooks {

    /**
     * BeforeSuite
     * BeforeTest
     * BeforeClass
     * BeforeMethod
     */

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before suite!");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("\tBefore test!");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("\t\tBefore class!");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("\t\t\tBefore method!");
    }

    @Test
    public void test1(){
        System.out.println("\t\t\t\tTest method 01!");
    }

    @Test
    public void test2(){
        System.out.println("\t\t\t\tTest method 02!");
    }

}