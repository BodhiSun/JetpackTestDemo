package com.bodhi.lifecycletestdemo;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/21
 * desc :
 */
class MyServiceObserver implements LifecycleObserver {
    private static final String TAG = "lifeTag";

    /**
     * 当Service的onCreate()方法被调用时，该方法会被调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void startGetLocation(){
        Log.e(TAG,"Service onCreate startGetLocation");
    }

    /**
     * 当Service的onDestroy()方法被调用时，该方法会被调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void stopGetLocation(){
        Log.e(TAG,"Service onDestroy stopGetLocation");
    }



}
