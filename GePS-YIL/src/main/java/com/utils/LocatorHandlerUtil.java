package com.utils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.apache.logging.log4j.Logger;

public class LocatorHandlerUtil {

    static Logger logger = LoggerUtil.getLogger(LocatorHandlerUtil.class);;

//TODO Constructor
    private LocatorHandlerUtil(){
    }
    public static void locatorVisibleHandler(Locator locator) {
        try {
            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
        } catch(Exception exception) {
            logger.error("Exception in Locator Visible Handler Util for locator [{}]: {}", locator, exception.getMessage());
        }
    }
}