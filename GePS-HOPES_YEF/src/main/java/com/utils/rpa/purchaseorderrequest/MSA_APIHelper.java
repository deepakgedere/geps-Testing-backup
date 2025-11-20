package com.utils.rpa.purchaseorderrequest;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.RequestOptions;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

public class MSA_APIHelper {

    Logger logger;
    Page page;

//TODO Constructor
    public MSA_APIHelper(Page page) {
        this.page = page;
        this.logger = LoggerUtil.getLogger(MSA_APIHelper.class);
    }

    public void updateStatus(String apiUrl, int id) {
        try {
            page.request().fetch((apiUrl + id), RequestOptions.create());
        } catch (Exception exception) {
            logger.error("Exception in Update Status Function: {}", exception.getMessage());
        }
    }
}