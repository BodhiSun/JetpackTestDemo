package com.bodhi.databindingtestdemo;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/30
 * desc :布局绑定中 用于响应按钮点击事件
 */
public class EventHandleListener {
    private Context context;

    public EventHandleListener(Context context){
        this.context = context;
    }

    public void onButtonClicked(View view){
        Toast.makeText(context,"I am clicked!",Toast.LENGTH_SHORT).show();
    }


}
