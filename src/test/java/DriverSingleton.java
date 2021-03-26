import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverSingleton {

    private static WebDriver driver;

    public static WebDriver getDriverInstance(){
        if (driver == null){
            System.setProperty("webdriver.chrome.driver","D:\\Computers\\Web Drivers\\ChromeDriver-V89-Win32\\chromedriver.exe");
            driver = new ChromeDriver();
        }
        return driver;
    }
}
