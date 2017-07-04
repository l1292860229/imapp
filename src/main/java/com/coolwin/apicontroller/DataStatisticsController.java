package com.coolwin.apicontroller;

import com.coolwin.Biz.DataStatisticsBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dell on 2017/7/1.
 */
@RestController
@EnableAutoConfiguration
public class DataStatisticsController {
    @Autowired
    DataStatisticsBiz dataStatisticsBiz;

    @RequestMapping("/getDataStatistics")
    public String DataStatistics(@RequestParam String ypid,@RequestParam String token){
        return dataStatisticsBiz.getDataStatistics(ypid,token);
    }
}
