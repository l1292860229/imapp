package com.coolwin.Biz;

import com.coolwin.entity.appentity.AppResult;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dell on 2017/5/9.
 */
public class BaseBiz {
    AppResult appResult = new AppResult();
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
}
