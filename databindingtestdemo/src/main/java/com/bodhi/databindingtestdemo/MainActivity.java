package com.bodhi.databindingtestdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bodhi.databindingtestdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //实例化布局文件
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //和布局绑定的数据类
        Book book = new Book("Android","Google",5);
        //将数据传递到布局文件
        activityMainBinding.setVariable(BR.book,book);

        //和布局绑定的事件类
        EventHandleListener eventHandleListener = new EventHandleListener(this);
        activityMainBinding.setEventHandler(eventHandleListener);

        //和布局绑定的变量 自定义BindingAdapter给imageView传参
        activityMainBinding.setNetworkImage("https://www.baidu.com/img/PCfb_5bf082d29588c07f842ccde3f97243ea.png");
        activityMainBinding.setLocalImage(R.mipmap.ic_launcher_round);

        //先设置imageview的 padding旧值
        activityMainBinding.setImagePadding(40);

        activityMainBinding.setClickHandler(new ClickHandler());
    }

     //点击button按钮时 让imageview的padding从40变为80
    public class ClickHandler{
        public void onClick(View view){
            Toast.makeText(MainActivity.this,"改变padding值",Toast.LENGTH_SHORT).show();
            //在设置imageview的 padding新值
            activityMainBinding.setImagePadding(80);
        }
    }
}
