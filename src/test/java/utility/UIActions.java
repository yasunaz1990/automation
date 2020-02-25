package utility;

import commons.BrowserType;
import commons.Colors;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;


public class UIActions {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Integer WAIT_TIME;

    public UIActions() {
        WAIT_TIME = 40;
    }

    public UIActions(Integer implicitWaitTime) {
        WAIT_TIME = implicitWaitTime;
    }

    //region Browser Actions
    public WebDriver getDriver() {
        if(driver == null) {
            StringBuilder strb = new StringBuilder();
            strb.append("\n\nException Message : \n");
            strb.append("\tWebDriver is not initiated, please instantiate WebDriver by\n");
            strb.append("\tcalling openBrowser() method. \n\n");
            strb.append("Exception Location:  Class  -> UIActions\n");
            strb.append("Exception Occured :  Method -> getDriver()\n");
            throw new NullPointerException(strb.toString());
        }
        return driver;
    }

    public void openBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, WAIT_TIME);
    }

    public void openBrowser(String browserType) {
        if(browserType.equalsIgnoreCase(BrowserType.CHROME)) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            wait = new WebDriverWait(driver, WAIT_TIME);
        }
        else if(browserType.equalsIgnoreCase(BrowserType.EDGE)) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            wait = new WebDriverWait(driver, WAIT_TIME);
        }
    }

    public void closeBrowser() {
        if(driver != null){
            driver.close();
            driver.quit();
        }
    }

    public void openTab() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_T);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_T);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }

    public void closeTab() {

    }
    //endregion


    //region Page Actions
    public void openSite(String url) {
        driver.get(url);
    }

    public void reload() {
        driver.navigate().refresh();
    }

    public void goBack() {
        driver.navigate().back();
    }

    public void goFoward() {
        driver.navigate().forward();
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    public String title() {
        return driver.getTitle();
    }

    public void click(By locator) {
        waitUntilElementVisible(locator).click();
    }

    public void click(String text){
        String expression = "//*[text()='" + text + "']";
        By selector = By.xpath(expression);
        waitUntilElementVisible(selector);
        List<WebElement> foundElems = driver.findElements(selector);
        if(foundElems.size() == 0) {
            StringBuilder strb = new StringBuilder();
            strb.append("\n\nException Message : \n");
            strb.append("\tUnable to find the element with text{ +" + text + " } from UI");
            strb.append("\tAny typo? Also text matching is case sensitive, if you cannot \n");
            strb.append("\tresolve this, please use another locators to locate to element. \n");
            strb.append("Exception Location:  Class  -> UIActions\n");
            strb.append("Exception Occured :  Method -> click(String text)\n");
            throw new NotFoundException(strb.toString());
        } else {
            for(WebElement elem : foundElems) {
                elem.click();
                break;
            }
        }
    }


    public void doubleClick() {

    }

    public void rightClick() {

    }

    public void hover() {

    }

    public void focus() {

    }

    public void highlight(By locator) {
        WebElement element = waitUntilElementVisible(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
       // js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
        js.executeScript("arguments[0].setAttribute('style', 'border: 3px solid red;');", element);
    }

    public void textHighligh(By locator) {
        WebElement element = waitUntilElementVisible(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    }


    public void clear() {

    }

    public void write() {

    }

    public void clearThenWrite() {

    }

    public void waitfor(int second) {
        try{
            Thread.sleep(second * 1000);
        }catch (Exception e) {

        }
    }


    //endregion


    //region Selectors

    //endregion



    //region Waiters
    private WebElement waitUntilElementVisible(By locator) {
        WebElement elem = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return elem;
    }

    private boolean waitUntilElementInvisible(By locator) {
        return false;
    }

    public boolean isElementDisplayed() {
        return false;
    }
    //endregion


    //region Private Helper Methods


    private void click_NthElement(By element, int index) {
        waitUntilElementVisible(element);
        List<WebElement> elementAllElements = driver.findElements(element);
        WebElement elementElementByIdx = elementAllElements.get(index);
        elementElementByIdx.click();
    }
    //endregion

}
