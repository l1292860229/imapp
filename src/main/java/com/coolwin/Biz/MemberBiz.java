package com.coolwin.Biz;

import com.coolwin.entity.appentity.AppResult;
import com.coolwin.entity.appentity.AppUser;
import com.coolwin.entity.constant.ThirdUrlConstant;
import com.coolwin.entity.thirdentity.MemberList;
import com.coolwin.util.GsonUtil;
import com.coolwin.util.HttpRequestor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/7/1.
 */
@Component
public class MemberBiz extends BaseBiz {
    SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
    public String getMemberList(String ypid,String token,int page){
        AppResult appResult = new AppResult();
        try {
            String data = HttpRequestor.doGet(ThirdUrlConstant.MEMBERS_URL+"?id="+ypid+"&token="+token+"&page="+page);
            data = data.replace("(","").replace(")","");
            MemberList memberList = GsonUtil.parseJsonWithGson(data,MemberList.class);
            List<AppUser> appUserList = new ArrayList<>();
            if(memberList.list!=null){
                for (String s : memberList.list.keySet()) {
                    AppUser appUser = new AppUser();
                    appUser.setUid(s);
                    appUser.setNickname(memberList.list.get(s).title);
                    appUser.setHeadsmall(memberList.list.get(s).img);
                    appUser.setProvince(memberList.list.get(s).minprice);
                    appUser.setCreatetime(sdf.parse( memberList.list.get(s).maxprice).getTime()+"");
                    appUser.setPhone(memberList.list.get(s).openid);
                    appUser.setUserdj(memberList.list.get(s).dj);
                    appUserList.add(appUser);
                }
            }
            appResult.setData(appUserList);
        } catch (Exception e) {
            e.printStackTrace();
            appResult.setStateValue(1,"后台数据请求异常",null,"");
            return GsonUtil.objectToJson(appResult);
        }
        appResult.setStateValue(0,null,null,"");
        return GsonUtil.objectToJson(appResult);
    }
}
