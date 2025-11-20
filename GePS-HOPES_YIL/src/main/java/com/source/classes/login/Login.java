package com.source.classes.login;
import com.microsoft.playwright.*;
import com.source.interfaces.login.ILogin;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.util.Properties;
import static com.constants.login.LLogin.*;

public class Login implements ILogin {

    Logger logger;
    Page page;
    Properties properties;

    private Login() {
    }

//TODO Constructor
    public Login(Properties properties, Page page) {
        this.properties = properties;
        this.page = page;
        logger = LoggerUtil.getLogger(Login.class);
    }

    public int performLogin(String emailId) {
        int status = 0;
        try {
            String password = properties.getProperty("loginPassword");

            page.locator(EMAIL).fill(emailId);
            page.locator(PASSWORD).fill(password);
            page.locator(LOGIN_BUTTON).click();

            APIResponse apiResponse = page.request().fetch("https://geps_hopes_yea.cormsquare.com/api/users/current");
            status = apiResponse.status();
        } catch (Exception error) {
            logger.error("Error in Perform Login Function: " + error.getMessage());
        }
        return status;
    }
}