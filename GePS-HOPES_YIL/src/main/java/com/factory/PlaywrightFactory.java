package com.factory;
import com.microsoft.playwright.*;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Properties;

public class PlaywrightFactory {

    static Logger logger;
    Playwright playwright;
    FileInputStream fileInputStream;
    FileOutputStream fileOutputStream;
    Properties properties;

//TODO Constructor
    public PlaywrightFactory() {
        logger = LoggerUtil.getLogger(PlaywrightFactory.class);
    }

//TODO Thread Local to avoid flaky tests/to execute parallel tests
    private static final ThreadLocal<Playwright> localPlaywright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> localBrowser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> localBrowserContext = new ThreadLocal<>();
    private static final ThreadLocal<Page> localPage = new ThreadLocal<>();

    public void setPlaywright() {
        playwright = Playwright.create();
        localPlaywright.set(playwright);
    }

    public Playwright getPlaywright() {
        setPlaywright();
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

    public Properties initializeProperties() {
        try {
            fileInputStream = new FileInputStream("./src/test/resources/config/Properties");
            properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException error) {
            logger.error("Error in Initialize Property Function: " + error.getMessage());
        }
        return properties;
    }

    public Page initializePage(Properties properties) {
        try {
            String browserName = properties.getProperty("browserType").trim().toUpperCase();
            switch (browserName.toUpperCase()) {
                case "CHROMIUM":
                    localBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                    break;
                case "CHROME":
                    localBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false)));
                    break;
                case "EDGE":
                    localBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(false)));
                    break;
                case "SAFARI":
                    localBrowser.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                    break;
                case "FIREFOX":
                    localBrowser.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                    break;
                default:
                    System.out.println("--Enter Proper Browser Name--");
                    break;
            }
            localBrowserContext.set(getBrowser().newContext());
            localPage.set(getBrowserContext().newPage());
            getPage().navigate(properties.getProperty("appUrl").trim());
        } catch (Exception error) {
            logger.error("Error in Initialize Page Function: " + error.getMessage());
        }
        return getPage();
    }

    public void saveToPropertiesFile(String attributeKey, String attributeValue) {
        try {
            fileOutputStream = new FileOutputStream("./src/test/resources/config/Properties");
            properties.setProperty(attributeKey, attributeValue);
            properties.store(fileOutputStream, attributeKey);
            fileOutputStream.close();
        } catch (Exception error) {
            logger.error("Error in Save to Properties File Function: " + error.getMessage());
        }
    }

    public static String takeScreenshot(){
        String base64Path = "";
        try {
            String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
            byte[] buffer = getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
            base64Path = Base64.getEncoder().encodeToString(buffer);
        } catch (Exception error) {
            logger.error("Error in Take Screenshot Function: " + error.getMessage());
        }
        return base64Path;
    }
}