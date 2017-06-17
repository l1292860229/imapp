package com.coolwin.controller;

import com.coolwin.Biz.UserBiz;
import com.coolwin.entity.appentity.AppResult;
import com.coolwin.entity.constant.ThirdUrlConstant;
import com.coolwin.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by dell on 2017/5/5.
 */
@RestController
@EnableAutoConfiguration
public class UserController {

    @Autowired
    UserBiz userBiz;

    /**
     * 登录
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public String login(@RequestParam String phone, @RequestParam String password){
        LoggerUtil.getLogger(UserController.class).info("UserController/login");
        String result =  HttpRequest.sendGet(ThirdUrlConstant.LOGIN_URL,
                "id=585&username="+phone+"&password="+password);
        result = result.replace("(","").replace(")","");
        LoggerUtil.getLogger(UserController.class).info("result="+result);
        return userBiz.getLoginUser(result,phone);
    }


    /**
     * 注册
     * @param name 用户名
     * @param tjr 推荐人id
     * @param password 密码
     * @param id 企业id
     * @param tid
     * @param username 登录名
     * @return
     */
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

    /**
     * 获取用户详细信息
     * @param id 企业id,如果有就传
     * @param uid 用户id,如果有就传
     * @param kai6id  用户唯一id,如果有就传
     * @param userid  自已的用户id,如果有就传
     * @return
     */
    @RequestMapping("/user")
    public String user(@RequestParam(required=false)  String id, @RequestParam(required=false) String uid,
                         @RequestParam(required=false) String kai6id,@RequestParam(required=false) String userid){
        LoggerUtil.getLogger(UserController.class).info("UserController/user");
        if(!(StringUtil.isNull(id) || StringUtil.isNull(uid)|| StringUtil.isNull(kai6id))){
            String result =  HttpRequest.sendGet(ThirdUrlConstant.FRIENDS_URL,
                    "id="+id+"&uid="+uid+"&k6id="+kai6id);
            result = result.replace("(","").replace(")","");
            LoggerUtil.getLogger(UserController.class).info("result="+result);
            //第三方
            return userBiz.getThirdUserDeta(GsonUtil.getObjectStringFromJson(result,"detail"),userid,uid);
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

    /**
     * 获取用户信息列表
     * @param id 企业id
     * @param ka6_id 用户唯一id
     * @param o 人脉关系度
     * @param page 第几页
     * @param uid 表示当前用户id
     * @return
     */
    @RequestMapping("/friendlist")
    public String friendlist(@RequestParam String id,@RequestParam String ka6_id,
                             @RequestParam String o,@RequestParam String page,@RequestParam String uid){
        String result =  HttpRequest.sendGet(ThirdUrlConstant.FRIENDLIST_URL,
                "id="+id+"&ka6_id="+ka6_id+"&o="+o+"&page="+page);
        result = result.replace("(","").replace(")","");
        LoggerUtil.getLogger(UserController.class).info("result="+result);
        return userBiz.getFriendListData(GsonUtil.getObjectStringFromJson(result,"list"),uid);
    }
    @RequestMapping("/edit")
    public String edit(@RequestParam String uid,@RequestParam String token,@RequestParam String id,@RequestParam String sign,
                       @RequestParam String city,@RequestParam String province,@RequestParam String gender,@RequestParam String nickname,
                       @RequestParam String headsmall,@RequestParam(required = false) String companywebsite,
                       @RequestParam(required = false) String industry,@RequestParam(required = false) String company,
                       @RequestParam(required = false) String companyaddress,@RequestParam(required = false) String job,
                       @RequestParam(required = false) String provide,@RequestParam(required = false) String demand,
                       @RequestParam(required = false) String telephone,HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("pic");
//        FileUtil.upload(files,null);
//        String result =  HttpRequest.sendGet(ThirdUrlConstant.MODIFYUSER_URL,
//                "id="+id+"&token="+token+"&headsmall="+o+"&nickname="+nickname+"&gender="+gender+
//                        "&sign="+sign+"&province="+province+"&city="+city);
//        LoggerUtil.getLogger(UserController.class).info("result="+result);
        return userBiz.updateUser(uid,sign,city,province,gender,nickname,headsmall,companywebsite,industry,
                company,companyaddress,job,provide,demand,telephone);
    }
}
