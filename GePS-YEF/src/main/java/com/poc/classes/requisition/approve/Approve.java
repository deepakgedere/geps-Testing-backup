package com.poc.classes.requisition.approve;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.poc.interfaces.logout.ILogout;
import com.poc.interfaces.requisitions.IPrApprove;
import com.poc.interfaces.login.ILogin;
import java.util.Properties;
import static com.factory.PlaywrightFactory.waitForLocator;
import static com.constants.requisitions.LPrApprove.*;

public class Approve implements IPrApprove {

    private ILogin iLogin;
    private ILogout iLogout;
    private Properties properties;
    private Page page;

    private Approve(){
    }

//TODO Constructor
    public Approve(ILogin iLogin, Properties properties, Page page, ILogout iLogout){
        this.iLogin = iLogin;
        this.properties = properties;
        this.page = page;
        this.iLogout = iLogout;
    }

    public void approve() {
        try {
        String projectManager = properties.getProperty("projectManagerEmail");
        iLogin.performLogin(projectManager);
        String title = properties.getProperty("orderTitle");
        String approveButtonLocator = getApproveButton(title);
        Locator projectManagerOption = page.locator(approveButtonLocator);
        waitForLocator(projectManagerOption);
        projectManagerOption.first().click();

        Locator approveButton = page.locator(APPROVE);
        waitForLocator(approveButton);
        approveButton.click();

        Locator yesButtonLocator = page.locator(YES);
        waitForLocator(yesButtonLocator);
        yesButtonLocator.click();
        iLogout.performLogout();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }
}