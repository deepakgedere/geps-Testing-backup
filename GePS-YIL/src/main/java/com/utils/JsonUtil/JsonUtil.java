package com.utils.JsonUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class JsonUtil {

    private static final String BASE_PATH = "src/test/resources/config/";

    public static void savePropertiesIntoJsonFile(String key, String value, ObjectMapper mapper, JsonNode jsonNode) {
        String threadFile = "src/test/resources/config/" + jsonNode.get("output").get("jsonFilePath").asText() + ".json";

        try {
            File jsonFile = Paths.get(threadFile).toFile();

            if (jsonNode instanceof ObjectNode objectNode) {
                objectNode.put(key, value);
                // Save back to file
                mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, objectNode);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to update JSON for thread " + jsonNode.get("output").get("jsonFilePath").asText() + ": " + e.getMessage(), e);
        }
    }

    public static void updateJson(List<String> keyPath, String value, ObjectMapper mapper, JsonNode jsonNode) {
        if (keyPath == null || keyPath.size() < 1) {
            throw new IllegalArgumentException("Key path must contain at least one key.");
        }

        String threadFile = "src/test/resources/config/" + jsonNode.get("output").get("jsonFilePath").asText() + ".json";

        try {
            File jsonFile = Paths.get(threadFile).toFile();

            if (!(jsonNode instanceof ObjectNode rootNode)) {
                throw new RuntimeException("Root node is not an ObjectNode.");
            }

            ObjectNode currentNode = rootNode;

            // Traverse to the second last key
            for (int i = 0; i < keyPath.size() - 1; i++) {
                String key = keyPath.get(i);
                JsonNode childNode = currentNode.get(key);

                if (childNode == null || !childNode.isObject()) {
                    throw new RuntimeException("Path invalid: Key '" + key + "' not found or is not an object.");
                }

                currentNode = (ObjectNode) childNode;
            }

            // Final key
            String finalKey = keyPath.get(keyPath.size() - 1);
            if (!currentNode.has(finalKey)) {
                throw new RuntimeException("Final key '" + finalKey + "' does not exist under path.");
            }

            // Update value
            currentNode.put(finalKey, value);

            // Write back to file
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);

        } catch (IOException e) {
            throw new RuntimeException("Failed to update JSON for thread " + jsonNode.get("output").get("jsonFilePath").asText() + ": " + e.getMessage(), e);
        }
    }

    public static void updateJson(String dotSeparatedPath, String value, ObjectMapper mapper, JsonNode jsonNode) {
        if (dotSeparatedPath == null || dotSeparatedPath.isEmpty()) {
            throw new IllegalArgumentException("Key path cannot be null or empty.");
        }

        List<String> keyPath = Arrays.asList(dotSeparatedPath.split("\\."));

        // Handle single-key path (top-level key)
        if (keyPath.size() == 1) {
            String key = keyPath.get(0);
            String threadFile = "src/test/resources/config/" + jsonNode.get("output").get("jsonFilePath").asText() + ".json";

            try {
                File jsonFile = Paths.get(threadFile).toFile();

                if (!(jsonNode instanceof ObjectNode objectNode)) {
                    throw new RuntimeException("Root node is not an ObjectNode.");
                }

                if (!objectNode.has(key)) {
                    throw new RuntimeException("Top-level key '" + key + "' does not exist.");
                }

                objectNode.put(key, value);
                mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, objectNode);
            } catch (IOException e) {
                throw new RuntimeException("Failed to update JSON: " + e.getMessage(), e);
            }

        } else {
            // Delegate to the nested version
            updateJson(keyPath, value, mapper, jsonNode);
        }
    }

}
