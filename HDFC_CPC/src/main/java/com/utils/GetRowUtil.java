package com.utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.apache.logging.log4j.Logger;

public class GetRowUtil {

    private static JsonNode jsonNode;
    static ObjectMapper objectMapper;
    static Logger logger;


    //TODO Constructor
    public GetRowUtil(JsonNode jsonNode) {
        this.jsonNode = jsonNode;
        this.objectMapper = new ObjectMapper();
        logger = LoggerUtil.getLogger(GetRowUtil.class);
    }

    public static Locator getRow(Page page, String transactionId) {
        return page.locator("//tr[.//td[contains(text(), '" + transactionId.trim() + "')]]");
    }

    public static Locator getPoRow(Page page, String transactionId) {
        String trnNumber = transactionId.replaceFirst("TR-", "").trim();
        return page.locator("(//tr[.//span[contains(text(),'" + trnNumber.trim() + "')]])[1]");
    }

    public static Locator getPoRowVendor(Page page, String transactionId) {
        String trnNumber = transactionId.replaceFirst("TR-", "").trim();
        return page.locator("//tr[.//td[contains(text(),'" + trnNumber.trim() + "')]]");
    }
}