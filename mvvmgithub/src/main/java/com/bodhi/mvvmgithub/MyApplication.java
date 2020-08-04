package com.bodhi.mvvmgithub;

import android.app.Application;

import com.bodhi.mvvmgithub.api.Api;
import com.bodhi.mvvmgithub.api.RetrofitClient;
import com.bodhi.mvvmgithub.db.UserDatabase;
import com.bodhi.mvvmgithub.model.User;

public class MyApplication extends Application {

    private static UserDatabase userDatabase;
    private static Api api;

    @Override
    public void onCreate() {
        super.onCreate();

        //由于数据库和Retrofit通常伴随应用程序的整个生命周期 故在此初始化
        userDatabase = UserDatabase.getInstance(this);
        api = RetrofitClient.getInstance().getApi();
    }

    public static Api getApi(){
        return api;
    }

    public static UserDatabase getUserDatabase(){
        return userDatabase;
    }
}
