package com.coolwin.controller;

import com.coolwin.ImappApplication;
import com.coolwin.entity.DB.DBUserMenu;
import com.coolwin.dao.DBMapper;
import com.coolwin.util.HttpRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by dell on 2017/5/3.
 */
@Controller
@EnableAutoConfiguration
public class HelloController {
    private Logger logger = Logger.getLogger(ImappApplication.class);
    @Autowired
    private DBMapper dbMapper;

    @RequestMapping("/")
    public String index(){
        String result =  HttpRequest.sendGet("http://shop.wei20.cn/gouwu/wishmb/api_user_login1.shtml","id=585&username=test002&password=test002");
        System.out.println("result="+result);
        return "hello";
    }
    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name, Model model){
        model.addAttribute("name",name);
        List<DBUserMenu> DBUserMenus =  dbMapper.getUser();
        for (DBUserMenu DBUserMenu : DBUserMenus) {
            System.out.println(DBUserMenu);
        }
        return "hello";
    }
    @RequestMapping("/hellolog")
    public String hellolog(){
        logger.info("hellolog come on");
        return "hello";
    }
}
