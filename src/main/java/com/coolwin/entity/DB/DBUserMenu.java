package com.coolwin.entity.DB;

/**
 * Created by dell on 2017/5/4.
 */
public class DBUserMenu {
    private Integer id;
    private String openid;
    private String menuname;
    private String menuurl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getMenuurl() {
        return menuurl;
    }

    public void setMenuurl(String menuurl) {
        this.menuurl = menuurl;
    }

    @Override
    public String toString() {
        return "DBUserMenu{" +
                "id=" + id +
                ", openid='" + openid + '\'' +
                ", menuname='" + menuname + '\'' +
                ", menuurl='" + menuurl + '\'' +
                '}';
    }
}
