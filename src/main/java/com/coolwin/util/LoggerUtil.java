package com.coolwin.util;

import org.apache.log4j.Logger;

/**
 * Created by dell on 2017/5/5.
 */
public class LoggerUtil {
    public static Logger getLogger(Class clazz){
        return Logger.getLogger(clazz);
    }
}
