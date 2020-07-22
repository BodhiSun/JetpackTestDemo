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
class ApplicationObserver implements LifecycleObserver {
    private static final String TAG = "lifeTag";

    /**
     * 在应用程序的整个生命周期中只会被调用一次
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(){
        Log.e(TAG,"Application   onCreate");
    }

    /**
     * 当应用程序在前台出现时被调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onSart(){
        Log.e(TAG,"Application   onSart");
    }

    /**
     * 当应用程序在前台出现时被调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(){
        Log.e(TAG,"Application   onResume");
    }

    /**
     * 当应用程序退出到后台时被调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(){
        Log.e(TAG,"Application   onPause");
    }

    /**
     * 当应用程序退出到后台时被调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(){
        Log.e(TAG,"Application   onStop");
    }

    /**
     * 永远不会被调用，系统不会分发调用ON_DESTROY事件
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestory(){
        Log.e(TAG,"Application   onDestory");
    }


}
