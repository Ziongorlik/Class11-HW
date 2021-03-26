import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.example.JSUtils;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Utils {

    private static WebDriver driver = DriverSingleton.getDriverInstance();

    public static String takeScreenshot(String imagePath, WebElement element) {
        File screenShotFile;

        // element == null means screenshot the screen
        if (element == null){
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            screenShotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        } else {
            screenShotFile = element.getScreenshotAs(OutputType.FILE);
        }

        try {
            FileUtils.copyFile(screenShotFile, new File(imagePath + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imagePath + ".jpg";
    }

    public static void dragAndDrop(By sourceLocator, By targetLocator) {
        WebElement source = driver.findElement(sourceLocator);
        WebElement target = driver.findElement(targetLocator);
        JSUtils.JavascriptDragAndDrop(driver, source, target);
    }

    public static void doubleClick(By locator) {
        WebElement element = driver.findElement(locator);
        Actions dbAction = new Actions(driver);
        dbAction.doubleClick(element).perform();
    }

    public static void mouseHover(By locator) {
        WebElement element = driver.findElement(locator);
        Actions hoverAction = new Actions(driver);
        hoverAction.moveToElement(element).perform();
    }

    public static void selectMultipleElements(By locator, int index1, int index2) {
        List<WebElement> elementsList = driver.findElements(locator);
        Actions selectMulti = new Actions(driver);
        selectMulti.keyDown(Keys.CONTROL).click(elementsList.get(index1)).click(elementsList.get(index2)).keyUp(Keys.CONTROL);
        selectMulti.build().perform();
    }

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollToLocation(By locator) {
        int x = driver.findElement(locator).getLocation().getX();
        int y = driver.findElement(locator).getLocation().getY();
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("javascript:window.scrollBy(" + x + "," + y + ")");
    }

    public static String getData(String filePath, String keyName) {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            return document.getElementsByTagName(keyName).item(0).getTextContent();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONData(String filePath) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        try(FileReader reader = new FileReader(filePath)){
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            return jsonObject;
        }
    }
}