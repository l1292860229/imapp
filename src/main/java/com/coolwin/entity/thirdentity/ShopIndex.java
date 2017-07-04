package com.coolwin.entity.thirdentity;

import com.coolwin.entity.publicentity.BannerList;

import java.util.List;

/**
 * Created by dell on 2017/6/17.
 */
public class ShopIndex {
    public String msg;
    public String code;
    public String title;
    public List<BannerList> bannerList;
    public List<Plist> plist;
    public class Plist{
        public String p_class;
        public String p_link;
        public String p_url;
        public String p_name;
    }
}
