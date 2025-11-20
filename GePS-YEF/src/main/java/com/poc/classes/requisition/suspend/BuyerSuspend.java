package com.poc.classes.requisition.suspend;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.poc.interfaces.login.ILogin;
import com.poc.interfaces.logout.ILogout;
import com.poc.interfaces.requisitions.IPrEdit;
import com.poc.interfaces.requisitions.IPrSuspend;
import java.util.Properties;
import static com.factory.PlaywrightFactory.waitForLocator;
import static com.constants.requisitions.LPrBuyerSuspend.*;

public class BuyerSuspend implements IPrSuspend {

    private ILogin iLogin;
    private ILogout iLogout;
    private Properties properties;
    private Page page;
    private IPrEdit iPrEdit;

    private BuyerSuspend(){
    }

//TODO Constructor
    public BuyerSuspend(ILogin iLogin, Properties properties, Page page, ILogout iLogout, IPrEdit iPrEdit){
        this.iLogin = iLogin;
        this.properties = properties;
        this.page = page;
        this.iLogout = iLogout;
        this.iPrEdit = iPrEdit;
    }

    public void suspend(){
        try {
        iLogin.performLogin(properties.getProperty("buyerEmail"));
        String title = properties.getProperty("orderTitle");
        String getTitle = getTitle(title);
        Locator titleLocator = page.locator(getTitle);
        waitForLocator(titleLocator);
        titleLocator.first().click();

        Locator suspendButtonLocator = page.locator(SUSPEND_BUTTON);
        waitForLocator(suspendButtonLocator);
        suspendButtonLocator.click();

        Locator remarksLocator = page.locator(REMARKS);
        waitForLocator(remarksLocator);
        remarksLocator.fill("Buyer Suspended");

        Locator yesButtonLocator = page.locator(YES);
        waitForLocator(yesButtonLocator);
        yesButtonLocator.click();
        iLogout.performLogout();
        iPrEdit.buyerSuspendEdit();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }
}