package com.coolwin.controller;

import com.coolwin.entity.appentity.AppResult;
import com.coolwin.util.GsonUtil;
import com.coolwin.util.MD5Util;
import com.coolwin.util.NetUtil;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by dell on 2017/6/13.
 */
@RestController
@EnableAutoConfiguration
public class WeiChatPrepayController {
    // key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
    //TODO
    private static String KEY = "coolwin20150608quanmindaiyan0518";
    @RequestMapping("/weichatprepay")
    public String upload(HttpServletRequest request){
        AppResult appResult = new AppResult();
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        SortedMap<String, String> map = new TreeMap<String, String>();
        //TODO 你的appid
        map.put("appid", "wx6e8948c5d050b521");
        //TODO 你的商户id
        map.put("mch_id", "1343315201");
        map.put("nonce_str", String.valueOf(System.currentTimeMillis()));
        map.put("body", "demo");
        map.put("out_trade_no", String.valueOf(System.currentTimeMillis()));
        map.put("total_fee", "1");
        map.put("spbill_create_ip", "127.0.0.1");
        map.put("notify_url", "http://www.weixin.qq.com/wxpay/pay.php");
        map.put("trade_type", "APP");
        String sign = createSign(map);
        map.put("sign",sign );
        // 生成XMl
        Set set = map.entrySet();
        Iterator it = set.iterator();
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            sb.append("<" + key + "><![CDATA[");
            sb.append(value);
            sb.append("]]></" + key + ">");
        }
        sb.append("</xml>");
        SortedMap<String, String> test = new TreeMap<String, String>();
        try {
//            System.out.println("生成xml:"+sb.toString());
            String postData = NetUtil.doPost(url, sb.toString());
//            System.out.println("请求结果"+postData.toString());
            JSONObject json = XML.toJSONObject(postData).getJSONObject("xml");
            test.put("appid", json.getString("appid"));
            test.put("partnerid", json.getString("mch_id"));
            test.put("prepayid", json.getString("prepay_id"));
            test.put("package", "Sign=WXPay");
            test.put("noncestr", String.valueOf(System.currentTimeMillis()));
            test.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            test.put("sign", createSign(test));
        } catch (Exception e) {
            e.printStackTrace();
            appResult.setData(null);
            appResult.setStateValue(1,"支付失败,原因:"+e.getMessage(),null,"/weichatprepay");
            return GsonUtil.objectToJson(appResult);
        }
        appResult.setData(test);
        appResult.setStateValue(0,null,null,"/weichatprepay");
        return GsonUtil.objectToJson(appResult);
    }
    @SuppressWarnings("rawtypes")
    public static String createSign(SortedMap<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        // 所有参与传参的参数按照accsii排序（升序）
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + KEY);
//        System.out.println("字符串拼接后是：" + sb.toString());
        String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
//        System.out.println("sign：" + sign);
        return sign;
    }
}
