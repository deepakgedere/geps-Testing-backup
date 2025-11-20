package com.utils;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.Logger;

public class GetTitleUtil {

    static Logger logger;
    static JsonNode jsonNode;

//TODO Constructor
    private GetTitleUtil(){
    }

    public GetTitleUtil(JsonNode jsonNode, Logger logger){
        this.jsonNode = jsonNode;
        this.logger = logger;
    }

    public static String getTransactionTitle(String type, String purchaseType) {
        String title = "";
        try {
            String jsonTitleKey;
            if (type.equalsIgnoreCase("PS")) {
                if(purchaseType.equalsIgnoreCase("Catalog") || purchaseType.equalsIgnoreCase("Catalog BOP2/BOP5")) {
                    jsonTitleKey = "psCatalogTitle";
                } else if (purchaseType.equalsIgnoreCase("Punchout")){
                    jsonTitleKey = "punchOutCatalogTitle";
                } else {
                    jsonTitleKey = "psNonCatalogTitle";
                }
            } else if (type.equalsIgnoreCase("SALES")) {
                jsonTitleKey = purchaseType.equalsIgnoreCase("Catalog") ? "salesCatalogTitle" : "salesNonCatalogTitle";
            } else{
                if(purchaseType.equalsIgnoreCase("Catalog") || purchaseType.equalsIgnoreCase("Catalog BOP2/BOP5")) {
                    jsonTitleKey = "sdCatalogTitle";
                } else if (purchaseType.equalsIgnoreCase("Punchout")){
                    jsonTitleKey = "punchOutCatalogTitle";
                } else {
                    jsonTitleKey = "sdNonCatalogTitle";
                }
            }
            title = jsonNode.get("requisition").get(jsonTitleKey).asText();
        } catch (Exception exception) {
            logger.error("Exception Initializing Get Transaction Title Function: {}", exception.getMessage());
        }
        return title;
    }

    public static String getRFQTransactionTitle(String type) {
        String title = "";
        try {
            String jsonTitleKey;
            if(type.equalsIgnoreCase("PS")){
                jsonTitleKey = jsonNode.get("requisition").get("psNonCatalogTitle").asText();
            } else if (type.equalsIgnoreCase("SALES")) {
                jsonTitleKey = jsonNode.get("requisition").get("salesNonCatalogTitle").asText();
            } else {
                jsonTitleKey = jsonNode.get("requisition").get("sdNonCatalogTitle").asText();
            }

            title = jsonTitleKey;
        } catch (Exception exception) {
            logger.error("Exception Initializing Get RFQ Transaction Title Function: {}", exception.getMessage());
        }
        return title;
    }
}
