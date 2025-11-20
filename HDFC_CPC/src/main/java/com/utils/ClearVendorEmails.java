package com.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;

public class ClearVendorEmails {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerUtil.getLogger(ClearVendorEmails.class);

    public static void clearVendorEmails(JsonNode jsonNode) {
        try {
            ObjectNode parentNode = (ObjectNode) jsonNode.get("mailIds");
            ArrayNode emptyArray = objectMapper.createArrayNode();
            parentNode.set("VendorEmails", emptyArray);

            try (FileWriter fileWriter = new FileWriter("./src/test/resources/config/test-data.json")) {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, jsonNode);
            }
        } catch (Exception exception) {
            logger.error("Error in Save Properties Into Json File Function: {}", exception.getMessage());
        }
    }
}