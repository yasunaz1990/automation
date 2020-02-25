import com.github.bogdanlivadariu.gifwebdriver.GifWebDriver;
import commons.BrowserType;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import utility.UIActions;

public class SomeTests extends UIActions {


    @Test
    public void test_case() {
       openBrowser(BrowserType.CHROME);
       openSite("https://trello.com");
       focus(By.cssSelector("[name='email']"));
       waitfor(5);
       closeBrowser();
    }




}
