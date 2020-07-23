package com.bodhi.viewmodeltestdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bodhi.viewmodeltestdemo.sharedata.FragmentTransDataActivity;

public class TimerWithLiveDataActivity extends AppCompatActivity {

    private TextView textView;
    private MutableLiveData<Integer> liveData;
    private Observer<Integer> liveNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_with_live_data);

        initComponent();
    }

    private void initComponent() {
        textView = findViewById(R.id.textview);

        //通过ViewModelProvider得到ViewModel
        TimerWithLiveDataViewModel timerWithLiveDataViewModel = new ViewModelProvider(this).get(TimerWithLiveDataViewModel.class);

        //得到ViewModel中的LiveData
        liveData = timerWithLiveDataViewModel.getCurrentSecond();

//        //observe方法 只有当页面处于活跃时 start~resume,才能收到来自liveData的通知
//        liveData.observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer second) {
//                Log.e("liveNum","   second:"+second);
//                textView.setText("TIME:"+second);
//            }
//        });


        //observeForever 只要liveData包装的数据发生变化 无论页面处于什么状态 都会收到通知
        liveNum = new Observer<Integer>() {
            @Override
            public void onChanged(Integer second) {
                Log.e("liveNum", "   second:" + second);
                textView.setText("TIME:" + second);
            }
        };
        liveData.observeForever(liveNum);


        timerWithLiveDataViewModel.startTiming();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //调用observeForever方法 用完之后一定要记得调用removeObserver方法 否则Activity无法回收 造成内存泄露
        liveData.removeObserver(liveNum);
    }



    public void clickToTemp(View view) {
        startActivity(new Intent(this, FragmentTransDataActivity.class));
    }

}