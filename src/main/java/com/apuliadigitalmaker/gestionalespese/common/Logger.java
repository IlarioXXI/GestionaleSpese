package com.apuliadigitalmaker.gestionalespese.common;

import org.slf4j.LoggerFactory;

public class Logger {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    public static void info(String msg) {
        LOGGER.info(msg);
    }

    public static void warning(String msg) {
        LOGGER.warn(msg);
    }

    public static void error(String msg, Throwable th) {
        LOGGER.error(msg, th.getMessage());
    }
}
