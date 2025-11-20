package com.utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;

public class SaveToJsonUtil {

    private static JsonNode jsonNode;
    static ObjectMapper objectMapper;
    static Logger logger;


//TODO Constructor
    public SaveToJsonUtil(JsonNode jsonNode){
        this.jsonNode = jsonNode;
        this.objectMapper = new ObjectMapper();
        logger = LoggerUtil.getLogger(SaveToJsonUtil.class);
    }

    public static void saveToJson(String parentKey, String attributeKey, String attributeValue) {
        try {
            if (jsonNode.has(parentKey) && jsonNode.get(parentKey).isObject()) {
                ObjectNode parentNode = (ObjectNode) jsonNode.get(parentKey);
                parentNode.put(attributeKey, attributeValue);

//TODO try is used to close the file writer or the json file will be empty
                try (FileWriter fileWriter = new FileWriter("./src/test/resources/config/test-data.json")) {
                    objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, jsonNode);
                }
            } else {
                logger.warn("Parent key '{}' not found or not an object in JSON", parentKey);
            }
        } catch (Exception exception) {
            logger.error("Error in Save Properties Into Json File Function: {}", exception.getMessage());
        }
    }

    public static void appendToJsonArray(String parentKey, String attributeKey, String attributeValue) {
        try {
            ObjectNode parentNode;
            if (jsonNode.has(parentKey) && jsonNode.get(parentKey).isArray()) {
                // Parent key exists and is an array
                var arrayNode = (com.fasterxml.jackson.databind.node.ArrayNode) jsonNode.get(parentKey);
                ObjectNode newObject = objectMapper.createObjectNode();
                newObject.put(attributeKey, attributeValue);
                arrayNode.add(newObject);
            } else {
                // Create a new array node
                var arrayNode = objectMapper.createArrayNode();
                ObjectNode newObject = objectMapper.createObjectNode();
                newObject.put(attributeKey, attributeValue);
                arrayNode.add(newObject);
                if (jsonNode instanceof ObjectNode) {
                    ((ObjectNode) jsonNode).set(parentKey, arrayNode);
                }
            }
            try (FileWriter fileWriter = new FileWriter("./src/test/resources/config/test-data.json")) {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, jsonNode);
            }
        } catch (Exception exception) {
            logger.error("Error in Save Properties Into Json File Function: {}", exception.getMessage());
        }
    }

}