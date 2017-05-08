package com.coolwin.Biz;

import com.coolwin.entity.constant.OpenfireUrlConstant;
import com.coolwin.util.HttpRequest;
import org.springframework.stereotype.Component;

/**
 * Created by dell on 2017/5/6.
 */
@Component
public class OpenfireBiz {
    private String secret = "7ih1mIbF";
    public boolean regist(String username,String password){
      String result =  HttpRequest.sendGet(OpenfireUrlConstant.OPENFIRE_REGIST_URL,
                "type=add&secret="+secret+"&username="+username+"&name="+username+"&password="+password+"&email="+username+"@139.com");
        if(result.contains("ok")){
            return true;
        }
        return false;
    }
}
