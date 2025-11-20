package com.poc.classes.requisition.edit;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.poc.interfaces.login.ILogin;
import com.poc.interfaces.logout.ILogout;
import com.poc.interfaces.requisitions.IPrApprove;
import com.poc.interfaces.requisitions.IPrAssign;
import com.poc.interfaces.requisitions.IPrEdit;
import com.poc.interfaces.requisitions.IPrSendForApproval;
import java.util.Properties;
import static com.factory.PlaywrightFactory.waitForLocator;
import static com.constants.requisitions.LPrEdit.*;

public class Edit implements IPrEdit {

    private ILogin iLogin;
    private ILogout iLogout;
    private Properties properties;
    private Page page;
    private IPrSendForApproval iPrSendForApproval;
    private IPrApprove iPrApprove;
    private IPrAssign iPrAssign;

    private Edit(){
    }

//TODO Constructor
    public Edit(ILogin iLogin, Properties properties, Page page, ILogout iLogout, IPrSendForApproval iPrSendForApproval, IPrApprove iPrApprove, IPrAssign iPrAssign){
        this.iLogin = iLogin;
        this.properties = properties;
        this.page = page;
        this.iLogout = iLogout;
        this.iPrSendForApproval = iPrSendForApproval;
        this.iPrApprove = iPrApprove;
        this.iPrAssign = iPrAssign;
    }

    public void edit() {
        try {
        iLogin.performLogin(properties.getProperty("requesterEmail"));
        String title = properties.getProperty("orderTitle");
        String getTitle = getTitle(title);
        Locator titleLocator = page.locator(getTitle);
        waitForLocator(titleLocator);
        titleLocator.first().click();

        Locator editButtonLocator = page.locator(EDIT_BUTTON);
        waitForLocator(editButtonLocator);
        editButtonLocator.click();

        Locator updateButtonLocator = page.locator(UPDATE_BUTTON);
        waitForLocator(updateButtonLocator);
        updateButtonLocator.click();

        Locator yesButtonLocator = page.locator(YES);
        waitForLocator(yesButtonLocator);
        yesButtonLocator.click();
        iLogout.performLogout();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void rejectEdit()  {
        try {
        edit();
        iPrSendForApproval.sendForApproval();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void buyerSuspendEdit()  {
        try {
        edit();
        iPrSendForApproval.sendForApproval();
        iPrApprove.approve();
        iPrAssign.buyerManagerLogin();
        iPrAssign.buyerManagerAssign();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }
}