package com.factory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.*;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlaywrightFactory {

    JsonNode jsonNode;
    static Logger logger= LoggerUtil.getLogger(PlaywrightFactory.class);;
    Playwright playwright;

//TODO Constructor
    public PlaywrightFactory(JsonNode jsonNode) {
        this.jsonNode = jsonNode;
    }

//TODO Thread Local to execute parallel tests
    private static final ThreadLocal<Playwright> localPlaywright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> localBrowser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> localBrowserContext = new ThreadLocal<>();
    private static final ThreadLocal<Page> localPage = new ThreadLocal<>();

    public void setPlaywright() {
        playwright = Playwright.create();
        localPlaywright.set(playwright);
    }

    public Playwright getPlaywright() {
        if (localPlaywright.get() == null) {
            setPlaywright();
        }
        return localPlaywright.get();
    }

    public Browser getBrowser() {
        return localBrowser.get();
    }

    public BrowserContext getBrowserContext() {
        return localBrowserContext.get();
    }

    public static Page getPage() {
        return localPage.get();
    }

    public void initializeBrowser(JsonNode jsonNode) {
        try {
            String browserName = jsonNode.get("configSettings").get("browserName").asText().toUpperCase();
            boolean runConfig = jsonNode.get("configSettings").get("runHeadless").asBoolean();

            switch (browserName) {
                case "CHROMIUM":
                    localBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(runConfig)));
                    break;
                case "CHROME":
                    localBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(runConfig)));
                    break;
                case "EDGE":
                    localBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(runConfig)));
                    break;
                case "SAFARI":
                    localBrowser.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(runConfig)));
                    break;
                case "FIREFOX":
                    localBrowser.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(runConfig)));
                    break;
                default:
                    logger.error("--Enter Proper Browser Name--");
                    break;
            }
        } catch (Exception exception) {
            logger.error("Error initializing browser: " + exception.getMessage());
        }
    }

    public void initializeBrowserContext() {
        try {
            String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
            Path videoDir = Paths.get("test-output/videos", timestamp);

            BrowserContext context = getBrowser().newContext(new Browser.NewContextOptions()
                    .setRecordVideoDir(videoDir)
            );
            localBrowserContext.set(context);
        } catch (Exception exception) {
            logger.error("Error initializing browser context: " + exception.getMessage());
        }
    }

    public Page initializePage(JsonNode jsonNode) {
        try {
            Page page = getBrowserContext().newPage();
            localPage.set(page);
            page.navigate(jsonNode.get("configSettings").get("appUrl").asText().trim());
        } catch (Exception exception) {
            logger.error("Error initializing page: " + exception.getMessage());
        }
        return getPage();
    }

    public void cleanup() {
        try {
            if (localPage.get() != null) localPage.get().close();
            if (localBrowserContext.get() != null) localBrowserContext.get().close();
            if (localBrowser.get() != null) localBrowser.get().close();
            if (localPlaywright.get() != null) localPlaywright.get().close();
        } catch (Exception e) {
            logger.error("Error during cleanup: " + e.getMessage());
        } finally {
            localPage.remove();
            localBrowserContext.remove();
            localBrowser.remove();
            localPlaywright.remove();
        }
    }
}