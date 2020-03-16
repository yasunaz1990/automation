package pages;

import org.openqa.selenium.By;
import utility.UIActions;

public class LandingPage extends UIActions {

    private final String URL = "https://trello.com/en-US";

    // Elements
    private final By login_link = xpath("//a[text()='Log In']");
    private final By signup_link = xpath("//a[text()='Sign Up']");
    private final By email_input = css("[name='email']");


    // Constructor
    public LandingPage() {
        gotoSite(URL);
    }


    // User Actions
    public LoginPage clickLogin() {
        waitfor(1);
        click(login_link);
        return new LoginPage();
    }

    public void clickSignUp() {
        click(signup_link);
    }
}
