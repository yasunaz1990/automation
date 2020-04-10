package utility;

import commons.BrowserType;
import commons.DriverUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.List;

public  class UIActions {

    protected static WebDriver driver;
    private static WebDriverWait wait;
    private static Integer WAIT_TIME = 30;
    private static ArrayList<String> tabs;
    private static Integer CURRENT_TAB_POSITION = 0;

    private static StandByUntil standByUntil;

    public UIActions() {
        driver = DriverUtil.getDriver();
        wait = new WebDriverWait(driver, WAIT_TIME);
        standByUntil = new StandByUntil(wait);
    }


    //region Browser Actions
//    public void openBrowser() {
//        WAIT_TIME = 30;
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
//        wait = new WebDriverWait(driver, WAIT_TIME);
//        standByUntil = new StandByUntil(wait);
//    }

//    public void openBrowser(String browserType) {
//        if(browserType.equalsIgnoreCase(BrowserType.CHROME)) {
//            WebDriverManager.chromedriver().setup();
//            driver = new ChromeDriver();
//            DriverUtil.setDriver(driver);
//        }
//        else if(browserType.equalsIgnoreCase(BrowserType.EDGE)) {
//            WebDriverManager.edgedriver().setup();
//            driver = new EdgeDriver();
//        }
//        else if(browserType.equalsIgnoreCase(BrowserType.FIREFOX)) {
//            WebDriverManager.firefoxdriver().setup();
//            driver = new FirefoxDriver();
//            CHOSEN_BROWSER = BrowserType.FIREFOX;
//        }
//
//        wait = new WebDriverWait(driver, WAIT_TIME);
//        standByUntil = new StandByUntil(wait);
//    }
//
//    public WebDriver getDriver() {
//        if(driver == null) {
//            StringBuilder strb = new StringBuilder();
//            strb.append("\n\nException Message : \n");
//            strb.append("\tWebDriver is not initiated, please instantiate WebDriver by\n");
//            strb.append("\tcalling openBrowser() method. \n\n");
//            strb.append("Exception Location:  Class  -> UIActions\n");
//            strb.append("Exception Occured :  Method -> getDriver()\n");
//            throw new NullPointerException(strb.toString());
//        }
//        return driver;
//    }

    public void fullScreen() {
        driver.manage().window().fullscreen();
    }


    public void maximize() {
        driver.manage().window().maximize();
    }


    public void switchToIFrame() {
        WebElement iframe = waitUntilElementVisible(By.tagName("iframe"));
        driver = driver.switchTo().frame(iframe);
    }


    public void switchToIFrame(By locator) {
        WebElement iframe = waitUntilElementVisible(locator);
        driver = driver.switchTo().frame(iframe);
    }


    public void switchBackFromIframe() {
        driver = driver.switchTo().defaultContent();
    }


    public void openNewTab() {
        ((JavascriptExecutor) driver).executeScript("window.open()");
        tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
//        driver.switchTo().window(tabs.get(1));
    }

    public void switchToTab(int tabNumber) {
        driver.switchTo().window(tabs.get(tabNumber));
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
    public void gotoSite(String url) {
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
        try{
            WebElement element = standByUntil.elementIsClickable(locator);
            element.click();
        }catch (Exception ex) {
            System.out.println("Element was not clickalbe. Check its locators logic ( Ex: css, xpath .etc");
        }
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
                .doubleClick(standByUntil.elementIsClickable(locator))
                .build()
                .perform();
    }


    public void rightClick(By locator) {
        new Actions(driver)
                .contextClick(standByUntil.elementIsClickable(locator))
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


    public void mouseDownOn(By element) {
        new Actions(driver)
                .moveToElement(waitUntilElementVisible(element))
                .clickAndHold().perform();
    }


    public void moveTo(By element) {
        new Actions(driver).moveToElement(waitUntilElementVisible(element))
                .build()
                .perform();
    }


    public void mouseUpOn(By element) {
        new Actions(driver).moveToElement(waitUntilElementVisible(element))
                .release()
                .perform();
    }


    public void dragAndDrop(By base, By target) {
        mouseDownOn(base);
        moveTo(target);
        mouseUpOn(target);
    }


    public void moveViewToElement(By selector) {
        WebElement where;
        Actions builder = new Actions(driver);
        where = waitUntilElementVisible(selector);
        builder.moveToElement(where).perform();
    }


    public void scrollToBottom(){
        String jscode ="window.scrollTo(0, document.body.scrollHeight)";
        ((JavascriptExecutor) driver).executeScript(jscode);
    }


    public void scrollToTop() {
        String jscode ="window.scrollTo(0, 0)";
        ((JavascriptExecutor) driver).executeScript(jscode);
    }


    public void scrollDownByPixel(int pixelnum) {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,"+pixelnum+")");
    }


    public void scrollUpByPixel(int pixelnum) {
        pixelnum = pixelnum - (pixelnum*2);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,"+pixelnum+")");
    }


    public void highlight(By locator) {
        WebElement element = waitUntilElementVisible(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'border: 3px solid red;');", element);
    }


    public void textHighlight(By locator) {
        WebElement element = waitUntilElementVisible(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    }


    public void clear(By locator) {
        waitUntilElementVisible(locator).clear();
    }


    public void write(By locator, String text) {

//        waitUntilElementVisible(locator).sendKeys(text);
        standByUntil.elementIsThereAndVisibleToUser(locator).sendKeys(text);
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


    public void submit(By element) {
        waitUntilElementVisible(element).submit();
    }


    public WebElement element(By locator) {
       // WebElement elem = waitUntilElementVisible(locator);
        WebElement element = standByUntil.elementIsThereAndVisibleToUser(locator);
        return element;
    }


    public List<WebElement> listOfElements(By locator) {
        waitUntilElementVisible(locator);
        return driver.findElements(locator);
    }
    //endregion


    //region Selectors
    public By css(String expression) {
        return By.cssSelector(expression);
    }


    public By id(String expression) {
        return By.id(expression);
    }


    public By xpath(String expression) {
        return By.xpath(expression);
    }


    public By link(String expression) {
        return By.linkText(expression);
    }


    public By linktextContains(String expression) {
        return By.partialLinkText(expression);
    }


    public By nameAttribute(String expression) {
        return By.name(expression);
    }


    public By withTag(String expression) {
        return By.tagName(expression);
    }
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
