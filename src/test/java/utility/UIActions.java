package utility;

import commons.BrowserType;
import commons.Colors;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


public class UIActions {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Integer WAIT_TIME;
    private static ArrayList<String> tabs;

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

    public void fullScreen() {
        driver.manage().window().fullscreen();
    }

    public void maximize() {
        driver.manage().window().maximize();
    }

    public void closeBrowser() {
        if(driver != null){
            driver.close();
            driver.quit();
        }
    }

    public void openNewTab() {
        ((JavascriptExecutor) driver).executeScript("window.open()");
        tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    }

    public void switchToTab() {
        ((JavascriptExecutor) driver).executeScript("window.open()");
        tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    }

    public void closeTab() {
        driver.close();
        driver.switchTo().window(tabs.get(0));
    }

    public void loadCookie(String name, String token) {
        Cookie cookie = new Cookie(name, token);
        driver.manage().addCookie(cookie);
    }

    public void deleteCookie(String name) {
        driver.manage().deleteCookieNamed(name);
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
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


    public void doubleClick(By locator) {
        new Actions(driver)
                .doubleClick(waitUntilElementVisible(locator))
                .build()
                .perform();
    }

    public void rightClick(By locator) {
        new Actions(driver)
                .contextClick(waitUntilElementVisible(locator))
                .build()
                .perform();
    }

    public void hover(By locator) {
        WebElement elem = waitUntilElementVisible(locator);
        new Actions(driver).moveToElement(elem).build().perform();
    }

    public void focus(By locator) {
        WebElement element = waitUntilElementVisible(locator);
        if("input".equals(element.getTagName())){
            element.sendKeys("");
        }
        else {
            new Actions(driver).moveToElement(element).perform();
        }
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


    public void clear(By locator) {
        waitUntilElementVisible(locator).clear();
    }

    public void write(By locator, String text) {
        waitUntilElementVisible(locator).sendKeys(text);
    }

    public void clearThenWrite(By locator, String text) {
        WebElement inputElem = waitUntilElementVisible(locator);
        inputElem.clear();
        inputElem.sendKeys(text);
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
