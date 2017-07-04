package com.coolwin.entity.thirdentity;

import java.util.List;

/**
 * Created by dell on 2017/6/19.
 */
public class Information {
    public int Status;
    public String Message;
    public String title;
    public List<Subjects> subjects;
    public class Subjects{
        public int id;
        public String url;
        public String Name;
        public String LowSellPrice;
        public String LowMarketPrice;
        public String discount;
        public String Collect;
    }
}
