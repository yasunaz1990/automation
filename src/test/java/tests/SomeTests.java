package tests;

import commons.BrowserType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import utility.UIActions;

import java.util.concurrent.TimeUnit;

public class SomeTests extends UIActions {


    @Test
    public void test_case() {
        openBrowser();
        gotoSite("https://trello.com");
        click("Log In");
        highlight(id("user"));
        write(id("user"), "wesley@burkdesing.biz");
        openNewTab();
        gotoSite("https://www.youtube.com");
        closeTab();
        closeBrowser();

    }
}
