package com.coolwin.entity.DB;

import com.coolwin.entity.Picture;
import com.coolwin.entity.appentity.AppCommodity;
import com.coolwin.util.GsonUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/6/10.
 */
public class DBInformation {
    private int id;
    private int uid;
    private String ypid;
    private String content;
    private String title;
    private double price;
    private String picture;
    private String shopurl;
    private String video;
    private String type;

    public AppCommodity toAppCommodity(){
        AppCommodity appCommodity = new AppCommodity();
        appCommodity.setId(id);
        appCommodity.setUid(uid);
        appCommodity.setYpid(ypid);
        appCommodity.setContent(content);
        appCommodity.setTitle(title);
        appCommodity.setPrice(price);
        appCommodity.setType(type);
        List<Picture> mpic = GsonUtil.parseJsonWithGsonObject(picture,new TypeToken<List<Picture>>()
        {}.getType());
        AppCommodity.Video videotemp = GsonUtil.parseJsonWithGson(video,AppCommodity.Video.class);
        List<Picture> mpictemp = new ArrayList<>();
        if(mpic!=null && mpic.size()>0){
            mpictemp.add(mpic.get(0));
        }else if(videotemp!=null){
            mpictemp.add(new Picture(videotemp.image));
        }
        appCommodity.setPicture(mpictemp);
        appCommodity.setVideo(videotemp);
        appCommodity.setShopurl(shopurl);
        return appCommodity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getYpid() {
        return ypid;
    }

    public void setYpid(String ypid) {
        this.ypid = ypid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getShopurl() {
        return shopurl;
    }

    public void setShopurl(String shopurl) {
        this.shopurl = shopurl;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
