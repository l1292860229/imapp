package com.coolwin.Biz;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dell on 2017/5/9.
 */
public class BaseBiz {
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
}
