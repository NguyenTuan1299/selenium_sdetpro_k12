package tests;

import com.google.common.reflect.ClassPath;
import driver.BrowserType;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        // Get all classes that start with prefix "tests."
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        List<Class<?>> testClasses = new ArrayList<>();

        // Ignoring BaseTest and Main classes because they are not Test Classes
        for (ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
            if (info.getName().startsWith("tests.") && !info.getName().equalsIgnoreCase("tests.BaseTest") && !info.getName().equalsIgnoreCase("tests.Main")) {
                testClasses.add(info.load());
            }
        }

        String browser = System.getProperty("browser");
        if (browser == null) {
            throw new RuntimeException("Please provide browser via -Dbrowser");
        }
        try {
            BrowserType.valueOf(browser);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The browser " + browser + " is not supported, we cover for: " + Arrays.toString(BrowserType.values()));
        }

        // Parallel session data, can be from env variable
        int maxParallelSession = 10;

        // Divide test classes into groups
        if(testClasses.size() / maxParallelSession == 0){
            maxParallelSession = testClasses.size();
        }
        List<String> testGroupNames = new ArrayList<>();
        for (int groupIndex = 0; groupIndex < maxParallelSession; groupIndex++) {
            testGroupNames.add("Group " + (groupIndex + 1));
        }

        int testNumEachGroup = testClasses.size() / testGroupNames.size();
        Map<String, List<Class<?>>> desiredCaps = new HashMap<>();
        for (int groupIndex = 0; groupIndex < testGroupNames.size(); groupIndex++) {
            int startIndex = groupIndex * testNumEachGroup;
            boolean isTheLastGroup = (groupIndex == testGroupNames.size() - 1);
            int endIndex = isTheLastGroup ? testClasses.size() : (startIndex + testNumEachGroup);
            List<Class<?>> sublist = testClasses.subList(startIndex, endIndex);
            desiredCaps.put(testGroupNames.get(groupIndex), sublist);
        }

        // Creating a suite
        TestNG testNG = new TestNG();
        XmlSuite suite = new XmlSuite();
        suite.setName("Regression");

        // Put test into classes group
        List<XmlTest> allTests = new ArrayList<>();
        for (String groupName : desiredCaps.keySet()) {
            XmlTest test = new XmlTest(suite);
            test.setName(groupName);

            // Put classes into each test group
            List<XmlClass> xmlClasses = new ArrayList<>();
            List<Class<?>> dedicatedClasses = desiredCaps.get(groupName);
            for (Class<?> dedicatedClass : dedicatedClasses) {
                xmlClasses.add(new XmlClass(dedicatedClass.getName()));
            }
            test.setXmlClasses(xmlClasses);
            test.addParameter("browser", browser);
            allTests.add(test);
        }

        // Set parallel session data
        boolean isTestingOnSafari = browser.equals("safari");
        if(!isTestingOnSafari){
            suite.setParallel(XmlSuite.ParallelMode.TESTS);
            suite.setThreadCount(maxParallelSession);
        }

        if(isTestingOnSafari){
            suite.addIncludedGroup("safari");
        }

        suite.setTests(allTests);
        System.out.println(suite.toXml());

        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);

        // Invoke run() method
        testNG.setXmlSuites(suites);
        testNG.run();
    }
}