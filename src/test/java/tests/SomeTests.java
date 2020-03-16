package tests;

import commons.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LandingPage;

public class SomeTests  extends BaseTest {

    @Test
    public void login_test() {

        String expected = "There isn't an account for this email";
        String username = "maigadfq3434re@gmail.com";
        String password = "somepassword";

        new LandingPage()
                .clickLogin()
                .signIn(username, password);
    }
}

