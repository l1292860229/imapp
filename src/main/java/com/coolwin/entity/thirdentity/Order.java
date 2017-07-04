package com.coolwin.entity.thirdentity;

import java.util.List;

/**
 * Created by dell on 2017/7/1.
 */
public class Order{
    public String status;
    public String ispay;
    public String code;
    public double amount;
    public String id;
    public String name;
    public String address;
    public String tel;
    public List<Goods> goods;
    class Goods{
        public String title;
        public String num;
    }
}
