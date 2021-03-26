import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Exercise4 {
    private WebDriver driver;
    private ExtentTest extentTest;
    private ExtentReports extentReports;
    private final String userDirectory = System.getProperty("user.dir");

    @BeforeClass
    public void setup() throws FileNotFoundException {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(userDirectory + "\\extentClass11.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
        extentTest = extentReports.createTest("Exercise 4 Test");
        extentReports.setSystemInfo("Environment","Production");
        extentReports.setSystemInfo("Tester","Zion G.");
        extentReports.setSystemInfo("Company Name","Dimples");
        extentTest.log(Status.INFO,"@Before Class");

        boolean driverEstablish = false;
        try {
            driver = DriverSingleton.getDriverInstance();
            driver.manage().window().maximize();
            extentTest.log(Status.INFO,"Going to Google Translate");
            driver.get("https://translate.google.co.il/");
            driverEstablish = true;
        } catch (Exception e){
            e.printStackTrace();
            extentTest.log(Status.FAIL,"Driver Connection Failed! " + e.getMessage());
            driverEstablish = false;
        } finally {
            if (driverEstablish){
                extentTest.log(Status.PASS,"Driver Established Successfully");
                // Exercise-5
                System.setOut(new PrintStream(new FileOutputStream("output.txt")));
            }
        }
    }

    @Test
    public void shotTheScreen() {
        String timeNow = String.valueOf(System.currentTimeMillis());
        extentTest.info(MediaEntityBuilder.createScreenCaptureFromPath(Utils.takeScreenshot("screenshots/" + timeNow, null)).build());
        extentTest.info("Clicking the source text's textarea");
        driver.findElement(By.className("er8xn")).click();
    }

    @AfterClass
    public void closeAll(){
        extentTest.info("@After Class");
        driver.quit();
        extentReports.flush();
    }
}