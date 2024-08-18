package com.example.mingchao_boot.Aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    public static void info(String info){
        Logger logger = LoggerFactory.getLogger(Log.class);
        logger.info("日志信息:"+info);
    }
    public static void error(String info){
        Logger logger = LoggerFactory.getLogger(Log.class);
        logger.error("日志信息:"+info);
    }
}
