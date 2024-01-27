package tests;

import driver.DriverFactory;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.apache.commons.io.FileUtils;

public class BaseTest {

    private final static List<DriverFactory> webdriverThreadPool = Collections.synchronizedList(new ArrayList<>());
    private static ThreadLocal<DriverFactory> driverThread;

    protected WebDriver getDriver(){
        return driverThread.get().getDriver();
    }

    @BeforeTest
    protected void initBrowserSession() {
        driverThread = ThreadLocal.withInitial(() -> {
            DriverFactory threadDriverFactory = new DriverFactory();
            webdriverThreadPool.add(threadDriverFactory);
            return threadDriverFactory;
        });
    }

    @AfterTest(alwaysRun = true)
    public void closeBrowserSession() {
        driverThread.get().closeBrowserSession();
    }

    @AfterMethod
    public void captureScreenshot(ITestResult result) {
        boolean isTestFailed = result.getStatus() == ITestResult.FAILURE;
        if (isTestFailed) {
            attachScreenshotToReport(result);
        }
    }

    private void attachScreenshotToReport(ITestResult result) {
        // testMethodName-yyyy-mm-dd-hr-min-sec.png

        // 1. Get method name
        String methodName = result.getName();

        // 2. Get taken time
        Calendar calendar = new GregorianCalendar();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH) + 1;
        int d = calendar.get(Calendar.DATE);
        int hr = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        String filename = methodName + "-" + y + "-" + m + "-" + d + "-" + hr + "-" + min + "-" + sec + ".png";

        // 3. Take screenshot
        WebDriver driver = driverThread.get().getDriver();
        File screenshotBase64Data = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            // 4. Save
            String fileLocation = System.getProperty("user.dir") + "/screenshots/" + filename;
            FileUtils.copyFile(screenshotBase64Data, new File(fileLocation));

            // 5. Attach into the report
            Path content = Paths.get(fileLocation);
            try (InputStream inputStream = Files.newInputStream(content)) {
                Allure.addAttachment(methodName, inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}