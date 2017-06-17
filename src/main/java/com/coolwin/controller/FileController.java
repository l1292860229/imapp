package com.coolwin.controller;

import com.coolwin.Biz.FileBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dell on 2017/5/5.
 */
@RestController
@EnableAutoConfiguration
public class FileController {

    @Autowired
    FileBiz fileBiz;

    @RequestMapping("/upload")
    public String upload(HttpServletRequest request){
        return fileBiz.upload(request);
    }

    @RequestMapping("/downfile")
    public String downfile(HttpServletRequest request){
        return fileBiz.downFile(request);
    }
    @RequestMapping("/tohtml")
    public String tohtml(HttpServletRequest request,@RequestParam String filename){
        return fileBiz.tohtml(request,filename);
    }
}
