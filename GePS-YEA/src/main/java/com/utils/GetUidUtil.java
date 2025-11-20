package com.utils;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.Logger;

public class GetUidUtil {

    static Logger logger;
    static JsonNode jsonNode;

    //TODO Constructor
    private GetUidUtil() {
    }

    public GetUidUtil(JsonNode jsonNode, Logger logger) {
        this.jsonNode = jsonNode;
        this.logger = logger;
    }

    public static String getUid(String type) {
        System.out.println("jhg");
        return null;
    }
}
