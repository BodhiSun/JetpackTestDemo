package com.bodhi.lifecycletestdemo;

import android.app.Application;

import androidx.lifecycle.ProcessLifecycleOwner;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/21
 * desc :
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new ApplicationObserver());
    }
}
