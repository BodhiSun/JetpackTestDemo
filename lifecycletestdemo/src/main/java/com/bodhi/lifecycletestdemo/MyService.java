package com.bodhi.lifecycletestdemo;

import android.content.Intent;
import android.os.IBinder;

import androidx.lifecycle.LifecycleService;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/21
 * desc : LifecycleService 实现了 LifecycleOwner 接口
 */
public class MyService extends LifecycleService {
    private MyServiceObserver myServiceObserver;


    public MyService(){
        myServiceObserver = new MyServiceObserver();
        // 将观察者与被观察者绑定
        getLifecycle().addObserver(myServiceObserver);
    }

}
