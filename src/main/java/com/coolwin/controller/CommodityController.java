package com.coolwin.controller;

import com.coolwin.Biz.CommodityBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dell on 2017/5/5.
 */
@RestController
@EnableAutoConfiguration
public class CommodityController {

    @Autowired
    CommodityBiz commodityBiz;

    @RequestMapping(value="/getcommodity",method = RequestMethod.POST)
    public String getCommodity(@RequestParam String ka6id, @RequestParam String token,@RequestParam String ypid,
                           @RequestParam String uid, @RequestParam int page){
       return commodityBiz.getcommodity(ka6id,token,ypid,uid,page);
    }
    @RequestMapping(value="/savecommodity",method = RequestMethod.POST)
    public String saveCommodity(@RequestParam String ka6id, @RequestParam String token,@RequestParam String ypid,
                           @RequestParam String uid,@RequestParam String title,
                                @RequestParam String price, @RequestParam String content,HttpServletRequest request){
        return commodityBiz.saveCommodity(ka6id,token,ypid,uid,title,price,content,request);
    }
    @RequestMapping(value="/delcommodity",method = RequestMethod.POST)
    public String delcommodity(@RequestParam String id,@RequestParam String ka6id, @RequestParam String token,@RequestParam String ypid
    ,@RequestParam String uid){
        return commodityBiz.deleteCommodity(id);
    }
}
