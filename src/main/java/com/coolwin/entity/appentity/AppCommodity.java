package com.coolwin.entity.appentity;

import com.coolwin.entity.DB.DBCommodity;
import com.coolwin.entity.Picture;
import com.coolwin.util.GsonUtil;

import java.util.List;

/**
 * Created by dell on 2017/6/10.
 */
public class AppCommodity {
    private int id;
    private int uid;
    private String ypid;
    private String content;
    private String title;
    private double price;
    private List<Picture> picture;
    private String shopurl;
    private Video video;
    public class Video{
        public String url;
        public String time;
        public String image;
    }
    public DBCommodity toDBCommodity(){
        DBCommodity dbCommodity = new DBCommodity();
        dbCommodity.setId(id);
        dbCommodity.setUid(uid);
        dbCommodity.setYpid(ypid);
        dbCommodity.setContent(content);
        dbCommodity.setTitle(title);
        dbCommodity.setPrice(price);
        dbCommodity.setPicture(GsonUtil.objectToJson(picture));
        dbCommodity.setShopurl(shopurl);
        dbCommodity.setVideo(GsonUtil.objectToJson(video));
        return dbCommodity;
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
    public List<Picture> getPicture() {
        return picture;
    }

    public void setPicture(List<Picture> picture) {
        this.picture = picture;
    }

    public String getShopurl() {
        return shopurl;
    }

    public void setShopurl(String shopurl) {
        this.shopurl = shopurl;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

}
