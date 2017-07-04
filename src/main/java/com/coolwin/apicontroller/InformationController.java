package com.coolwin.apicontroller;

import com.coolwin.Biz.InformationBiz;
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
public class InformationController {

    @Autowired
    InformationBiz informationBiz;

    @RequestMapping(value="/getinformation",method = RequestMethod.POST)
    public String getInformation(@RequestParam String ka6id, @RequestParam String token,@RequestParam String ypid,
                           @RequestParam String uid, @RequestParam int page,@RequestParam String type){
       return informationBiz.getInformation(ka6id,token,ypid,uid,page,type);
    }
    @RequestMapping(value="/saveinformation",method = RequestMethod.POST)
    public String saveInformation(@RequestParam String ka6id, @RequestParam String token,@RequestParam String ypid,
                           @RequestParam String uid,@RequestParam String title,
                                @RequestParam(required = false) String price,
                                @RequestParam String type,
                                @RequestParam String content,HttpServletRequest request){
        return informationBiz.saveInformation(ka6id,token,ypid,uid,title,price,content,type,request);
    }
    @RequestMapping(value="/delinformation",method = RequestMethod.POST)
    public String delInformation(@RequestParam String id,@RequestParam String ka6id,
                               @RequestParam String token,@RequestParam String ypid,
                                @RequestParam String uid,@RequestParam String type){
        return informationBiz.deleteInformation(ypid,id,ka6id,type);
    }
}
