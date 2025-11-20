package com.utils.rpa.orderacknowledgement;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.RequestOptions;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

public class OA_APIHelper {

    Logger logger;
    Page page;

//TODO Constructor
    public OA_APIHelper(Page page) {
        this.page = page;
        this.logger = LoggerUtil.getLogger(OA_APIHelper.class);
    }

    public void updateStatus(String apiUrl, String poId) {
        try {
            page.request().fetch(apiUrl + poId, RequestOptions.create());
        } catch (Exception exception) {
            logger.error("Exception in Update Status Function: {}", exception.getMessage());
        }
    }
}