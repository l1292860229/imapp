package com.coolwin.Biz;

import com.coolwin.dao.CommodityMapper;
import com.coolwin.entity.DB.DBCommodity;
import com.coolwin.entity.Picture;
import com.coolwin.entity.appentity.AppCommodity;
import com.coolwin.util.FileUtil;
import com.coolwin.util.GsonUtil;
import com.coolwin.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/5/5.
 */
@Component
public class CommodityBiz extends BaseBiz{
    @Autowired
    CommodityMapper commodityMapper;
    public String  getcommodity(String ka6id,String token,String ypid,String uid,int page){
        List<DBCommodity> list =  commodityMapper.getCommodity(uid,ypid, ((page-1)*20));
        List<AppCommodity> resultList = new ArrayList<>();
        list.forEach(dbCommodity -> {
            resultList.add(dbCommodity.toAppCommodity());
        });
        //返回结果
        appResult.setData(resultList);
        appResult.setStateValue(0,null,null,"/getcommodity");
        return GsonUtil.objectToJson(appResult);
    }
    public String  saveCommodity(String ka6id,String token,String ypid,String uid,String title,
                                 String price,String content,HttpServletRequest request){
        AppCommodity appCommodity = new AppCommodity();
        appCommodity.setYpid(ypid);
        appCommodity.setUid(Integer.valueOf(uid));
        appCommodity.setTitle(title);
        appCommodity.setShopurl("http://shop.wei20.cn/gouwu/wishmb/home.shtml?id="+ypid+"&token="+token);
        try{
            appCommodity.setPrice(Double.parseDouble(price));
        }catch (NumberFormatException e){}
        appCommodity.setContent(content);
        upload(appCommodity,request);
        commodityMapper.insertCommodity(appCommodity.toDBCommodity());
        appResult.setData(null);
        appResult.setStateValue(0,null,null,"/savecommodity");
        return GsonUtil.objectToJson(appResult);
    }
    public String  deleteCommodity(String commodityId){
        commodityMapper.deleteCommodity(commodityId);
        appResult.setData(null);
        appResult.setStateValue(0,null,null,"/deleteCommodity");
        return GsonUtil.objectToJson(appResult);
    }
    public void upload(AppCommodity appCommodity,HttpServletRequest request){
        String root =  request.getSession().getServletContext().getRealPath("/");
        List<MultipartFile> files = new ArrayList<>();
        MultipartFile file = ((MultipartHttpServletRequest)request).getFile("picture");
        files.add(file);
        for(int i=0;i<10;i++){
            file = ((MultipartHttpServletRequest)request).getFile("picture"+i);
            if(file!=null){
                files.add(file);
            }
        }
        String contextpath = request.getScheme() +"://" + request.getServerName()  + ":" +request.getServerPort() +request.getContextPath();
        //上传图片时,对图片做处理
        if(files!=null && files.size()>0){
            List<Picture>   pictures = FileUtil.uploadImage(root,contextpath,files,"commodity");
            appCommodity.setPicture(pictures);
        }
        //上传视频时,对视频做处理
        AppCommodity.Video video = appCommodity.new Video();
        files = ((MultipartHttpServletRequest)request).getFiles("video");
        List<String> strings=null;
        if(files!=null && files.size()>0){
            strings = FileUtil.upload(root,contextpath,files,"commodity","video");
            video.url =strings.get(0);
        }
        files = ((MultipartHttpServletRequest)request).getFiles("videoimage");
        if(files!=null && files.size()>0){
            strings = FileUtil.upload(root,contextpath,files,"commodity","video");
            video.image =strings.get(0);
        }
        String videotime = request.getParameter("videotime");
        if(!StringUtil.isNull(videotime)){
            video.time = request.getParameter("videotime");
        }
        appCommodity.setVideo(video);
    }

}
