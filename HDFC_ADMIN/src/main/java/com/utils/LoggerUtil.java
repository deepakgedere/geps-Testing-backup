package com.utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {

//TODO Constructor
    public LoggerUtil(){
    }

    public static Logger getLogger(Class<?> className) {
        return LogManager.getLogger(className);
    }
}