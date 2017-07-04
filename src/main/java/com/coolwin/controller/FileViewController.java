package com.coolwin.controller;

import com.coolwin.Biz.FileBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dell on 2017/5/5.
 */
@Controller
@EnableAutoConfiguration
public class FileViewController {

    @Autowired
    FileBiz fileBiz;

    @RequestMapping("/uploadtemplatepic")
    public String uploadtemplatepic(HttpServletRequest request){
        return fileBiz.uploadtemplatepic(request);
    }
}
