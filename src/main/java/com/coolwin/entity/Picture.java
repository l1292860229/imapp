package com.coolwin.entity;

/**
 * Created by dell on 2017/6/11.
 */
public class Picture{
    public String key;
    public String originUrl;
    public String smallUrl;
    public double width;
    public double height;

    public Picture(){}

    public Picture(String image) {
        this.originUrl = image;
        this.smallUrl = image;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "key='" + key + '\'' +
                ", originUrl='" + originUrl + '\'' +
                ", smallUrl='" + smallUrl + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}