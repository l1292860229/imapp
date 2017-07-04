package com.coolwin.Biz;

import com.coolwin.dao.InformationMapper;
import com.coolwin.entity.DB.DBInformation;
import com.coolwin.entity.Picture;
import com.coolwin.entity.appentity.AppCommodity;
import com.coolwin.entity.appentity.AppResult;
import com.coolwin.entity.constant.ThirdUrlConstant;
import com.coolwin.entity.thirdentity.Information;
import com.coolwin.util.FileUtil;
import com.coolwin.util.GsonUtil;
import com.coolwin.util.HttpRequestor;
import com.coolwin.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/5/5.
 */
@Component
public class InformationBiz extends BaseBiz{
    @Autowired
    InformationMapper informationMapper;
    public String  getInformation(String ka6id,String token,String ypid,String uid,int page,String type){
        if(page==0){
            page = 1;
        }
        AppResult appResult = new AppResult();
        List<DBInformation> list =  informationMapper.getInformation(uid,ypid,type, ((page-1)*20));
        List<AppCommodity> resultList = new ArrayList<>();
        list.forEach(dbInformation -> {
            resultList.add(dbInformation.toAppCommodity());
        });
        if (resultList.size()==0) {
            try {
                String typeid="";
                if(type.equals("资讯")){
                    typeid = "3639";
                }
                String outString =  HttpRequestor.doGet(ThirdUrlConstant.TOP_URL+"?nid="+typeid+"&id="+ypid+"&start="+(page-1)+"&count=20");
                Information information = GsonUtil.parseJsonWithGson(outString,Information.class);
                if (information.subjects!=null&& information.subjects.size()>0) {
                    information.subjects.forEach( subjects -> {
                        AppCommodity appCommodity = new AppCommodity();
                        appCommodity.setTitle(subjects.Name);
                        appCommodity.setId(subjects.id);
                        appCommodity.setPrice(Double.valueOf(subjects.LowSellPrice));
                        if(type.equals("资讯")){
                            appCommodity.setShopurl("pages/smore/smore?sid="+subjects.id+"&id=585&nid=3639");
                        }else{
                            appCommodity.setShopurl("filmDetail/filmDetail?sid="+subjects.id+"&id=585");
                        }
                        List<Picture> picture = new ArrayList<>();
                        picture.add(new Picture(subjects.url));
                        appCommodity.setPicture(picture);
                        appCommodity.setType(type);
                        resultList.add(appCommodity);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //返回结果
        appResult.setData(resultList);
        appResult.setStateValue(0,null,null,"/getInformation");
        return GsonUtil.objectToJson(appResult);
    }
    public String  saveInformation(String ka6id,String token,String ypid,String uid,String title,
                                 String price,String content,String type,HttpServletRequest request){
        AppResult appResult = new AppResult();
        if(StringUtil.isNull(title)){
            appResult.setStateValue(1,"title为空",null,"/saveInformation");
            return GsonUtil.objectToJson(appResult);
        }
        if(StringUtil.isNull(content)){
            appResult.setStateValue(1,"content为空",null,"/saveInformation");
            return GsonUtil.objectToJson(appResult);
        }
        if(StringUtil.isNull(type)){
            appResult.setStateValue(1,"type为空",null,"/saveInformation");
            return GsonUtil.objectToJson(appResult);
        }
        AppCommodity appCommodity = new AppCommodity();
        appCommodity.setYpid(ypid);
        appCommodity.setUid(Integer.valueOf(uid));
        appCommodity.setTitle(title);
        appCommodity.setType(type);
        if(type.equals("资讯")){
            appCommodity.setShopurl("pages/smore/smore?sid=&id=585&nid=3639");
        }else{
            appCommodity.setShopurl("filmDetail/filmDetail?sid=&id=585");
        }
        try{
            appCommodity.setPrice(Double.parseDouble(price));
        }catch ( NumberFormatException |NullPointerException e){
            appCommodity.setPrice(0);
        }
        appCommodity.setContent(content);
        upload(appCommodity,request);
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("act","s_product");
        paramMap.put("a","add");
        paramMap.put("id",appCommodity.getYpid());
        paramMap.put("ka6_id",ka6id);
        try {
            paramMap.put("imageUrl", URLEncoder.encode(GsonUtil.objectToJson(appCommodity.getPicture()), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        paramMap.put("title",appCommodity.getTitle());
        paramMap.put("price",appCommodity.getPrice()+"");
        if(appCommodity.getVideo()!=null){
            paramMap.put("videoUrl",appCommodity.getVideo().url);
            paramMap.put("videoImageUrl",appCommodity.getVideo().image);
        }
        paramMap.put("description",appCommodity.getContent());
        paramMap.put("type",type.equals("资讯")?"2":"1");
        String outString = null;
        try {
            outString = HttpRequestor.doPost(ThirdUrlConstant.POST_API_URL,paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        informationMapper.insertInformation(appCommodity.toDBCommodity());
        appResult.setData(null);
        appResult.setStateValue(0,null,null,"/saveInformation");
        return GsonUtil.objectToJson(appResult);
    }
    public String  deleteInformation(String ypid,String commodityId,String ka6id,String type){
        AppResult appResult = new AppResult();
        informationMapper.deleteInformation(commodityId);
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("act","s_product");
        paramMap.put("a","del");
        paramMap.put("id",ypid);
        paramMap.put("sid",commodityId);
        paramMap.put("ka6_id",ka6id);
        paramMap.put("type",type.equals("资讯")?"2":"1");
        String outString = null;
        try {
//            outString = HttpRequestor.doPost(ThirdUrlConstant.POST_API_URL,paramMap);
            System.out.println("outString="+outString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        appResult.setData(null);
        appResult.setStateValue(0,null,null,"/deleteInformation");
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
        String contextpath = request.getScheme() +"://" + request.getServerName()  + ":" +request.getServerPort() +"/picture";
        Logger.getLogger(InformationBiz.class).info("contextpath="+contextpath);
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
