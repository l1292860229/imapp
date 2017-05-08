package com.coolwin.controller;

import com.coolwin.Biz.UserBiz;
import com.coolwin.entity.appentity.AppResult;
import com.coolwin.entity.constant.ThirdUrlConstant;
import com.coolwin.util.GsonUtil;
import com.coolwin.util.HttpRequest;
import com.coolwin.util.LoggerUtil;
import com.coolwin.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dell on 2017/5/5.
 */
@RestController
@EnableAutoConfiguration
public class UserController {

    @Autowired
    UserBiz userBiz;

    @RequestMapping("/login")
    public String login(@RequestParam String phone, @RequestParam String password){
        LoggerUtil.getLogger(UserController.class).info("UserController/login");
        String result =  HttpRequest.sendGet(ThirdUrlConstant.LOGIN_URL,
                "id=585&username="+phone+"&password="+password);
        result = result.replace("(","").replace(")","");
        LoggerUtil.getLogger(UserController.class).info("result="+result);
        return userBiz.getLoginUser(result,phone);
    }


    @RequestMapping("/regist")
    public String regist(@RequestParam String name, @RequestParam String tjr,
                         @RequestParam String password,@RequestParam String id,
                         @RequestParam String tid,@RequestParam String username){
        LoggerUtil.getLogger(UserController.class).info("UserController/regist");
        String result =  HttpRequest.sendGet(ThirdUrlConstant.REGIST_URL,
                "id="+id+"&username="+username+"&name="+name+"&tjr="+tjr+"&password="+password
                +"&tid="+tid+"&telephone="+username);
        result = result.replace("(","").replace(")","");
        LoggerUtil.getLogger(UserController.class).info("result="+result);
        return userBiz.registUser(result);
    }
    @RequestMapping("/user")
    public String user(@RequestParam String id, @RequestParam String uid,
                         @RequestParam String kai6id,@RequestParam String userid){
        LoggerUtil.getLogger(UserController.class).info("UserController/user");
        if(!(StringUtil.isNull(id) || StringUtil.isNull(uid)|| StringUtil.isNull(kai6id))){
            String result =  HttpRequest.sendGet(ThirdUrlConstant.FRIENDS_URL,
                    "id="+id+"&uid="+uid+"&k6id="+kai6id);
            result = result.replace("(","").replace(")","");
            LoggerUtil.getLogger(UserController.class).info("result="+result);
            //第三方
            return userBiz.getThirdUserDeta(GsonUtil.getObjectStringFromJson(result,"detail"),userid);
        }else if(!StringUtil.isNull(uid)){
            //如果只传了uid
            return userBiz.getUserDeta(userid,uid);
        }else if(!StringUtil.isNull(kai6id)){
            //如果只传了kai6id
            return userBiz.getUserDeta(userid,kai6id);
        }
        AppResult appResult = new AppResult();
        appResult.setData(null);
        appResult.setStateValue(1,"该用户不存在",null,"/user");
        return GsonUtil.objectToJson(appResult);
    }
}
