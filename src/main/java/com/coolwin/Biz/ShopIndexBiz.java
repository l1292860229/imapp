package com.coolwin.Biz;

import com.coolwin.dao.ShopIndexMapper;
import com.coolwin.entity.DB.DBShopIndex;
import com.coolwin.entity.appentity.AppIndex;
import com.coolwin.entity.appentity.AppResult;
import com.coolwin.entity.constant.ThirdUrlConstant;
import com.coolwin.entity.publicentity.BannerList;
import com.coolwin.entity.thirdentity.ShopIndex;
import com.coolwin.util.FileUtil;
import com.coolwin.util.GsonUtil;
import com.coolwin.util.HttpRequestor;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

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
        }else{
            try {
               String outString =  HttpRequestor.doGet(ThirdUrlConstant.GET_API_URL+"?act=s_home&id="+ypid+"&ka6id="+ka6id);
                ShopIndex thirdShopIndex =  GsonUtil.parseJsonWithGson(outString,ShopIndex.class);
                List<AppIndex> appIndices = new ArrayList<>();
                if (thirdShopIndex.bannerList!=null &&thirdShopIndex.bannerList.size()>0) {
                    AppIndex appIndex = new AppIndex();
                    appIndex.bannerList = thirdShopIndex.bannerList;
                    appIndices.add(appIndex);
                }
                List<ShopIndex.Plist> plists =  thirdShopIndex.plist;
                if(plists!=null&& plists.size()>0){
                    List<AppIndex.Data> dataList = new ArrayList<>();
                    for (ShopIndex.Plist plist : plists) {
                        AppIndex.Data data = new AppIndex().new Data();
                        data.shopImageUrl = plist.p_url;
                        data.shopLink = plist.p_link;
                        int widthSize = Integer.valueOf(plist.p_class.replace("ys_",""));
                        int size = 100/widthSize;
                        dataList.add(data);
                        if(size == dataList.size()){
                            AppIndex appIndex = new AppIndex();
                            appIndex.datas = dataList;
                            appIndices.add(appIndex);
                            dataList = new ArrayList<>();
                        }
                    }
                }
                appResult.setData(new String(Base64Utils.encode(GsonUtil.objectToJson(appIndices).getBytes())));
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        String datamode = new String(Base64.getDecoder().decode(data));
        List<AppIndex> appIndices = GsonUtil.parseJsonWithGsonObject(datamode,new TypeToken<List<AppIndex>>() {
        }.getType());
        ShopIndex thirdShopIndex = new ShopIndex();
        thirdShopIndex.msg="sss";
        thirdShopIndex.code = "0";
        thirdShopIndex.title=null;
        List<ShopIndex.Plist> plists = new ArrayList<>();
        for (AppIndex appIndex : appIndices) {
            if (appIndex.bannerList!=null && appIndex.bannerList.size()>0) {
                thirdShopIndex.bannerList = appIndex.bannerList;
                continue;
            }
            List<AppIndex.Data> dataList =  appIndex.datas;
            if(dataList.size()==0){
                continue;
            }
            String key = (100/dataList.size())+"";
            for (AppIndex.Data data1 : dataList) {
                ShopIndex.Plist plist = thirdShopIndex.new Plist();
                plist.p_link = data1.shopLink;
                plist.p_url = data1.shopImageUrl;
                plist.p_name = "";
                plist.p_class="ys_"+key;
                plists.add(plist);
            }
        }
        thirdShopIndex.plist = plists;
        Map<String, Object> mapparam = new HashMap<>();
        mapparam.put("act","s_home");
        mapparam.put("id",ypid);
        mapparam.put("ka6id",ka6id);
        try {
            mapparam.put("data", URLEncoder.encode(GsonUtil.objectToJson(thirdShopIndex), "utf-8"));
//            String param = "act=s_home&id="+ypid+"&ka6id="+ka6id+"&data="+URLEncoder.encode(GsonUtil.objectToJson(shopIndex1), "utf-8");
//            String postData = NetUtil.doPost(ThirdUrlConstant.SHOPINDEX_URL, param);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            HttpRequestor.doPost(ThirdUrlConstant.POST_API_URL, mapparam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        appResult.setStateValue(0,null,null,"/shopindex");
        return GsonUtil.objectToJson(appResult);
    }
    public String gettemplatepic(HttpServletRequest request){
        String root =  request.getSession().getServletContext().getRealPath("/");
        String contextpath = request.getScheme() +"://" + request.getServerName()  + ":" +request.getServerPort()+"/picture/";
        AppResult appResult = new AppResult();
        //轮播图
        File file = new File(root+"..\\picture\\template\\lunbo");
        List<AppIndex> appIndices = new ArrayList<>();
        if (file.exists()) {
            appIndices.addAll(getDataModellunBo(contextpath,file,root));
        }
        //一张的模板
        file = new File(root+"..\\picture\\template\\1");
        if (file.exists()) {
            appIndices.addAll(getDataModel(contextpath,file,root));
        }
        //两张的模板
        file = new File(root+"..\\picture\\template\\2");
        if (file.exists()) {
            appIndices.addAll(getDataModel(contextpath,file,root));
        }
        //三张的模板
        file = new File(root+"..\\picture\\template\\3");
        if (file.exists()) {
            appIndices.addAll(getDataModel(contextpath,file,root));
        }
        //四张的模板
        file = new File(root+"..\\picture\\template\\4");
        if (file.exists()) {
            appIndices.addAll(getDataModel(contextpath,file,root));
        }
        appResult.setData(appIndices);
        appResult.setStateValue(0,null,null,"/shopindex");
        return GsonUtil.objectToJson(appResult);
    }
    public  List<AppIndex> getDataModellunBo(String root, File file, String replaypath){
        List<AppIndex> appIndices = new ArrayList<>();
        File[] files =  file.listFiles();
        for (File file1 : files) {
            List<String> filelist =  FileUtil.traverseFolder(root,file1.getAbsolutePath(),replaypath);
            AppIndex appIndex = new AppIndex();
            List<BannerList> dataList = new ArrayList<>();
            filelist.forEach(s -> {
                BannerList bannerList = new BannerList();
                bannerList.imgUrl = s;
                dataList.add(bannerList);
            });
            appIndex.bannerList = dataList;
            appIndices.add(appIndex);
        }
        return appIndices;
    }
    public  List<AppIndex> getDataModel(String root, File file, String replaypath){
        List<AppIndex> appIndices = new ArrayList<>();
        File[] files =  file.listFiles();
        for (File file1 : files) {
            List<String> filelist =  FileUtil.traverseFolder(root,file1.getAbsolutePath(),replaypath);
            AppIndex appIndex = new AppIndex();
            List<AppIndex.Data> dataList = new ArrayList<>();
            filelist.forEach(s -> {
                AppIndex.Data data =   appIndex.new Data();
                data.shopImageUrl = s;
                dataList.add(data);
            });
            appIndex.datas = dataList;
            appIndices.add(appIndex);
        }
        return appIndices;
    }
}
