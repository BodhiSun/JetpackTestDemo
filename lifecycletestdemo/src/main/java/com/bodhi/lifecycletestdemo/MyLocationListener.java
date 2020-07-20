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
 * desc :LifecycleOwner - LifecycleObserver 观察者模式对Activity声明周期进行监听
 */
class MyLocationListener implements LifecycleObserver {
    private static final String TAG = "lifecycle";

    public MyLocationListener(Activity context,OnLocationChangedListener onLocationChangedListener){
        Log.d(TAG,"initLocationManager");
        //初始化操作
//        initLocationManager();
    }

    /**
     * 当Activity执行onResume()方法时，该方法会自动调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void startGetLocation(){
        Log.d(TAG,"startGetLocation");
    }


    /**
     * 当Activity执行onPause()方法时，该方法会自动调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void stopGetLocation(){
        Log.d(TAG,"stopGetLocation");
    }

    /**
     *当地理位置发生变化时，通过该接口通知调用者
     */
    interface OnLocationChangedListener {
        void onChanged(double latitude,double longitude);
    }


}
