package com.coolwin.util;

import com.google.gson.*;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/1/27.
 */

public class GsonUtil {
    static Gson gson = new GsonBuilder()
            .create();
    //将Json数据解析成相应的映射对象
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        T result = gson.fromJson(jsonData, type);
        return result;
    }

    public static <T> T parseJsonWithGsonObject(String jsonData, Type typeOfT) {
        T result = gson.fromJson(jsonData, typeOfT);
        return result;
    }
    public  static String objectToJson(Object object) {
        return gson.toJson(object);
    }

    public static String getObjectStringFromJson(String json,String key){
        JSONObject jsonObject = new JSONObject(json);
        return jsonObject.getJSONObject(key).toString();
    }
}
