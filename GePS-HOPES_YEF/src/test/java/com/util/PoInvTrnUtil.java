package com.util;
import com.base.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import org.testng.annotations.DataProvider;

public class PoInvTrnUtil extends BaseTest {

    @DataProvider(name = "invoiceData")
    public static Object[][] getInvoiceData() {
        Object[][] data = null;
        try {
            JsonNode invoices = jsonNode.get("invoices").get("invoiceTransactionDetails");
            data = new Object[invoices.size()][4];

            int i = 0;
            for (JsonNode invoice : invoices) {
                data[i][0] = invoice.get("referenceId").asText();
                data[i][1] = invoice.get("transactionId").asText();
                data[i][2] = invoice.get("uid").asText();
                data[i][3] = invoice.get("type").asText();
                i++;
            }
        } catch (Exception exception) {
            logger.error("Exception in Invoice Data Provider function: {}", exception.getMessage());
        }
        return data;
    }
}