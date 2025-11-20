package com.utils.rpa.invoiceverification;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.RequestOptions;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

public class IV_APIHelper {

    Logger logger;
    Page page;

//TODO Constructor
    public IV_APIHelper(Page page) {
        this.page = page;
        this.logger = LoggerUtil.getLogger(IV_APIHelper.class);
    }

    public void updateStatus(String apiUrl, String invoiceId) {
        try {
            page.request().fetch(apiUrl + invoiceId, RequestOptions.create());
        } catch (Exception exception) {
            logger.error("Exception in Update Status Function: {}", exception.getMessage());
        }
    }
}