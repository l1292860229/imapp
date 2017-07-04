package com.coolwin.apicontroller;

import com.coolwin.Biz.MemberBiz;
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
public class MemberController {
    @Autowired
    MemberBiz memberBiz;

    @RequestMapping("/getMemberList")
    public String getMemberList(@RequestParam String ypid,@RequestParam String token,@RequestParam int page){
        return memberBiz.getMemberList(ypid,token,page);
    }
}
