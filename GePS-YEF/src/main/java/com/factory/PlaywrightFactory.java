package com.factory;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Properties;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;

public class PlaywrightFactory {

    Playwright playwright;
    FileInputStream fileInputStream;
    FileOutputStream fileOutputStream;
    Properties properties;

//TODO Constructor
    public PlaywrightFactory() {
    }

//TODO Thread Local
    private static final ThreadLocal<Playwright> localPlaywright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> localBrowser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> localBrowserContext = new ThreadLocal<>();
    private static final ThreadLocal<Page> localPage = new ThreadLocal<>();
    
//TODO Playwright Instance
    public void setPlaywright() {
        playwright = Playwright.create();
        localPlaywright.set(playwright);
    }

    public Playwright getPlaywright() {
        setPlaywright();
        return localPlaywright.get();
    }

//TODO Browser
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
        } catch (IOException error) {
            throw new RuntimeException(error);
        }
        return properties;
    }

    public Page initializePage(Properties properties) {
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
        localPage.set(getBrowser().newPage());
        getPage().navigate(properties.getProperty("appUrl").trim());
        return getPage();
    }

    public static void waitForLocator(Locator locator){
        try {
            locator.waitFor(new Locator.WaitForOptions().setTimeout(5000).setState(WaitForSelectorState.VISIBLE));
        } catch (Exception error) {
            System.out.println("What is the Error: " + error);
        }
    }

    public void saveToPropertiesFile(String attributeKey, String attributeValue) {
        try {
            fileOutputStream = new FileOutputStream("./src/test/resources/config/Properties");
            properties.setProperty(attributeKey, attributeValue);
            properties.store(fileOutputStream, "PoReferenceId");
        } catch (IOException error) {
            throw new RuntimeException(error);
        }
    }

    public void tearDown() {
        try {
            getPage().context().browser().close();
        } catch (Exception error) {
            System.out.println("Error :" + error);
        }
    }

    public static String takeScreenshot(){
        String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
        byte[] buffer = getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
        String base64Path = Base64.getEncoder().encodeToString(buffer);
        return base64Path;
    }
}