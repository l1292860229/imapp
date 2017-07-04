package com.coolwin.entity.thirdentity;

import java.util.Map;

/**
 * Created by dell on 2017/7/1.
 */
public class MemberList {
    public Map<String,Member> list;
    public class Member{
        public String title;
        public String img;
        public String minprice;
        public String maxprice;
        public String openid;
        public String dj;
    }
}
