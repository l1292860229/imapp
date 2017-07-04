package com.coolwin.Biz;

import com.coolwin.entity.appentity.AppResult;
import com.coolwin.entity.constant.ThirdUrlConstant;
import com.coolwin.entity.thirdentity.OrderList;
import com.coolwin.util.GsonUtil;
import com.coolwin.util.HttpRequestor;
import org.springframework.stereotype.Component;

/**
 * Created by dell on 2017/7/1.
 */
@Component
public class OrderBiz {

    public String getOrderList(String ypid,String s,String token,int page){
        AppResult appResult = new AppResult();
        try {
            String data = HttpRequestor.doGet(ThirdUrlConstant.ORDERLIST_URL+"?ypid="+ypid+"&token="+token+(s==null?"":"&s="+s)+"&page="+page);
            OrderList orderList = GsonUtil.parseJsonWithGson(data,OrderList.class);
            if(orderList.code.equals("200")){
                appResult.setData(orderList.list);
            }else{
                appResult.setStateValue(1,orderList.msg,null,"");
                return GsonUtil.objectToJson(appResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
            appResult.setStateValue(1,"后台数据请求异常",null,"");
            return GsonUtil.objectToJson(appResult);
        }
        appResult.setStateValue(0,null,null,"");
        return GsonUtil.objectToJson(appResult);
    }
    public String upateOrderListStatus(String ypid,String s,String token,String orderid){
        AppResult appResult = new AppResult();
        try {
            String data = HttpRequestor.doGet(ThirdUrlConstant.UPDATEORDER_URL+"?ypid="+ypid+"&token="+token+"&s="+s+"&orderid="+orderid);
            OrderList orderList = GsonUtil.parseJsonWithGson(data,OrderList.class);
            if(!orderList.code.equals("200")){
                appResult.setStateValue(1,orderList.msg,null,"");
                return GsonUtil.objectToJson(appResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
            appResult.setStateValue(1,"后台数据请求异常",null,"");
            return GsonUtil.objectToJson(appResult);
        }
        appResult.setStateValue(0,"更新成功",null,"");
        return GsonUtil.objectToJson(appResult);
    }
}
