package com.coolwin.Biz;

import com.coolwin.dao.ShopIndexMapper;
import com.coolwin.entity.DB.DBShopIndex;
import com.coolwin.entity.appentity.AppResult;
import com.coolwin.util.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dell on 2017/5/5.
 */
@Component
public class ShopIndexBiz extends BaseBiz{
    @Autowired
    ShopIndexMapper shopIndexMapper;
    public String  getShopIndex(String ka6id,String token,String ypid,String uid){
        AppResult appResult = new AppResult();
        DBShopIndex shopIndex =  shopIndexMapper.getShopIndex(uid,ka6id,ypid);
        if(shopIndex!=null){
            //返回结果
            appResult.setData(shopIndex.getShopindex());
        }
        appResult.setStateValue(0,null,null,"/shopindex");
        return GsonUtil.objectToJson(appResult);
    }
    public String  insertOrUpdateShopIndex(String ka6id,String token,String ypid,String uid,String data){
        AppResult appResult = new AppResult();
        DBShopIndex shopIndex =  shopIndexMapper.getShopIndex(uid,ka6id,ypid);
        if(shopIndex!=null){
            shopIndexMapper.updateShopIndex(uid,ka6id,ypid,data);
        }else{
            shopIndexMapper.insertShopIndex(uid,ka6id,ypid,data);
        }
        //返回结果
        appResult.setStateValue(0,null,null,"/shopindex");
        return GsonUtil.objectToJson(appResult);
    }
}
