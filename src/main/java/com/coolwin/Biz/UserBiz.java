package com.coolwin.Biz;

import com.coolwin.dao.UserMapper;
import com.coolwin.entity.DB.DBUser;
import com.coolwin.entity.appentity.AppUser;
import com.coolwin.entity.thirdentity.ThirdUser;
import com.coolwin.entity.thirdentity.ThirdUserDeta;
import com.coolwin.entity.thirdentity.ThirdUserList;
import com.coolwin.util.CharacterParser;
import com.coolwin.util.GsonUtil;
import com.coolwin.util.StringUtil;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/5/5.
 */
@Component
public class UserBiz  extends BaseBiz{
    @Autowired
    UserMapper userMapper;
    @Autowired
    OpenfireBiz openfireBiz;
    /**
     * 跟据第三方接口数据返回结果值
     */
    public String  getLoginUser(String result,String phone){
        //转化成第三方对象
        ThirdUser thirdUser = GsonUtil.parseJsonWithGson(result,ThirdUser.class);
        //判断数据是否正确
        if (thirdUser.getSuccess().equals("no")) {
            appResult.setData(null);
            appResult.setStateValue(1,thirdUser.getMsg(),null,"/login");
            return GsonUtil.objectToJson(appResult);
        }
        String ypid= thirdUser.getYpid2();
        if (StringUtil.isNull(ypid)) {
            ypid = thirdUser.getYpid();
        }
        if("0".equals(ypid)){
            ypid = "585";
        }
        //查询数据库是否有记录存在
        DBUser  dbUser = userMapper.getUserByYpidAndPhone(ypid,thirdUser.getKa6_id().toLowerCase());
        //如果没有就新增加一条记录
        if(dbUser==null){
            dbUser =  registUser(thirdUser,phone);
            if (dbUser==null) {
                appResult.setStateValue(1,"openfire注册失败",null,"/login");
                return GsonUtil.objectToJson(appResult);
            }
        }
        //返回结果
        appResult.setData(dbUser.getAppUser(thirdUser));
        appResult.setStateValue(0,null,null,"/login");
        return GsonUtil.objectToJson(appResult);
    }

    /**
     * 跟据第三方类注册用户
     * @param user
     * @return
     */
    public DBUser registUser(ThirdUser user,String phone){
        String ypid= user.getYpid2();
        if (StringUtil.isNull(ypid)) {
            ypid = user.getYpid();
        }
        if("0".equals(ypid)){
            ypid = "585";
        }
        String[] wxinfo = user.getWxinfo().split("|");
        //获取openfire注册密码
        String password = String.valueOf((int)Math.random()*1000000);
        //先在openfire注册如果成功就插入数据
        if(openfireBiz.regist(user.getKa6_id().toLowerCase(),password)){
            userMapper.insertUser(ypid, CharacterParser.getSelling(wxinfo[0]),user.getKa6_id().toLowerCase(),
                    wxinfo[0],wxinfo[2],password,
                    String.valueOf(System.currentTimeMillis()/1000),phone,wxinfo[1]);
            return userMapper.getUserByYpidAndPhone(ypid,user.getKa6_id().toLowerCase());
        }
        return null;
    }
    /**
     * 注册用户
     * @param result
     * @return
     */
    public String registUser(String result){
        //转化成第三方对象
        ThirdUser thirdUser = GsonUtil.parseJsonWithGson(result,ThirdUser.class);
        //判断数据是否正确
        if (thirdUser.getSuccess().equals("no")) {
            appResult.setStateValue(1,thirdUser.getMsg(),null,"/regist");
        }else{
            appResult.setStateValue(0,thirdUser.getMsg(),null,"/regist");
        }
        appResult.setData(null);
        return GsonUtil.objectToJson(appResult);
    }

    /**
     * 获取单个用户的详细资料
     * @param uid
     * @param uidOrKai6id
     * @return
     */
    public String getUserDeta(String uid,String uidOrKai6id){
        DBUser  dbUser =  userMapper.getUser(uid,uidOrKai6id);
        appResult.setData(dbUser.getAppUser());
        appResult.setStateValue(0,null,null,"/user");
        return GsonUtil.objectToJson(appResult);
    }

    /**
     * 获取第三方的用户信息详细资料
     * @param result
     * @param userid
     * @return
     */
    public String  getThirdUserDeta(String result,String userid,String uid){
        //转化成第三方对象
        ThirdUserDeta thirdUserDeta = GsonUtil.parseJsonWithGson(result,ThirdUserDeta.class);
        //判断数据是否正确
        //查询数据库是否有记录存在
        DBUser  dbUser;
        if(StringUtil.isNull(thirdUserDeta.getKa6_id())){
            dbUser = userMapper.getUser(userid,uid);
        }else{
            dbUser = userMapper.getUser(userid,thirdUserDeta.getKa6_id().toLowerCase());
        }
        //返回结果
        appResult.setData(dbUser.getAppUser(thirdUserDeta));
        appResult.setStateValue(0,null,null,"/user");
        return GsonUtil.objectToJson(appResult);
    }

    /**
     * 获取第三方的用户信息列表
     * @param result
     * @param uid
     * @return
     */
    public String getFriendListData(String result,String uid){
        Map<String,ThirdUserList> thirdUserListMap = GsonUtil.parseJsonWithGsonObject(result,new TypeToken<Map<String,ThirdUserList>>() {}.getType());
        List<DBUser> dbUsers = userMapper.getUserListByThirdUserList(uid,thirdUserListMap.values());
        List<AppUser> appUsers = new ArrayList<>();
        //java8 新特性 lambda表达式
        thirdUserListMap.keySet().forEach(s -> {
            boolean isadd=true;
            for (DBUser dbUser : dbUsers) {
                if (dbUser.getPhone().equals(thirdUserListMap.get(s).getOpenid().toLowerCase())) {
                    dbUser.setNickname(thirdUserListMap.get(s).getTitle());
                    dbUser.setHeadsmall(thirdUserListMap.get(s).getImg());
                    appUsers.add(dbUser.getAppUser());
                    isadd = false;
                    break;
                }
            }
            //如果数据库中有则不添加
            if(isadd){
                AppUser appUser = new AppUser();
                appUser.setUid(s);
                appUser.setPhone(thirdUserListMap.get(s).getOpenid().toLowerCase());
                appUser.setNickname(thirdUserListMap.get(s).getTitle());
                appUser.setHeadsmall(thirdUserListMap.get(s).getImg());
                appUser.setIsfriend("1");
                appUser.setGetmsg("1");
                appUsers.add(appUser);
            }
        });
        appResult.setData(appUsers);
        appResult.setStateValue(0,null,null,"/friendlist");
        return GsonUtil.objectToJson(appResult);
    }
    public String updateUser( String uid, String sign, String city,String province, String gender,String nickname,
                             String headsmall, String companywebsite,String industry, String company,String companyaddress,
                              String job,String provide,  String demand, String telephone){
        userMapper.updateUser(uid,sign,city,province,gender,nickname,headsmall,companywebsite,industry,company,companyaddress,
                job,provide,demand,telephone);
        DBUser dbUser = userMapper.getUserByYpidAndPhone(uid,uid);
        appResult.setData(dbUser.getAppUser());
        appResult.setStateValue(0,null,null,"/edit");
        return GsonUtil.objectToJson(appResult);
    }
}
