package com.bodhi.databindingtestdemo.login;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.bodhi.databindingtestdemo.BR;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/31
 * desc : 用于存放与实现 布局-model 双向绑定相关业务逻辑的类
 */
public class TwoWayBindingViewModel extends BaseObservable {

    private LoginModel loginModel;

    public TwoWayBindingViewModel(){
        loginModel = new LoginModel();
        loginModel.userName = "Jack";
    }

    //在get方法前Bindable注解 告诉编译器要对userName进行双向绑定
    @Bindable
    public String getUserName(){
        Log.e("bindingadapter","getUserName");
        return loginModel.userName;
    }

    //当布局中用户修改userName对应数据 会自动调用set方法
    public void setUserName(String userName){
        Log.e("bindingadapter","setUserName");
        //对传递的参数进行判断 防止布局-model循环调用
        if(userName!=null && !userName.equals(loginModel.userName)){
            Log.e("bindingadapter","setUserName   userName:"+userName);
            loginModel.userName = userName;
            //同时可以处理一些与业务相关的逻辑
            notifyPropertyChanged(BR.userName);
        }
    }


}
