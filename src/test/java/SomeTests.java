import commons.BrowserType;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import utility.UIActions;

public class SomeTests extends UIActions {


    @Test
    public void test_case() {
        String url = "https://jqueryui.com/droppable/";
        By emailLink = css("[name='email']");
        By source = id("draggable");
        By target = id("droppable");

        openBrowser(BrowserType.FIREFOX);
        fullScreen();
        gotoSite(url);
        switchToIFrame();
        dragAndDrop(source, target);
        switchBackFromIframe();
        scrollToBottom();
        waitfor(5);
        closeBrowser();
    }
}
