package com.base;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.source.classes.login.Login;
import com.source.classes.logout.Logout;
import com.source.classes.vendors.VendorEDDApproval;
import com.source.classes.vendors.VendorEDDRegistration;
import com.source.classes.vendors.VendorInvite;
import com.source.classes.vendors.VendorRegistration;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.vendors.IVendorEDDApproval;
import com.source.interfaces.vendors.IVendorEDDRegistration;
import com.source.interfaces.vendors.IVendorInvite;
import com.source.interfaces.vendors.IVendorRegistration;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.io.File;

public class BaseMain {

    protected Logger logger = LoggerUtil.getLogger(BaseMain.class);
    protected ObjectMapper objectMapper;
    protected JsonNode jsonNode;
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    protected PlaywrightFactory playwrightFactory;
    protected ILogin iLogin;
    protected ILogout iLogout;
    protected IVendorInvite iVendorInvite;
    protected IVendorEDDRegistration iVendorEDDRegistration;
    protected IVendorEDDApproval iVendorEDDApproval;
    protected IVendorRegistration iVendorRegistration;

    //TODO Constructor
    public BaseMain() {
        try {
            objectMapper = new ObjectMapper();
            jsonNode = objectMapper.readTree(new File("./src/test/resources/config/test-data.json"));
            playwrightFactory = new PlaywrightFactory(objectMapper, jsonNode);
            playwright = playwrightFactory.getPlaywright();
            playwrightFactory.initializeBrowser(jsonNode);
            browser = playwrightFactory.getBrowser();
            playwrightFactory.initializeBrowserContext();
            browserContext = playwrightFactory.getBrowserContext();
            page = playwrightFactory.initializePage(jsonNode);

            iLogin = new Login(jsonNode, page);
            iLogout = new Logout(page);
            iVendorInvite = new VendorInvite(jsonNode, page, iLogin, iLogout);
            iVendorEDDRegistration = new VendorEDDRegistration(jsonNode, page, iLogin, iLogout);
            iVendorEDDApproval = new VendorEDDApproval(jsonNode, page, iLogin, iLogout);
            iVendorRegistration = new VendorRegistration(jsonNode, page, iLogin, iLogout); // Assuming VendorInvite implements IVendorRegistration
        } catch (Exception exception) {
            logger.error("Error Initializing BaseMain Constructor: {}", exception.getMessage());
        }
    }
}