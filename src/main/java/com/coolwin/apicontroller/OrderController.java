package com.coolwin.apicontroller;

import com.coolwin.Biz.OrderBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dell on 2017/7/1.
 */
@RestController
@EnableAutoConfiguration
public class OrderController {
    @Autowired
    OrderBiz orderBiz;
    @RequestMapping("/getOrderList")
    public String getInformation(@RequestParam String ypid,@RequestParam(required = false) String s, @RequestParam String token,@RequestParam int page){
        return orderBiz.getOrderList(ypid,s,token,page);
    }
    @RequestMapping("/upateOrderListStatus")
    public String upateOrderListStatus(@RequestParam String ypid,@RequestParam String s,
                                      @RequestParam String token,@RequestParam String orderid){
        return orderBiz.upateOrderListStatus(ypid,s,token,orderid);
    }
}
