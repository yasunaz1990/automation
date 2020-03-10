package commons;

import org.testng.annotations.*;


public abstract class BaseTest {

    @BeforeMethod
    public void beforeEachTestCase() {
//        String choseBrowser = System.getProperty("browser");
//        DriverUtil.openBrowser(choseBrowser);
        DriverUtil.openBrowser();
    }

    @AfterMethod
    public void afterEachTestCase() {
        DriverUtil.closeBrowser();
    }
}
