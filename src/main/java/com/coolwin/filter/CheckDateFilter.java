package com.coolwin.filter;

import com.coolwin.entity.appentity.AppResult;
import com.coolwin.util.GsonUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by dell on 2017/5/5.
 */
public class CheckDateFilter implements Filter {
    String key = "1Zxm^*s7ZowzjR3@PRA^/www.winchat.com.cn-2012-2016wei20.com";
    protected static String[] patterns = new String[]{"/upload",".html",".css",".js"};
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//       String esign =  servletRequest.getParameter("esign");
//       String timestamp =  servletRequest.getParameter("timestamp");
//       String devicetype =  servletRequest.getParameter("devicetype");
//       if(StringUtil.isNull(esign)||StringUtil.isNull(devicetype)||StringUtil.isNull(devicetype)){
//           return;
//       }
//        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
//        String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
//        System.out.println(url);
//        for (String pattern : patterns) {
//            if (url.contains(pattern)) {
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            }
//        }
//        servletResponse.setCharacterEncoding("utf-8");
//        AppResult appResult = new AppResult();
//        String ka6id = servletRequest.getParameter("ka6id");
//        if(StringUtil.isNull(ka6id)){
//            appResult.setStateValue(1,"ka6id为空",null,"/doFilter");
//            setResponse(appResult,servletResponse);
//            return;
//        }
//        String token = servletRequest.getParameter("token");
//        if(StringUtil.isNull(token)){
//            appResult.setStateValue(1,"token为空",null,"/doFilter");
//            setResponse(appResult,servletResponse);
//            return;
//        }
//        String ypid = servletRequest.getParameter("ypid");
//        if(StringUtil.isNull(ypid)){
//            appResult.setStateValue(1,"ypid为空",null,"/doFilter");
//            setResponse(appResult,servletResponse);
//            return;
//        }
//        String uid = servletRequest.getParameter("uid");
//        if(StringUtil.isNull(uid)){
//            appResult.setStateValue(1,"uid为空",null,"/doFilter");
//            setResponse(appResult,servletResponse);
//            return;
//        }
       filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }

    public void setResponse(AppResult appResult, ServletResponse servletResponse){
        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
        PrintWriter out = null;
        try {
            out = wrapper.getWriter();
            //取返回的json串
            String result = GsonUtil.objectToJson(appResult);
            out.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }
}
