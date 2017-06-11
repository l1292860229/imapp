package com.coolwin.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by dell on 2017/5/5.
 */
public class CheckDateFilter implements Filter {
    String key = "1Zxm^*s7ZowzjR3@PRA^/www.winchat.com.cn-2012-2016wei20.com";
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
       filterChain.doFilter(servletRequest, servletResponse);
    }
    @Override
    public void destroy() {
    }
}
