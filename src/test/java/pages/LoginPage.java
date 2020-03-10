package pages;

import org.openqa.selenium.By;
import utility.Steps;
import utility.UIActions;

public class LoginPage extends UIActions {

    private final By username_input = id("user");
    private final By password_input = id("password");
    private final By login_button = id("login");
    private final By error_message = css("#error > p");


    // User Actions
    public LoginPage signIn(String username, String password) {
        Steps.imgLog("writing username and password");
        write(username_input, username);
        write(password_input, password);
        click(login_button);
        return this;
    }

    public String getErrorMessageBannerText() {
        return element(error_message).getText();
    }
}
