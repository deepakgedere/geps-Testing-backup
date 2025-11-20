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
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {

    public Logger logger = LoggerUtil.getLogger(BaseTest.class);
    public ObjectMapper objectMapper;
    public JsonNode jsonNode;
    public PlaywrightFactory playwrightFactory;
    public Playwright playwright;
    public Browser browser;
    public BrowserContext browserContext;
    public Page page;
    public String traceFileName;
    public ILogin iLogin;
    public ILogout iLogout;
    public IVendorInvite iVendorInvite;
    public IVendorEDDRegistration iVendorEDDRegistration;
    public IVendorEDDApproval iVendorEDDApproval;
    public IVendorRegistration iVendorRegistration;

    //TODO Constructor
    public BaseTest() {
        try {
            objectMapper = new ObjectMapper();
            jsonNode = objectMapper.readTree(new File("src/test/resources/config/test-data.json"));

            String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
            traceFileName = "HDFC_ADMIN-Trace_Suite_" + timestamp + ".zip";

            playwrightFactory = new PlaywrightFactory(objectMapper, jsonNode);
            playwrightFactory.setPlaywright();
            playwright = playwrightFactory.getPlaywright();
            playwrightFactory.initializeBrowser(jsonNode);
            browser = playwrightFactory.getBrowser();
            playwrightFactory.initializeBrowserContext();
            browserContext = playwrightFactory.getBrowserContext();
            page = playwrightFactory.initializePage(jsonNode);
            playwrightFactory.startTracing(browserContext, traceFileName);

            iLogin = new Login(jsonNode, page);
            iLogout = new Logout(page);
            iVendorInvite = new VendorInvite(jsonNode, page, iLogin, iLogout);
            iVendorEDDRegistration = new VendorEDDRegistration(jsonNode, page, iLogin, iLogout);
            iVendorEDDApproval = new VendorEDDApproval(jsonNode, page, iLogin, iLogout);
            iVendorRegistration = new VendorRegistration(jsonNode, page, iLogin, iLogout);
        } catch (Exception exception) {
            logger.error("Error initializing BaseTest: {}", exception.getMessage());
        }
    }

    // Add this method to your BaseTest class
    public void close() {
        try {
            if (browserContext != null) {
                browserContext.close();
            }
            if (browser != null) {
                browser.close();
            }
            if (playwright != null) {
                playwright.close();
            }
        } catch (Exception e) {
            logger.error("Error during cleanup: {}", e.getMessage());
        }
    }
}