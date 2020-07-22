package com.bodhi.lifecycletestdemo;

import android.app.Activity;
import android.print.PrinterId;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/20
 * desc :LifecycleOwner - LifecycleObserver 观察者模式对Activity/Fragment生命周期进行监听
 */
class MyLocationListener implements LifecycleObserver {
    private static final String TAG = "lifeTag";

    public MyLocationListener(Activity context,OnLocationChangedListener onLocationChangedListener){
        Log.e(TAG,"initLocationManager");
        //初始化操作
//        initLocationManager();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void onCreate(){
        Log.e(TAG,"Activity onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void onStart(){
        Log.e(TAG,"Activity onStart");
    }

    /**
     * 当Activity执行onResume()方法时，该方法会自动调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void startGetLocation(){
        Log.e(TAG,"Activity onResume startGetLocation");
    }


    /**
     * 当Activity执行onPause()方法时，该方法会自动调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void stopGetLocation(){
        Log.e(TAG,"Activity onPause stopGetLocation");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void onStop(){
        Log.e(TAG,"Activity onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestory(){
        Log.e(TAG,"Activity onDestory");
    }

    /**
     *当地理位置发生变化时，通过该接口通知调用者
     */
    interface OnLocationChangedListener {
        void onChanged(double latitude,double longitude);
    }


}
