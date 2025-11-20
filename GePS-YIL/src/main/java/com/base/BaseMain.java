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
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.*;
import org.apache.logging.log4j.Logger;
import java.io.File;

public class BaseMain {

    protected Logger logger;
    protected ObjectMapper objectMapper;
    protected JsonNode jsonNode;
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    protected PlaywrightFactory playwrightFactory;
    protected ILogin iLogin;
    protected ILogout iLogout;
//TODO Constructor
    public BaseMain(){
        try {
            final ThreadLocal<Playwright> localPlaywright = new ThreadLocal<>();
            final ThreadLocal<Browser> localBrowser = new ThreadLocal<>();
            final ThreadLocal<BrowserContext> localBrowserContext = new ThreadLocal<>();
            final ThreadLocal<Page> localPage = new ThreadLocal<>();
            this.logger = LoggerUtil.getLogger(BaseMain.class);
            objectMapper = new ObjectMapper();
            jsonNode = objectMapper.readTree(new File("./src/test/resources/config/test-data.json"));
            playwrightFactory = new PlaywrightFactory(jsonNode);
            playwright = playwrightFactory.getPlaywright();
            playwrightFactory.initializeBrowser(jsonNode);
            browser = playwrightFactory.getBrowser();
            playwrightFactory.initializeBrowserContext();
            browserContext = playwrightFactory.getBrowserContext();
            page = playwrightFactory.initializePage(jsonNode);
            iLogin = new Login(jsonNode, page, objectMapper);
            iLogout = new Logout(page, jsonNode);
        } catch (Exception exception) {
            logger.error("Error Initializing BaseMain Constructor: {}", exception.getMessage());
        }
    }
}