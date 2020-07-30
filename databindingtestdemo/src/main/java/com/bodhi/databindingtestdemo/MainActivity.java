package com.bodhi.databindingtestdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;

import com.bodhi.databindingtestdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //实例化布局文件
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //和布局绑定的数据类
        Book book = new Book("Android","Google",5);
        //将数据传递到布局文件
        activityMainBinding.setVariable(BR.book,book);

        //和布局绑定的事件类
        EventHandleListener eventHandleListener = new EventHandleListener(this);
        activityMainBinding.setEventHandler(eventHandleListener);




    }
}
