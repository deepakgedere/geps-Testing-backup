package com.utils.screenshots;

import com.microsoft.playwright.Page;
import com.utils.LoggerUtil;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;

public class ScreeenshotUtil {

    static Logger logger;

    public ScreeenshotUtil(Logger logger){
        this.logger = LoggerUtil.getLogger(ScreeenshotUtil.class);
    }

    public static byte[] saveScreenshot(Page page) {
        byte[] screenshot = null;
        try {
            screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
        } catch (Exception exception) {
            logger.error("Error in Save Screenshot Function: {}", exception.getMessage());
        }
        return screenshot;
    }

    public static void attachScreenshotWithName(String screenshotName, Page page) {
        try {
            byte[] screenshot = saveScreenshot(page);
            if (screenshot != null) {
                Allure.addAttachment(screenshotName, "image/png", new ByteArrayInputStream(screenshot), ".png");
            }
        } catch (Exception exception) {
            logger.error("Error in Attach Screenshot With Name Function: {}", exception.getMessage());
        }
    }
}
