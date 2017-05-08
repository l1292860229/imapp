package com.coolwin.util;

/**
 * Created by Administrator on 2017/1/21.
 */

public class StringUtil {
    public static boolean isNull(String str){
        if(str==null || str.equals("") || str.toLowerCase().equals("null")){
            return true;
        }
        return false;
    }
}
