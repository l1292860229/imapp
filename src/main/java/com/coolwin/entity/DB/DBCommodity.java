package com.coolwin.entity.DB;

import com.coolwin.entity.Picture;
import com.coolwin.entity.appentity.AppCommodity;
import com.coolwin.util.GsonUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by dell on 2017/6/10.
 */
public class DBCommodity {
    private int id;
    private int uid;
    private String ypid;
    private String content;
    private String title;
    private double price;
    private String picture;
    private String shopurl;
    private String video;

    public AppCommodity toAppCommodity(){
        AppCommodity appCommodity = new AppCommodity();
        appCommodity.setId(id);
        appCommodity.setUid(uid);
        appCommodity.setYpid(ypid);
        appCommodity.setContent(content);
        appCommodity.setTitle(title);
        appCommodity.setPrice(price);
        appCommodity.setPicture(GsonUtil.parseJsonWithGsonObject(picture,new TypeToken<List<Picture>>()
        {}.getType()));
        appCommodity.setShopurl(shopurl);
        appCommodity.setVideo(GsonUtil.parseJsonWithGson(video,AppCommodity.Video.class));
        return appCommodity;
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
