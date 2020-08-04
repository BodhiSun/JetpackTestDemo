package com.bodhi.mvvmgithub.api;

import com.bodhi.mvvmgithub.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 定义Retrofit Api接口
 */
public interface Api {

    @GET("users/{userId}")
    Call<User> getUser(@Path("userId")String userID);

}
