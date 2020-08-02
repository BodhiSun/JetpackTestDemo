package com.bodhi.databindingtestdemo.login;

import android.util.Log;

import androidx.databinding.ObservableField;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/1
 * desc :采用ObservableField绑定字段的方法 优化双向绑定相关业务逻辑类的实现方式
 */
public class TwoWayBindingViewModel2 {

    private ObservableField<LoginModel> loginModelObservableField;

    public TwoWayBindingViewModel2(){
        LoginModel loginModel = new LoginModel();
        loginModel.userName = "Macle";
        loginModelObservableField = new ObservableField<>();
        loginModelObservableField.set(loginModel);
    }

    public String getUserName(){
        Log.e("bindingadapter","getUserName2");
        return loginModelObservableField.get().userName;
    }

    public void setUserName(String userName){
        Log.e("bindingadapter","setUserName2     userName:"+userName);
        loginModelObservableField.get().userName = userName;
    }




}
