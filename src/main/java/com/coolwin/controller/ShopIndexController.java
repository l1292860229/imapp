package com.coolwin.controller;

import com.coolwin.Biz.ShopIndexBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dell on 2017/5/5.
 */
@RestController
@EnableAutoConfiguration
public class ShopIndexController {

    @Autowired
    ShopIndexBiz shopIndexBiz;
    @RequestMapping(value="/getIndex",method = RequestMethod.POST)
    public String getIndex(@RequestParam String ka6id, @RequestParam String token,@RequestParam String ypid,
                           @RequestParam String uid){
       return shopIndexBiz.getShopIndex(ka6id,token,ypid,uid);
    }
    @RequestMapping(value="/saveshopindex",method = RequestMethod.POST)
    public String saveshopindex(@RequestParam String ka6id, @RequestParam String token,@RequestParam String ypid,
                           @RequestParam String uid,@RequestParam(required = false) String data){
        return shopIndexBiz.insertOrUpdateShopIndex(ka6id,token,ypid,uid,data);
    }
}
