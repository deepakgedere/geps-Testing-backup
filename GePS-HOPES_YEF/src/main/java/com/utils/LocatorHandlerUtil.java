package com.utils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.apache.logging.log4j.Logger;

public class LocatorHandlerUtil {

    static Logger logger;

//TODO Constructor
    private LocatorHandlerUtil(){
    }

    public LocatorHandlerUtil(Logger logger){
        this.logger = LoggerUtil.getLogger(LocatorHandlerUtil.class);
    }

    public static void locatorVisibleHandler(Locator locator) {
        try {
            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
        } catch(Exception exception) {
            logger.error("Exception in Locator Visible Handler Util for locator [{}]: {}", locator, exception.getMessage());
        }
    }

    public static void locatorVisibleHandler(Locator locator, int timeout) {
        try {
            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(timeout));
        } catch(Exception exception) {
            logger.error("Exception in Locator Visible Handler Util for locator [{}]: {}", locator, exception.getMessage());
        }
    }
}