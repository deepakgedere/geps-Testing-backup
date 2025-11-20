package com.util;
import com.base.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import org.testng.annotations.DataProvider;

public class PRTrnUtil extends BaseTest {

    @DataProvider(name = "trnData")
    public static Object[][] getTrnData() {
        Object[][] data = null;
        try {
            // Extract transaction ID
            String trnId = jsonNode.get("purchaseRequisitionNumber").get("prNumber").asText();

            int trnCount = jsonNode.get("purchaseRequisitionNumber").get("prNumberCount").asInt();

            // Correctly initialize the array
            data = new Object[trnCount][1];

            if(trnCount > 1){
                // Populate the array
                for (int i = 0; i < trnCount; i++) {
                    data[i][0] = trnId + " - " + (i + 1);
                }
            }
            else data[0][0] = trnId;
        } catch (Exception exception) {
            logger.error("Exception in Transaction Data Provider function", exception);
        }
        return data;
    }
}