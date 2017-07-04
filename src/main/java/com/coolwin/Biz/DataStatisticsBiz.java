package com.coolwin.Biz;

import com.coolwin.entity.appentity.AppDataStatistics;
import com.coolwin.entity.appentity.AppResult;
import com.coolwin.entity.constant.ThirdUrlConstant;
import com.coolwin.util.GsonUtil;
import com.coolwin.util.HttpRequestor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/1.
 */
@Component
public class DataStatisticsBiz {
    public String getDataStatistics(String ypid,String token){
        AppResult appResult = new AppResult();
        try {
            String data = HttpRequestor.doGet(ThirdUrlConstant.SHOPTJ_URL+"?id="+ypid+"&token="+token);
            data = data.replace("({","")
                    .replace("})","")
                    .replace(",,",",");
            Map<String,String> map = GsonUtil.parseJsonWithGson(data,Map.class);
            List<AppDataStatistics> dataStatistics = new ArrayList<>();
            for (String s : map.keySet()) {
                if(s.equals("title")){
                    continue;
                }
                AppDataStatistics appDataStatistics = new AppDataStatistics();
                appDataStatistics.key = s;
                try{
                    appDataStatistics.value = Double.parseDouble(map.get(s));
                }catch(NumberFormatException e){appDataStatistics.value=0;}
                dataStatistics.add(appDataStatistics);
            }
            appResult.setData(dataStatistics);
        } catch (Exception e) {
            e.printStackTrace();
            appResult.setStateValue(1,"后台数据请求异常",null,"");
            return GsonUtil.objectToJson(appResult);
        }
        appResult.setStateValue(0,null,null,"");
        return GsonUtil.objectToJson(appResult);
    }
}
