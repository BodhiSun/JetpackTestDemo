package com.bodhi.lifecycletestdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lifeTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyLocationListener myLocationListener = new MyLocationListener(this, new MyLocationListener.OnLocationChangedListener() {
            @Override
            public void onChanged(double latitude, double longitude) {
                //展示收到的位置信息
            }
        });

        // AppCompatActivity 和 Fragment 实现了 LifecycleOwner 接口
        // 将观察者与被观察者绑定
        getLifecycle().addObserver(myLocationListener);
    }

    public void clickStartService(View view) {
        Intent intent = new Intent(this,MyService.class);
        startService(intent);
    }

    public void clickStopService(View view) {
        Intent intent = new Intent(this,MyService.class);
        stopService(intent);
    }
}
