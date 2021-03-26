import com.google.gson.Gson;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class Testing {
    public static final String RESOURCES_DATA_JSON = "src/main/resources/data.json";
    WebDriver driver;


    @BeforeClass
    public void setup() {
        driver = DriverSingleton.getDriverInstance();
        driver.manage().window().maximize();
    }

    @Test
    public void Exercise1() {
        driver.get("https://dgotlieb.github.io/Navigation/Navigation.html");
        driver.switchTo().frame("my-frame");
        System.out.println(driver.findElement(By.id("iframe_container")).getText());
    }

    @Test
    public void Exercise2(){
        driver.navigate().to("https://dgotlieb.github.io/Actions");
        Utils.takeScreenshot("Screenshots/element-screenshot",driver.findElement(By.id("div1")));
        Utils.dragAndDrop(By.id("drag1"),By.id("div1"));
        Utils.doubleClick(By.cssSelector("p[ondblclick='doubleClickFunction()']"));
        Assert.assertEquals(driver.findElement(By.id("demo")).getText(),"You double clicked");
        Utils.mouseHover(By.id("close"));
        Utils.selectMultipleElements(By.cssSelector("option[name='kind']"), 0,1);
        driver.findElement(By.cssSelector("input[type='file']")).sendKeys("D:\\Computers\\Qa Experts\\Homework\\Class11-HW\\images\\QA.png");
        //Utils.scrollToElement(driver.findElement(By.id("clickMe")));
        Utils.scrollToLocation(By.id("clickMe"));
    }

    @Test
    public void Exercise3() {
        String url = Utils.getData("src/main/resources/data.xml", "URL");
        driver.navigate().to(url);
    }

    @Test
    public void Exercise6() {
        driver.navigate().to("https://dgotlieb.github.io/Navigation/Navigation.html");
        String handle = driver.getWindowHandle();
        driver.findElement(By.id("MyAlert")).click();
        Alert alert = driver.switchTo().alert();
        System.out.println(alert.getText());
        alert.accept();

        WebElement output = driver.findElement(By.id("output"));
        driver.findElement(By.id("MyPrompt")).click();
        alert = driver.switchTo().alert();
        alert.sendKeys("Zion");
        alert.accept();
        Assert.assertEquals(output.getText(),"Zion");

        driver.findElement(By.id("MyConfirm")).click();
        alert = driver.switchTo().alert();
//        alert.accept();
//        Assert.assertEquals(output.getText(),"Confirmed");
        alert.dismiss();
        Assert.assertEquals(output.getText(),"canceled");

        driver.findElement(By.id("openNewTab")).click();
        driver.switchTo().window(handle);

        driver.findElement(By.tagName("a")).click();
        driver.switchTo().window(handle);
    }

    @Test
    public void Exercise7() {
        driver.get("https://google.co.il");
        ((JavascriptExecutor)driver).executeScript("window.open('https://youtube.com','_blank');");
        ((JavascriptExecutor)driver).executeScript("window.open('https://translate.google.com','_blank');");
        Object[] tabs = driver.getWindowHandles().toArray();

        String handleGoogle = tabs[0].toString();
        String handleYoutube = tabs[1].toString();

        driver.switchTo().window(handleGoogle);
        driver.switchTo().window(handleYoutube);
    }

    @Test
    public void Exercise8() throws IOException, ParseException {
        String URL = (String) Utils.getJSONData(RESOURCES_DATA_JSON).get("URL");
        driver.get(URL);
    }

    @Test
    public void Exercise9() throws IOException, ParseException {
        Gson gson = new Gson();
        String json = Utils.getJSONData(RESOURCES_DATA_JSON).toJSONString();
        Config config = gson.fromJson(json,Config.class);
        driver.get(config.getURL());
    }

    @AfterClass
    public void closeEverything(){
        driver.quit();
    }
}