package com.coolwin.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dell on 2017/5/12.
 */
public class DataUtil {
    public static final String  YYYY_MM_DD = "yyyyMMdd";
    static SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
    public static String format(Date date, String format ){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
    public static String format(Date date){
        return dateFormat.format(date);
    }
}
